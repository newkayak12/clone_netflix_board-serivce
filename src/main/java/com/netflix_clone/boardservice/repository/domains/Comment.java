package com.netflix_clone.boardservice.repository.domains;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentNo", columnDefinition = "BIGINT(20)")
    private Long commentNo;

    @Column(name = "profileNo", nullable = true, columnDefinition = "BIGINT(20)")
    private Long profileNo;
    @Column(name = "contentsNo", nullable = false, columnDefinition = "BIGINT(20)")
    private Long contentsNo;
    @Column(name = "comment", nullable = false, columnDefinition = "VARCHAR(500)")
    private String comment;
    @Column(name = "star", columnDefinition = "DOUBLE default 0.0")
    private Double star;
    @Column(name = "regDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()")
    private LocalDateTime regDate;
    @Column(name = "modifyDate", columnDefinition = "DATETIME")
    private LocalDateTime modifyDate;

    @Column(name = "isDeleted", columnDefinition = "BIT(1) default FALSE")
    private Boolean isDeleted;

    @PrePersist
    public void write(){
        this.regDate = LocalDateTime.now();
    }

    @PreUpdate
    public void modify(){
        this.modifyDate = LocalDateTime.now();
    }

}
