package com.foodjou.fjapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public Queue queue(){
        return new Queue(queue);
    }
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with("saleh_routing_key");
    }
}
