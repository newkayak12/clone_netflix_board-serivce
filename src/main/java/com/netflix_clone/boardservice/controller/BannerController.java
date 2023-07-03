package com.netflix_clone.boardservice.controller;

import com.netflix_clone.boardservice.component.exception.CommonException;
import com.netflix_clone.boardservice.component.wrap.RestPage;
import com.netflix_clone.boardservice.repository.dto.reference.BannerDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveBannerRequest;
import com.netflix_clone.boardservice.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/banner")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService service;

    @GetMapping(value = "/")
    @Cacheable(key = "{#request.page, #request.limit}", value = {"banners"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<RestPage<BannerDto>> banners(PageableRequest request) {
        return new ResponseEntity<>(service.banners(request), HttpStatus.OK);
    }

    @GetMapping(value = "/{bannerNo:[\\d]+}")
    @Cacheable(key = "#bannerNo", value = {"banner"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<BannerDto> banner(@PathVariable Long bannerNo) throws CommonException {
        return new ResponseEntity<>(service.banner(bannerNo), HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> save(@ModelAttribute SaveBannerRequest request) throws CommonException {
        return new ResponseEntity<>(service.save(request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{bannerNo:[\\d]+}")
    @CacheEvict(key = "#bannerNo", value = {"banner", "banners"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<Boolean> remove(@PathVariable Long bannerNo) {
        return new ResponseEntity<>(service.remove(bannerNo), HttpStatus.OK);
    }
}
