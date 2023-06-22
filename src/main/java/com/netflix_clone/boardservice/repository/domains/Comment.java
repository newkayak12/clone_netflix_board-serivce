package com.netflix_clone.boardservice.repository.domains;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "comment")
@DynamicInsert
@DynamicUpdate
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentNo", columnDefinition = "BIGINT(20)")
    private Long commentNo;

    @ManyToOne
    @JoinColumn(name = "profileNo", columnDefinition = "BIGINT(20)")
    private BoardProfile profile;
    @Column(name = "contentsNo", nullable = false, columnDefinition = "BIGINT(20)")
    private Long contentsNo;
    @Column(name = "comments", nullable = false, columnDefinition = "VARCHAR(500)")
    private String comments;
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
