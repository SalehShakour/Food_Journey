package com.foodjou.fjapp.rabbitmq;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.User;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RabbitMQProducer {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;


    public void sendFoodLog(Food food, User currentUser){
        String textMessage = String.format("Received a request to get food with ID: %d. Requester: %s. Timestamp: %s",
                food.getId(), currentUser.getUsername(), LocalDateTime.now());
        LOGGER.info(textMessage);
        String routingKey = "routing_key_food";
        sendLog(routingKey, textMessage);
    }

    public void sendOrderLog(Order order, User currentUser){
        String textMessage = String.format("Received a request to get order with ID: %d. Requester: %s. Timestamp: %s",
                order.getId(), currentUser.getUsername(), LocalDateTime.now());
        LOGGER.info(textMessage);
        String routingKey = "routing_key_order";
        sendLog(routingKey, textMessage);
    }

    public void sendUserLog(User user){
        String textMessage = String.format("Received a request to get user with ID: %d. Timestamp: %s",
                user.getId(), LocalDateTime.now());
        LOGGER.info(textMessage);
        String routingKey = "routing_key_user";
        sendLog(routingKey, textMessage);
    }

    public void sendRestaurantLog(Long restaurantId, User currentUser){
        String textMessage = String.format("Received a request to get restaurant with ID: %d. Requester: %s. Timestamp: %s",
                restaurantId, currentUser.getUsername(), LocalDateTime.now());
        LOGGER.info(textMessage);
        String routingKey = "routing_key_restaurant";
        sendLog(routingKey, textMessage);
    }

    private void sendLog(String routingKey, String textMessage){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message(textMessage.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("myTopicExchange", routingKey, message);
    }
}

