package com.netflix_clone.boardservice.repository.dto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.netflix_clone.boardservice.repository.domains.Comment} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Long commentNo;
    private Long profileNo;
    private Long contentsNo;
    private String comment;
    private Double star;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private Boolean isDeleted;
}