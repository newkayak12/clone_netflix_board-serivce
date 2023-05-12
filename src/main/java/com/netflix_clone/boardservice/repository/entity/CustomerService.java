package com.netflix_clone.boardservice.repository.entity;

import com.netflix_clone.boardservice.enums.CsType;
import com.sun.mail.imap.protocol.BODY;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created on 2023-05-11
 * Project board-service
 */
@Table(name = "customerService")
@Entity
public class CustomerService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "csNo", columnDefinition = "BIGINT(20)")
    private Long csNo;

    @Column(name = "type", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private CsType type;

    @Column(name = "email", columnDefinition = "VARCHAR(200)")
    private String email;
    @Column(name = "question", columnDefinition = "VARCHAR(500)")
    private String question;
    @Column(name = "answer", columnDefinition = "VARCHAR(500)")
    private String answer;

    @Column(name = "askedDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()")
    private LocalDateTime askedDate;
    @Column(name = "replyDate", columnDefinition = "DATETIME")
    private LocalDateTime replyDate;
    @Column(name = "isReplSent", columnDefinition = "BIT(1) default FALSE")
    private Boolean isReplSent;

}
