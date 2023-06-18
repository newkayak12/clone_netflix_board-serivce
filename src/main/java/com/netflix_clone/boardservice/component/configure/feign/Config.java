package com.netflix_clone.boardservice.component.configure.feign;

import com.netflix_clone.boardservice.component.configure.ConfigMsg;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2023-05-12
 * Project board-service
 */
@Configuration (value = "feign_configuration")
@EnableFeignClients
public class Config {
    public Config() {
        ConfigMsg.msg("Feign");
    }
}
