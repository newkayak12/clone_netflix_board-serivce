package com.netflix_clone.boardservice.repository.dto.reference;

import com.netflix_clone.boardservice.enums.CsType;
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
@AllArgsConstructor
@NoArgsConstructor
public class CustomerServiceDto implements Serializable {
    private Long csNo;
    private CsType type;
    private String email;
    private String question;
    private String answer;
    private LocalDateTime askedDate;
    private LocalDateTime replyDate;
    private Boolean isReplSent;


    @QueryProjection
    public CustomerServiceDto(Long csNo, CsType type, String email, String question, String answer, LocalDateTime askedDate, LocalDateTime replyDate, Boolean isReplSent) {
        this.csNo = csNo;
        this.type = type;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.askedDate = askedDate;
        this.replyDate = replyDate;
        this.isReplSent = isReplSent;
    }
}
