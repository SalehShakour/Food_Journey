package com.foodjou.fjapp.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    public Queue queue(){
        String queue = "queue_demo";
        return new Queue(queue);
    }
    public TopicExchange exchange(){
        String exchange = "exchange_demo";
        return new TopicExchange(exchange);
    }
    public Binding binding(){
        String routingKey = "routing_key_demo";
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }
}
