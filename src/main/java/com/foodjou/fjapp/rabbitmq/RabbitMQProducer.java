package com.foodjou.fjapp.rabbitmq;

import com.foodjou.fjapp.controllers.FoodController;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
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
        String textMessage = String.format("Received a request to get food with ID: %d. Requester: %s. Timestamp: %s",
                food.getId(), currentUser.getUsername(), LocalDateTime.now());
        LOGGER.info(textMessage);
        String exchange = "exchange_demo";
        String routingKey = "routing_key_demo";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message(textMessage.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
