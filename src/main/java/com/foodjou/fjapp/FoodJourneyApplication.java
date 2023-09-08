package com.foodjou.fjapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodJourneyApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(FoodJourneyApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(FoodJourneyApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        // The CommandLineRunner will be used for future features :))
        LOG.info("EXECUTING : command line runner");
        Map<String, String> argMap = new HashMap<>();
        for (String arg : args) {
            String[] splitArg = arg.split("=", 2);
            if (splitArg.length == 2) {
                argMap.put(splitArg[0], splitArg[1]);
            }
        }

        if (argMap.containsKey("myArg")) {
            String myArgValue = argMap.get("myArg");
            LOG.info("myArg value: {}", myArgValue);
        }
    }
}