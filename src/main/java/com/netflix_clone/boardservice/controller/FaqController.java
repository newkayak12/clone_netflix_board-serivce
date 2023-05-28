package com.netflix_clone.boardservice.controller;

import com.netflix_clone.boardservice.exception.CommonException;
import com.netflix_clone.boardservice.repository.dto.reference.FaqDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveFaqRequest;
import com.netflix_clone.boardservice.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2023-05-12
 * Project board-service
 */
@RestController
@RequestMapping(value = "/api/v1/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService service;

    @GetMapping(value = "/")
    public ResponseEntity<List<FaqDto>> faqs(@ModelAttribute PageableRequest pageable){
        return new ResponseEntity(service.faqs(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{faqNo:[\\d]+}/")
    public ResponseEntity<FaqDto> faq(@PathVariable Long faqNo) throws CommonException {
        return new ResponseEntity<>(service.faq(faqNo), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Boolean> save(@RequestBody SaveFaqRequest request){
        return new ResponseEntity<>(service.save(request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{faqNo:[\\d+]/")
    public ResponseEntity<Boolean> remove(@PathVariable Long faqNo) {
        return new ResponseEntity<>(service.remove(faqNo), HttpStatus.OK);
    }

}
