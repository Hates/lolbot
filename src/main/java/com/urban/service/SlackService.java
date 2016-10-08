package com.urban.service;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import com.urban.command.AdminCommand;
import com.urban.command.InvalidCommand;
import com.urban.command.ReplyCommand;
import com.urban.command.StatsReplyCommand;

import org.springframework.stereotype.Component;

@Component
public class SlackService {
    public void registerListener(final SlackSession slackSession, final String botChannel, final String botCommand, final String statsCommand, final String adminUser) {
        SlackMessagePostedListener messageListener = new SlackMessagePostedListener() {
            @Override
            public void onEvent(SlackMessagePosted slackMessagePosted, SlackSession slackSession) {
                System.out.println("Processing Event: " + slackMessagePosted.getMessageContent());

                if (!isValidCommand(slackSession, slackMessagePosted)) {
                    return;
                }

                String messageContent = slackMessagePosted.getMessageContent().trim();
                if (messageContent.equals(botCommand)) {
                    ReplyCommand replyCommand = new ReplyCommand(botChannel, botCommand);
                    replyCommand.processCommand(slackMessagePosted, slackSession);
                } else if (messageContent.equals(statsCommand)) {
                    StatsReplyCommand statsReplyCommand = new StatsReplyCommand(botChannel, botCommand);
                    statsReplyCommand.processCommand(slackMessagePosted, slackSession);
                } else if (isAdminCommand(slackSession, slackMessagePosted)) {
                    AdminCommand adminCommand = new AdminCommand(botChannel, botCommand);
                    adminCommand.processCommand(slackMessagePosted, slackSession);
                } else {
                    InvalidCommand invalidCommand = new InvalidCommand(botChannel, botCommand);
                    invalidCommand.processCommand(slackMessagePosted, slackSession);
                }
            }

            boolean isValidCommand(SlackSession slackSession, SlackMessagePosted slackMessagePosted) {
                return isAdminCommand(slackSession, slackMessagePosted) || (notMessagePoster(slackSession, slackMessagePosted) && isListenerChannel(slackMessagePosted));
            }

            boolean isAdminCommand(SlackSession slackSession, SlackMessagePosted slackMessagePosted) {
                String senderUsername = slackMessagePosted.getSender().getUserName();
                Boolean isDirect = slackMessagePosted.getChannel().isDirect();

                return senderUsername.equals(adminUser) && isDirect;
            }

            // Ignore messages from our self.
            boolean notMessagePoster(SlackSession slackSession, SlackMessagePosted slackMessagePosted) {
                return !slackSession.sessionPersona().getId().equals(slackMessagePosted.getSender().getId());
            }

            // Ignore messages not from the listener channel.
            boolean isListenerChannel(SlackMessagePosted slackMessagePosted) {
                return slackMessagePosted.getChannel().getName().equals(botChannel);
            }
        };

        slackSession.addMessagePostedListener(messageListener);
    }
}
