package com.urban.commands;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.urban.usage.RedisUsageCounter;

public class ReplyCommand extends AbstractBotCommand {
    public ReplyCommand(String botChannel, String botCommand) {
        super(botChannel, botCommand);
    }

    @Override
    public void processCommand(SlackMessagePosted slackMessagePosted, SlackSession slackSession) {
        System.out.println("Processing reply");

        String username = slackMessagePosted.getSender().getUserName();
        if(username.contains("lol")) {
            System.out.println("User is lolbot: Returning");
            return;
        }

        RedisUsageCounter counter = new RedisUsageCounter();

        if(counter.hasTimeout(username)) {
            System.out.println("User has a timeout: Returning");
            return;
        }

        if(counter.lastUsage().equals(username)) {
            System.out.println("User was last usage: " + username);
            return;
        }

        System.out.println("Storing lol: " + username);

        SlackChannel channel = slackSession.findChannelByName(botChannel);
        counter.storeUsage(username);
        slackSession.sendMessage(channel, botCommand);
    }
}