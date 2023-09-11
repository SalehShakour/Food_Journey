package com.foodjou.fjapp.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.rabbitmq.CommonDTOs.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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

    private final RabbitTemplate rabbitTemplate;

    public void sendFoodLog(Food food, User currentUser) {
        String routingKey = "routing_key_food";
        sendLog(routingKey, new ViewedEntityLogDTO(food.getId()
                ,currentUser.getUsername(),
                LocalDateTime.now()));

    }


    public void sendRestaurantLog(Long restaurantId, User currentUser) {
        String routingKey = "routing_key_restaurant";
        sendLog(routingKey, new ViewedEntityLogDTO(restaurantId
                ,currentUser.getUsername(),
                LocalDateTime.now()));

    }


    private void sendLog(String routingKey, Object payload) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        byte[] payloadBytes;
        try {
            payloadBytes = mapper.writeValueAsBytes(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message(payloadBytes, messageProperties);
        rabbitTemplate.convertAndSend("myTopicExchange", routingKey, message);
    }

}

