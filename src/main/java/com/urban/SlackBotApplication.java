package com.urban;

import com.urban.services.BotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlackBotApplication implements CommandLineRunner {

    @Autowired
    private BotService botService;

    @Override
    public void run(String... args) {
        botService.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(SlackBotApplication.class, args);
    }
}