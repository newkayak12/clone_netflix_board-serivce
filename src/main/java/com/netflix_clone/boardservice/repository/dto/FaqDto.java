package com.netflix_clone.boardservice.repository.dto;

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
public class FaqDto implements Serializable {
    private Long faqNo;
    private String question;
    private String answer;
    private LocalDateTime regDate;
}
