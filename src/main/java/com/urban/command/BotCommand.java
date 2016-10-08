package com.urban.command;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

public abstract class BotCommand {
    String botChannel;
    String botCommand;

    public BotCommand(String botChannel, String botCommand) {
        this.botChannel = botChannel;
        this.botCommand = botCommand;
    }

    abstract void processCommand(SlackMessagePosted slackMessagePosted, SlackSession slackSession);
}
