package com.netflix_clone.boardservice.repository.domains;


import com.netflix_clone.boardservice.repository.dto.reference.FileDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "board_profile")
public class BoardProfile {
    @Id
    @Column(name = "profileNo", columnDefinition = "BIGINT(20)")
    private Long profileNo;
    @Column(name = "profileName", columnDefinition = "VARCHAR(50)")
    private String profileName;


    @OneToMany(mappedBy = "profile")
    private List<Comment> comments;
    @Transient
    private FileDto image;
}
