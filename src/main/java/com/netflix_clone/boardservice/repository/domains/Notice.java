package com.netflix_clone.boardservice.repository.domains;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "notice")
@Entity
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
