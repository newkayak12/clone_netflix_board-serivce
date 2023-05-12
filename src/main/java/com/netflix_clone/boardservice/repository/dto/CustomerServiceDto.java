package com.netflix_clone.boardservice.repository.dto;

import com.netflix_clone.boardservice.enums.CsType;
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
}
