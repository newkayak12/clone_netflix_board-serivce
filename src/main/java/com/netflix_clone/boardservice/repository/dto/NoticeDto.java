package com.netflix_clone.boardservice.repository.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.netflix_clone.boardservice.repository.domains.Notice} entity
 */
@Data
public class NoticeDto implements Serializable {
    private final Long noticeNo;
    private final String title;
    private final String content;
    private final LocalDateTime regDate;
}