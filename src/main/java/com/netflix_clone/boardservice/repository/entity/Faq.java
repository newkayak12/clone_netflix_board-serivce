package com.netflix_clone.boardservice.repository.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created on 2023-05-11
 * Project board-service
 */
@Table(name = "faq")
@Entity
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faqNo", columnDefinition = "BIGINT(20)")
    private Long faqNo;
    @Column(name = "question", columnDefinition = "VARCHAR(500)")
    private String question;
    @Column(name = "answer", columnDefinition = "VARCHAR(500)")
    private String answer;
    @Column(name = "regDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()")
    private LocalDateTime regDate;

}
