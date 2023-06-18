package com.netflix_clone.boardservice.controller;

import com.netflix_clone.boardservice.component.exception.CommonException;
import com.netflix_clone.boardservice.repository.dto.reference.CommentDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @GetMapping(value = "/")
    public ResponseEntity<PageImpl<CommentDto>> comments(@ModelAttribute PageableRequest request){
        return new ResponseEntity<>(service.comments(request), HttpStatus.OK);
    }
    @GetMapping(value = "/{commentNo:[\\d]+}")
    public ResponseEntity<CommentDto> comment(@PathVariable Long commentNo) throws CommonException {
        return new ResponseEntity<>(service.comment(commentNo), HttpStatus.OK);
    }
    @PostMapping(value = "/")
    public ResponseEntity<Boolean> save(@RequestBody CommentDto comment) {
        return new ResponseEntity<>(service.save(comment), HttpStatus.OK);
    }
    @DeleteMapping(value = "/{commentNo:[\\d]+}")
    public ResponseEntity<Boolean> remove(@PathVariable Long commentNo) {
        return new ResponseEntity<>(service.remove(commentNo), HttpStatus.OK);
    }
}
