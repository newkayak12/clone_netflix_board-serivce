package com.netflix_clone.boardservice.component.configure.jpa;

import com.netflix_clone.boardservice.component.configure.ConfigMsg;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration(value = "jpa_configuration")
@EnableJpaAuditing
public class Config {
    public Config() {
        ConfigMsg.msg("Jpa");
    }
}
