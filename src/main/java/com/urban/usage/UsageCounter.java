package com.urban.usage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import redis.clients.jedis.Jedis;

public class UsageCounter {

    private static final Jedis jedisConnection = new Jedis("localhost");
    private static final String prefix = "lolbot";
    private static final int database = 10;

    public UsageCounter() {
        jedisConnection.select(database);
    }

    public void storeUsage(String username) {
        jedisConnection.set(lastUsageKey(), username);
        jedisConnection.zincrby(mainPrefix(), 1, username);
        jedisConnection.zincrby(dailyPrefix(), 1, username);
    }

    public void removeUsage(String username) {
        if (fetchTotalUsage(username) > 0) {
            jedisConnection.zincrby(mainPrefix(), -1, username);
        }

        if (fetchDailyUsage(username) > 0) {
            jedisConnection.zincrby(dailyPrefix(), -1, username);
        }
    }

    public void storeTimeout(String username) {
        jedisConnection.set(timeoutKey(username), dateString());
        jedisConnection.expire(timeoutKey(username), (60 * 5));
    }

    public boolean hasTimeout(String username) {
        return jedisConnection.ttl(timeoutKey(username)) > 0;
    }

    public String lastUsage() {
        return jedisConnection.get(lastUsageKey());
    }

    public int fetchTotalUsage(String username) {
        return jedisConnection.zscore(mainPrefix(), username).intValue();
    }

    public int fetchDailyUsage(String username) {
        return jedisConnection.zscore(dailyPrefix(), username).intValue();
    }

    public String stats() {
        List<String> stats = new ArrayList<>();
        List<String> usernames = new ArrayList<>(jedisConnection.zrange(dailyPrefix(), 0, -1));

        Collections.reverse(usernames);

        Iterator<String> iterator = usernames.iterator();
        if (iterator.hasNext()) {
            String username = iterator.next();
            stats.add(":sparkles:*" + username + "*:sparkles: " + fetchDailyUsage(username) + " lols today! " + fetchTotalUsage(username) + " in total!");
            while (iterator.hasNext()) {
                username = iterator.next();
                stats.add("*" + username + "* " + fetchDailyUsage(username) + " lols today! " + fetchTotalUsage(username) + " in total!");
            }
        }

        return String.join("\n", stats);
    }

    private String dailyPrefix() {
        return mainPrefix() + ":" + dateString();
    }

    private String mainPrefix() {
        return prefix;
    }

    private String dateString() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

    private String timeoutKey(String username) {
        return mainPrefix() + ":timeout:" + username;
    }

    private String lastUsageKey() {
        return mainPrefix() + ":private:last_usage";
    }
}
