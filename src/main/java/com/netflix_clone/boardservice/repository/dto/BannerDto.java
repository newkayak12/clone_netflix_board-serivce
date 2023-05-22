package com.netflix_clone.boardservice.repository.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.netflix_clone.boardservice.repository.domains.Banner} entity
 */
@Data
public class BannerDto implements Serializable {
    private final Long bannerNo;
    private final String title;
    private final String url;
}