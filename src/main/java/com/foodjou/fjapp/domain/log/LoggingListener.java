package com.foodjou.fjapp.domain.log;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LoggingListener {
    Logger logger = LoggerFactory.getLogger(LoggingListener.class);


    @PrePersist
    public void logNewUserAttempt(Object entity) {
        logger.info("Try adding Entity at "+ new Date() +" : "+ entity.getClass().getName());
    }

    @PostPersist
    public void logNewEntityAdded(Object entity) {
        logger.info(entity.toString());
    }

    @PreRemove
    public void logEntityRemovalAttempt(Object entity) {
        logger.info("Try removing Entity at "+ new Date() +" : "+ entity.getClass().getName());
    }

    @PostRemove
    public void logEntityRemoval(Object entity) {
        logger.info(entity.toString());
    }

    @PreUpdate
    public void logEntityUpdateAttempt(Object entity) {
        logger.info("Try updating Entity at "+ new Date() +" : "+ entity.getClass().getName());
    }

    @PostUpdate
    public void logEntityUpdate(Object entity) {
        logger.info(entity.toString());
    }
}
