package com.netflix_clone.boardservice.repository.dto.reference;

import com.netflix_clone.boardservice.repository.domains.BoardProfile;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.netflix_clone.boardservice.repository.domains.Comment} entity
 */
@Data
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Long commentNo;
    private ProfileDto profile;
    private Long contentsNo;
    private String comments;
    private Double star;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private Boolean isDeleted;

    @QueryProjection
    public CommentDto(Long commentNo, ProfileDto profile, Long contentsNo, String comments, Double star, LocalDateTime regDate, LocalDateTime modifyDate) {
        this.commentNo = commentNo;
        this.profile = profile;
        this.contentsNo = contentsNo;
        this.comments = comments;
        this.star = star;
        this.regDate = regDate;
        this.modifyDate = modifyDate;
    }
}