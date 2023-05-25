package com.netflix_clone.boardservice.repository.domains;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "board-profile")
public class BoardProfile {
    @Id
    @Column(name = "profileNo", columnDefinition = "BIGINT(20)")
    private Long profileNo;
    @Column(name = "profileName", columnDefinition = "VARCHAR(50)")
    private String profileName;
}
