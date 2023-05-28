package com.netflix_clone.boardservice.repository.commentRepository;

import com.netflix_clone.boardservice.repository.dto.reference.CommentDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    PageImpl<CommentDto> comments(Pageable request, PageableRequest parameter);
    CommentDto comment(Long commentNo);
    Boolean remove(Long commentNo);
}
