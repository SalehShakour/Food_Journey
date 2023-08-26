package com.foodjou.fjapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Scheduled(cron = "0/5 * * * * ?")
    public void logTask() {
        Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
        logger.info("Enjoy Food Journey :))");
    }

}
