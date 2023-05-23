package com.netflix_clone.boardservice.repository.bannerRepository;

import com.netflix_clone.boardservice.repository.dto.reference.BannerDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerRepositoryCustom {
    PageImpl<BannerDto> banners(Pageable pageable);
    BannerDto banner(Long bannerNo);
    Boolean remove(Long bannerNo);
}
