package com.urban.commands.penalties;

import com.urban.usage.RedisUsageCounter;

public class RemoveUsagePenalty {
    public String penaltyMessage(String username) {
        return "Hey " + username + "! Only lols allowed! Penalty given!";
    }

    public String noPenaltyMessage(String username) {
        return "Hey " + username + "! Only lols allowed!";
    }

    public void addPenalty(RedisUsageCounter counter, String username) {
        counter.removeUsage(username);
    }
}