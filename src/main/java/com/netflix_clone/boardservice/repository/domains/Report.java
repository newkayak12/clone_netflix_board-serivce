package com.netflix_clone.boardservice.repository.domains;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table
@Entity
@DynamicUpdate
@DynamicInsert
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportNo", columnDefinition = "BIGINT(20)")
    private Long reportNo;

    @ManyToOne
    @JoinColumn(name = "commentNo", columnDefinition = "BIGINT(20)")
    private Comment comment;

    @OneToOne
    @JoinColumn(name = "profileNo", columnDefinition = "BIGINT(20)")
    private BoardProfile profile;

    @Column(name = "reportDate", columnDefinition = "DATETIME default CURRENT_TIMESTAMP()")
    private LocalDateTime reportDate;

    @PrePersist
    public void reportDate() {
        this.reportDate = LocalDateTime.now();
    }
}
