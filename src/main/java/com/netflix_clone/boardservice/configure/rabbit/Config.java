package com.netflix_clone.boardservice.configure.rabbit;

import com.netflix_clone.boardservice.enums.Rabbit;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "rabbit_configuration")
public class Config {
    private final String queueName = Rabbit.Queue.BOARD.getName();

    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(topicExchangeName);
//    }

    @Bean
    public Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to( new TopicExchange("netflix-clone-user") ).with("profile.*");
    }

}