package com.foodjou.fjapp.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("myTopicExchange");
    }

    @Bean
    public Queue foodQueue() {
        return new Queue("foodQueue");
    }

    @Bean
    public Queue restaurantQueue() {
        return new Queue("restaurantQueue");
    }

    @Bean
    public Queue orderQueue() {
        return new Queue("orderQueue");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("userQueue");
    }

    @Bean
    public Binding foodBinding(Queue foodQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(foodQueue).to(topicExchange).with("my.routing.key.food");
    }

    @Bean
    public Binding restaurantBinding(Queue restaurantQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(restaurantQueue).to(topicExchange).with("my.routing.key.restaurant");
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(orderQueue).to(topicExchange).with("my.routing.key.order");
    }

    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(userQueue).to(topicExchange).with("my.routing.key.user");
    }
}
