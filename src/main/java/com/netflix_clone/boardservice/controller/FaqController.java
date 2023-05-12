package com.netflix_clone.boardservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2023-05-12
 * Project board-service
 */
@RestController
@RequestMapping(value = "/api/v1/board/faq")
public class FaqController {

    @GetMapping(value = "s/")
    public String faqs(){
        return "faqs";
    }

}
