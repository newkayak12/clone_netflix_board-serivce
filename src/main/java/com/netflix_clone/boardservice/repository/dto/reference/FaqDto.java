package com.netflix_clone.boardservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2023-05-11
 * Project board-service
 */
@Data
@NoArgsConstructor
public class FaqDto implements Serializable {
    private Long faqNo;
    private String question;
    private String answer;
    private LocalDateTime regDate;

    @QueryProjection
    public FaqDto(Long faqNo, String question, String answer, LocalDateTime regDate) {
        this.faqNo = faqNo;
        this.question = question;
        this.answer = answer;
        this.regDate = regDate;
    }
}
