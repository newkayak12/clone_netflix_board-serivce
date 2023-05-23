package com.netflix_clone.boardservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * A DTO for the {@link com.netflix_clone.boardservice.repository.domains.Banner} entity
 */
@Data
@NoArgsConstructor
public class BannerDto implements Serializable {
    private Long bannerNo;
    private String title;
    private String url;

    @Transient
    private FileDto image;



    @QueryProjection
    public BannerDto(Long bannerNo, String title, String url) {
        this.bannerNo = bannerNo;
        this.title = title;
        this.url = url;
    }
}