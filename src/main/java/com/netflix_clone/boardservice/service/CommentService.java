package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.component.configure.feign.ImageFeign;
import com.netflix_clone.boardservice.component.enums.FileType;
import com.netflix_clone.boardservice.component.exception.BecauseOf;
import com.netflix_clone.boardservice.component.exception.CommonException;
import com.netflix_clone.boardservice.repository.commentRepository.CommentRepository;
import com.netflix_clone.boardservice.repository.domains.Comment;
import com.netflix_clone.boardservice.repository.dto.reference.CommentDto;
import com.netflix_clone.boardservice.repository.dto.reference.FileDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.reference.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CommentService {
    private final CommentRepository repository;
    private final ImageFeign imageFeign;
    private final ModelMapper mapper;


    @Transactional(readOnly = true)
    public PageImpl<CommentDto> comments(PageableRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        return (PageImpl<CommentDto>)  repository.comments(pageable, request).map(comment -> {

//            CommentDto commentResult  = mapper.map(queryResult.get(comment), CommentDto.class);
//            ProfileDto profileDto = mapper.map(queryResult.get(boardProfile), ProfileDto.class);

            ProfileDto profileDto = comment.getProfile();
            FileDto image = Optional.ofNullable(imageFeign.file(profileDto.getProfileNo(), FileType.PROFILE).getBody())
                    .orElseGet(() -> null);
            profileDto.setImage(image);
            comment.setProfile(profileDto);
            return comment;
        });
    }

    @Transactional(readOnly = true)
    public CommentDto comment(Long commentNo) throws CommonException {
        return Optional
                .ofNullable(repository.comment(commentNo))
                .map(comment -> {
                    ProfileDto profileDto = comment.getProfile();
                    FileDto image = Optional.ofNullable(imageFeign.file(profileDto.getProfileNo(), FileType.PROFILE).getBody())
                                            .orElseGet(() -> null);
                    profileDto.setImage(image);
                    comment.setProfile(profileDto);
                    return comment;
                })
                .orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
    }

    public Boolean save(CommentDto request) {
        Comment comment = mapper.map(request, Comment.class);
        return Optional.ofNullable(repository.save(comment))
                       .map(Objects::nonNull)
                       .orElseGet(() -> false);
    }

    public Boolean remove(Long commentNo) {
        return repository.remove(commentNo);
    }
}
