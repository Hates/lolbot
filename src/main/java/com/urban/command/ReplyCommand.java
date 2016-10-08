package com.urban.command;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.urban.usage.UsageCounter;

public class ReplyCommand extends BotCommand {
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

        UsageCounter counter = new UsageCounter();

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