package com.netflix_clone.boardservice.repository.domains;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "notice")
@Entity
@ToString
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeNo", columnDefinition = "BIGINT(20)")
    private Long noticeNo;
    @Column(name = "title", columnDefinition = "VARCHAR(250)", nullable = false)
    private String title;
    @Column(name = "content", columnDefinition = "VARCHAR(600)", nullable = false)
    private String content;
    @Column(name = "regDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()")
    private LocalDateTime regDate;


    @PrePersist
    public void whenInsert() {
        this.regDate = LocalDateTime.now();
    }
}
