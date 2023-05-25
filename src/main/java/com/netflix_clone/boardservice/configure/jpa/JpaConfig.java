package com.netflix_clone.boardservice.configure.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration(value = "jpa_configuration")
@EnableJpaAuditing
public class JpaConfig {
}
