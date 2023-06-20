package com.netflix_clone.boardservice.controller;

import com.netflix_clone.boardservice.repository.domains.Banner;
import com.netflix_clone.boardservice.repository.domains.CustomerService;
import com.netflix_clone.boardservice.repository.dto.reference.CustomerServiceDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveCsRequest;
import com.netflix_clone.boardservice.service.CustomerServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cs")
@RequiredArgsConstructor
public class CustomerServiceController {
    private final CustomerServiceService service;

    @GetMapping(value = "/")
    public ResponseEntity customerServices(@ModelAttribute PageableRequest request) {
        return new ResponseEntity<PageImpl>(service.customerServices(request), HttpStatus.OK);
    }
    @GetMapping(value = "/{csNo:[\\d]+}")
    public ResponseEntity customerService(@PathVariable Long csNo){
        return new ResponseEntity<CustomerServiceDto>(service.customerService(csNo), HttpStatus.OK);
    }
    @PostMapping(value = "/")
    public ResponseEntity save(@RequestBody SaveCsRequest request){
        return new ResponseEntity<Boolean>(service.save(request), HttpStatus.OK);
    }

    @PatchMapping(value = "/reply")
    public ResponseEntity reply(@ModelAttribute SaveCsRequest request){
        return ResponseEntity.ok(service.reply(request));
    }

    @DeleteMapping(value = "/{csNo:[\\d]+}")
    public ResponseEntity remove(@PathVariable Long csNo){
        return new ResponseEntity<Boolean>(service.remove(csNo), HttpStatus.OK);
    }

}
