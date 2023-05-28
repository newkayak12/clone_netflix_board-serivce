package com.netflix_clone.boardservice.controller;

import com.netflix_clone.boardservice.repository.dto.request.SaveReport;
import com.netflix_clone.boardservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @PostMapping(value = "/")
    public ResponseEntity<Boolean> report(@ModelAttribute SaveReport saveReport){
        return new ResponseEntity<Boolean>(service.report(saveReport), HttpStatus.OK);
    }

}
