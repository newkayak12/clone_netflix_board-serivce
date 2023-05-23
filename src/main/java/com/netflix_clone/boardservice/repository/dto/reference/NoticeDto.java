package com.netflix_clone.boardservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.netflix_clone.boardservice.repository.domains.Notice} entity
 */
@Data
@NoArgsConstructor
@ToString
public class NoticeDto implements Serializable {
    private Long noticeNo;
    private String title;
    private String content;
    private LocalDateTime regDate;


    @QueryProjection
    public NoticeDto(Long noticeNo, String title, String content, LocalDateTime regDate) {
        this.noticeNo = noticeNo;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
    }
}