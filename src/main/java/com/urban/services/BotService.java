package com.urban.services;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BotService {
    @Value("${slackApiKey}")
    private String slackApiKey;

    @Value("${botChannel}")
    private String botChannel;

    @Value("${botCommand}")
    private String botCommand;

    @Value("${statsCommand}")
    private String statsCommand;

    @Value("${adminUser}")
    private String adminUser;

    public void start() {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackApiKey);
        SlackService slackService = new SlackService();

        try {
            session.connect();
        } catch(IOException exception) {
            System.out.println("Could not connect");
        }

        slackService.registerListener(session, botChannel, botCommand, statsCommand, adminUser);
    }
}
