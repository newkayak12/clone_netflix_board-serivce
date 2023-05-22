package com.netflix_clone.boardservice.repository.domains;

import javax.persistence.*;

@Table(name = "banner")
@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerNo;

    @Column(name = "title", columnDefinition = "VARCHAR(250)", nullable = false)
    private String title;
    @Column(name = "url", columnDefinition = "VARCHAR(600)", nullable = true)
    private String url;
}
