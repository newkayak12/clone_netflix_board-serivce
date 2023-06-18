package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.component.configure.feign.ImageFeign;
import com.netflix_clone.boardservice.component.constants.Constants;
import com.netflix_clone.boardservice.component.enums.FileType;
import com.netflix_clone.boardservice.component.exception.BecauseOf;
import com.netflix_clone.boardservice.component.exception.CommonException;
import com.netflix_clone.boardservice.repository.bannerRepository.BannerRepository;
import com.netflix_clone.boardservice.repository.domains.Banner;
import com.netflix_clone.boardservice.repository.dto.reference.*;
import com.netflix_clone.boardservice.repository.dto.request.SaveBannerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BannerService {
    private final ModelMapper mapper;
    private final BannerRepository repository;
    private final ImageFeign imageFeign;


    @Transactional(readOnly = true)
    public PageImpl banners(PageableRequest request) {
        log.warn("REQUEST {}", request);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        return (PageImpl) repository.banners(pageable).map(element -> {
            FileDto image = Optional.ofNullable(imageFeign.file(element.getBannerNo(), FileType.BANNER).getBody()).orElseGet(() -> null);
            element.setImage(image);
            return element;
        });
    }

    @Transactional(readOnly = true)
    public BannerDto banner(Long bannerNo) throws CommonException {
        return Optional.ofNullable(repository.banner(bannerNo))
                       .map(bannerDto -> {
                           FileDto image = Optional.ofNullable(imageFeign.file(bannerDto.getBannerNo(), FileType.BANNER).getBody()).orElseGet(() -> null);
                           bannerDto.setImage(image);
                           return bannerDto;
                        })
                        .orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
    }

    public Boolean save(SaveBannerRequest request) throws CommonException {
        if( repository.bannerCount(request.getBannerNo()) >= Constants.BANNER_MAXIMUM_COUNT )
                                throw new CommonException(BecauseOf.EXCEED_MAXIMUM_BANNER_COUNT);

        Banner banner = mapper.map(request, Banner.class);
        banner = Optional.ofNullable(repository.save(banner)).orElseThrow(() -> new CommonException(BecauseOf.SAVE_FAILURE));

        if (Objects.nonNull(request.getBannerNo()) && Objects.nonNull(request.getRawFile()))
            imageFeign.remove(request.getBannerNo(), FileType.BANNER);

        FileRequest fileRequest = new FileRequest();
        fileRequest.setRawFile(request.getRawFile());
        fileRequest.setTableNo(banner.getBannerNo());
        fileRequest.setFileType(FileType.BANNER.name());

        return Optional.ofNullable(imageFeign.save(fileRequest).getBody())
                .map(file -> true).orElseThrow(() -> new CommonException(BecauseOf.SAVE_FAILURE));
    }

    public Boolean remove(Long bannerNo) {
        imageFeign.remove(bannerNo, FileType.BANNER);
        return repository.remove(bannerNo);
    }
}
