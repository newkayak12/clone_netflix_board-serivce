package com.netflix_clone.boardservice.controller;

import com.netflix_clone.boardservice.component.exception.CommonException;
import com.netflix_clone.boardservice.component.wrap.RestPage;
import com.netflix_clone.boardservice.repository.dto.reference.FaqDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveFaqRequest;
import com.netflix_clone.boardservice.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(key = "{#pageable.page}", value = {"faqs"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<RestPage<FaqDto>> faqs(@ModelAttribute PageableRequest pageable){
        return new ResponseEntity(service.faqs(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{faqNo:[\\d]+}")
    @Cacheable(key = "#faqNo", value = {"faq"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<FaqDto> faq(@PathVariable Long faqNo) throws CommonException {
        return new ResponseEntity<>(service.faq(faqNo), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    @CachePut(key = "#faqNo", value = {"faq"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<Boolean> save(@RequestBody SaveFaqRequest request){
        return new ResponseEntity<>(service.save(request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{faqNo:[\\d]+}")
    @CacheEvict(key = "#faqNo", value = {"faq", "faqs"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<Boolean> remove(@PathVariable Long faqNo) {
        return new ResponseEntity<>(service.remove(faqNo), HttpStatus.OK);
    }

}
