package com.urban.commands;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

public abstract class AbstractBotCommand {
    String botChannel;
    String botCommand;

    public AbstractBotCommand(String botChannel, String botCommand) {
        this.botChannel = botChannel;
        this.botCommand = botCommand;
    }

    abstract void processCommand(SlackMessagePosted slackMessagePosted, SlackSession slackSession);
}