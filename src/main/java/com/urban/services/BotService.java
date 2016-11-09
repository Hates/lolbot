package com.urban.services;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.urban.entities.Usage;
import com.urban.entities.UsageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BotService {
    private final Logger logger = LoggerFactory.getLogger(BotService.class);

    @Autowired
    private UsageRepository usageRepository;

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

        System.out.println(botChannel);

        try {
            session.connect();
        } catch(IOException exception) {
            logger.error("Could not connect to Slack!");
        }

        usageRepository.save(new Usage("hello"));
        
        slackService.registerListener(
                session,
                botChannel,
                botCommand,
                statsCommand,
                adminUser
        );
    }
}
