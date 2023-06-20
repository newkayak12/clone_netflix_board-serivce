package com.netflix_clone.boardservice.component.configure.emailSender;

import com.github.newkayak12.config.SimpleEmailSender;
import com.github.newkayak12.enums.Vendor;
import com.netflix_clone.boardservice.component.configure.ConfigMsg;
import com.netflix_clone.boardservice.component.constants.Constants;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Created on 2023-05-12
 * Project board-service
 */
@Configuration (value = "emailSender_configuration")
@DependsOn(value = {"constants"})
public class Config {
    public Config() {
        ConfigMsg.msg("EmailSender");
    }

    @Bean
    public SimpleEmailSender simpleEmailSender() {
        return new SimpleEmailSender(Vendor.GMAIL, "email/", Constants.EMAIL, Constants.EMAIL_KEY);
    }
}
