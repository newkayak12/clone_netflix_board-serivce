package com.netflix_clone.boardservice.configure.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component(value = "rabbit_consumer")
@RequiredArgsConstructor
@Slf4j
public class RabbitConsumer {
    private final ObjectMapper objectMapper;

    @RabbitListener(queues =  "netflix-clone-profile")
    public void RabbitReceiver(final Message message) throws IOException {
        log.warn("MESSAGE {}", message);
        byte[] body = message.getBody();
        Map result = objectMapper.readValue(body, Map.class);
        log.warn("BODY {}", result);
        log.warn("TEST Value {}", result.get("Hello"));
    }

}