package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.configure.feign.ImageFeign;
import com.netflix_clone.boardservice.enums.FileType;
import com.netflix_clone.boardservice.exception.BecauseOf;
import com.netflix_clone.boardservice.exception.CommonException;
import com.netflix_clone.boardservice.repository.bannerRepository.BannerRepository;
import com.netflix_clone.boardservice.repository.domains.Banner;
import com.netflix_clone.boardservice.repository.domains.Faq;
import com.netflix_clone.boardservice.repository.dto.reference.*;
import com.netflix_clone.boardservice.repository.dto.request.SaveBannerRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveFaqRequest;
import com.netflix_clone.boardservice.repository.faqRepository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.asymmetric.COMPOSITE;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
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

    public Boolean save(SaveBannerRequest request) {
        Banner banner = mapper.map(request, Banner.class);
        return Optional.ofNullable(repository.save(banner))
                .map(result -> {
                    if (Objects.nonNull(request.getBannerNo())) imageFeign.remove(request.getBannerNo(), FileType.BANNER);

                    FileRequest fileRequest = new FileRequest();
                    fileRequest.setRawFile(request.getRawFile());
                    fileRequest.setTableNo(result.getBannerNo());
                    fileRequest.setFileType(FileType.BANNER);
                    Integer count = imageFeign.save(Arrays.asList(fileRequest)).getBody().size();

                    return count > 0 ? true : false;
                })
                .orElseGet( () -> false);
    }

    public Boolean remove(Long bannerNo) {
        return repository.remove(bannerNo);
    }
}
