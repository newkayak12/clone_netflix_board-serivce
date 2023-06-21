package com.netflix_clone.boardservice.repository.dto.reference;

import com.netflix_clone.boardservice.repository.domains.BoardProfile;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.netflix_clone.boardservice.repository.domains.Report} entity
 */
@Data
@NoArgsConstructor
public class ReportDto implements Serializable {
    private Long reportNo;
    private CommentDto comment;
    private ProfileDto profile;
    private LocalDateTime reportDate;


    @QueryProjection
    public ReportDto(Long reportNo, CommentDto comment, ProfileDto profile, LocalDateTime reportDate) {
        this.reportNo = reportNo;
        this.comment = comment;
        this.profile = profile;
        this.reportDate = reportDate;
    }
}