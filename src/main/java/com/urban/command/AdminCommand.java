package com.urban.command;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

public class AdminCommand extends BotCommand {
    public AdminCommand(String botChannel, String botCommand) {
        super(botChannel, botCommand);
    }

    public void processCommand(SlackMessagePosted slackMessagePosted, SlackSession slackSession) {
        System.out.println("Admin command...");

        SlackChannel channel = slackSession.findChannelByName(botChannel);
        slackSession.sendMessage(channel, slackMessagePosted.getMessageContent());
    }
}