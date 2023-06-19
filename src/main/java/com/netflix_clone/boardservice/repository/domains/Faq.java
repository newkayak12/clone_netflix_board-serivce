package com.netflix_clone.boardservice.repository.domains;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created on 2023-05-11
 * Project board-service
 */
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "faq")
@Entity
@DynamicUpdate
@DynamicInsert
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faqNo", columnDefinition = "BIGINT(20)")
    private Long faqNo;
    @Column(name = "question", columnDefinition = "VARCHAR(500)")
    private String question;
    @Column(name = "answer", columnDefinition = "VARCHAR(500)")
    private String answer;
    @Column(name = "regDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()", updatable = false)
    private LocalDateTime regDate;

}
