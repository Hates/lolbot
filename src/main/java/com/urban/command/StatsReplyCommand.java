package com.urban.command;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.urban.usage.UsageCounter;

public class StatsReplyCommand extends BotCommand {
    public StatsReplyCommand(String botChannel, String botCommand) {
        super(botChannel, botCommand);
    }

    @Override
    public void processCommand(SlackMessagePosted slackMessagePosted, SlackSession slackSession) {
        processCommand(slackSession);
    }

    public void processCommand(SlackSession slackSession) {
        System.out.println("Printing stats...");

        UsageCounter counter = new UsageCounter();
        SlackChannel channel = slackSession.findChannelByName(botChannel);
        String stats = counter.stats();

        slackSession.sendMessage(channel, stats);
    }
}