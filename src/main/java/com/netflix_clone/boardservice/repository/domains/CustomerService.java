package com.netflix_clone.boardservice.repository.domains;

import com.netflix_clone.boardservice.component.enums.CsType;
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
@Table(name = "customerService")
@Entity
@DynamicInsert
@DynamicUpdate
public class CustomerService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "csNo", columnDefinition = "BIGINT(20)")
    private Long csNo;

    @Column(name = "type", columnDefinition = "VARCHAR(50)", updatable = false)
    @Enumerated(EnumType.STRING)
    private CsType type;

    @Column(name = "email", columnDefinition = "VARCHAR(200)", updatable = false)
    private String email;
    @Column(name = "question", columnDefinition = "VARCHAR(500)", updatable = false)
    private String question;
    @Column(name = "answer", columnDefinition = "VARCHAR(500)")
    private String answer;

    @Column(name = "askedDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()", updatable = false)
    private LocalDateTime askedDate;
    @Column(name = "replyDate", columnDefinition = "DATETIME")
    private LocalDateTime replyDate;
    @Column(name = "isReplSent", columnDefinition = "BIT(1) default FALSE")
    private Boolean isReplSent;

}
