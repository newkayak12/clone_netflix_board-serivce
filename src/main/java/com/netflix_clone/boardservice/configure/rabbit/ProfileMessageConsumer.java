package com.netflix_clone.boardservice.configure.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.boardservice.repository.dto.reference.ProfileDto;
import com.netflix_clone.boardservice.service.BoardProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component(value = "rabbit_consumer")
@RequiredArgsConstructor
@Slf4j
public class ProfileMessageConsumer {
    private final ObjectMapper objectMapper;
    private final BoardProfileService service;

    @RabbitListener(queues = "netflix-clone-board")
    public void profileSaveRabbit(final Message message) throws IOException {
        ProfileDto profileDto = objectMapper.readValue(message.getBody(), ProfileDto.class);
        service.saveProfile(profileDto);
    }


}