package com.netflix_clone.boardservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.boardservice.component.configure.feign.ImageFeign;
import com.netflix_clone.boardservice.component.enums.FileType;
import com.netflix_clone.boardservice.component.exception.BecauseOf;
import com.netflix_clone.boardservice.component.exception.CommonException;
import com.netflix_clone.boardservice.repository.domains.Notice;
import com.netflix_clone.boardservice.repository.dto.reference.*;
import com.netflix_clone.boardservice.repository.dto.request.SaveNoticeRequest;
import com.netflix_clone.boardservice.repository.noticeRepository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class NoticeService {
    private final NoticeRepository repository;
    private final ImageFeign imageFeign;
    private final ModelMapper mapper;

    public PageImpl notices(PageableRequest pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getLimit());
        return (PageImpl) repository.notices(pageable).map( dto -> {
           dto.setImages(imageFeign.files(dto.getNoticeNo(), FileType.NOTICE).getBody());
           return dto;
        });
    }

    public NoticeDto notice(Long noticeNo) throws CommonException {
        return Optional.ofNullable(repository.notice(noticeNo))
                       .map( noticeDto -> {
                           List<FileDto> images = imageFeign.files(noticeDto.getNoticeNo(), FileType.NOTICE).getBody();
                           noticeDto.setImages(images);
                           return noticeDto;
                       })
                       .orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
    }

    public Boolean save(SaveNoticeRequest request) {
        Notice notice = mapper.map(request, Notice.class);
        return Optional.ofNullable(repository.save(notice)).map(result -> {

            if(Objects.nonNull(request.getImages()) && !request.getImages().isEmpty()) {
                imageFeign.removeNotIn(request.getImages().stream().map(FileDto::getFileNo).collect(Collectors.toList()));
            }
            if(!request.getRawFiles().isEmpty()) {
                FileRequests requests = new FileRequests();
                requests.setTableNo(result.getNoticeNo());
                requests.setFileType(FileType.NOTICE.name());
                requests.setRawFiles(request.getRawFiles());
                imageFeign.saves(requests);
            }
            return true;
        }).orElseGet(() -> false);
    }

    public Boolean remove(Long noticeNo) {
        imageFeign.remove(noticeNo, FileType.NOTICE);
        return repository.remove(noticeNo);
    }
}
