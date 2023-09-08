package com.foodjou.fjapp.rabbitmq;

import com.foodjou.fjapp.controllers.FoodController;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class RabbitMQProducer {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final RabbitTemplate rabbitTemplate;
    public void sendFoodLog(Food food, User currentUser){
        String message = String.format("Received a request to get food with ID: %d. Requester: %s. Timestamp: %s",
                food.getId(), currentUser.getUsername(), LocalDateTime.now());
        LOGGER.info(message);
        String exchange = "exchange_demo";
        String routingKey = "routing_key_demo";
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
