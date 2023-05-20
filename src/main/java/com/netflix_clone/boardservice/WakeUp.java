package com.netflix_clone.boardservice;

import com.netflix_clone.boardservice.repository.CustomerSerivceRepository.CustomerServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created on 2023-05-04
 * Project user-service
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WakeUp {
    private final CustomerServiceRepository repository;

    @EventListener(value = {ApplicationReadyEvent.class})
    public void message(){
        log.warn("{} is ready", repository.wakeUpMsg("\n" +
                "  ____                          _                                 _            \n" +
                " | __ )   ___    __ _  _ __  __| |        ___   ___  _ __ __   __(_)  ___  ___ \n" +
                " |  _ \\  / _ \\  / _` || '__|/ _` | _____ / __| / _ \\| '__|\\ \\ / /| | / __|/ _ \\\n" +
                " | |_) || (_) || (_| || |  | (_| ||_____|\\__ \\|  __/| |    \\ V / | || (__|  __/\n" +
                " |____/  \\___/  \\__,_||_|   \\__,_|       |___/ \\___||_|     \\_/  |_| \\___|\\___|"));
    }
}
