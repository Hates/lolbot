package com.urban.command;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.urban.command.penalty.RemoveUsageAndTimeoutPenalty;
import com.urban.command.penalty.RemoveUsagePenalty;
import com.urban.usage.UsageCounter;

public class InvalidCommand extends BotCommand {
    private UsageCounter counter = new UsageCounter();
    private RemoveUsagePenalty penalty = new RemoveUsageAndTimeoutPenalty();

    public InvalidCommand(String botChannel, String botCommand) {
        super(botChannel, botCommand);
    }

    @Override
    public void processCommand(SlackMessagePosted slackMessagePosted, SlackSession slackSession) {
        System.out.println("Invalid command...");

        String username = slackMessagePosted.getSender().getUserName();
        if (username.contains("lol")) {
            return;
        }

        SlackChannel channel = slackSession.findChannelByName(botChannel);

        int totalUsage = counter.fetchTotalUsage(username);
        int dailyUsage = counter.fetchDailyUsage(username);
        if (totalUsage > 0 || dailyUsage > 0) {
            slackSession.sendMessage(channel, penalty.penaltyMessage(username));
            penalty.addPenalty(counter, username);
        } else {
            slackSession.sendMessage(channel, penalty.noPenaltyMessage(username));
        }
    }
}