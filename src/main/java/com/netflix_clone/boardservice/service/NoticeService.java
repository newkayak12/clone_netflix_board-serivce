package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.configure.feign.ImageFeign;
import com.netflix_clone.boardservice.enums.FileType;
import com.netflix_clone.boardservice.exception.BecauseOf;
import com.netflix_clone.boardservice.exception.CommonException;
import com.netflix_clone.boardservice.repository.domains.Notice;
import com.netflix_clone.boardservice.repository.dto.reference.FileDto;
import com.netflix_clone.boardservice.repository.dto.reference.FileRequest;
import com.netflix_clone.boardservice.repository.dto.reference.NoticeDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
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
        Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getLimit());
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
            if(!request.getImages().isEmpty()) {
                imageFeign.removeNotIn(request.getImages());
            }
            if(!request.getRawFiles().isEmpty()) {
                List<FileRequest> fileRequests = request.getRawFiles().stream().map(raw -> {
                    FileRequest fileRequest = new FileRequest();
                    fileRequest.setRawFile(raw);
                    fileRequest.setTableNo(request.getNoticeNo());
                    fileRequest.setFileType(FileType.NOTICE);
                    return fileRequest;
                }).collect(Collectors.toList());
                imageFeign.save(fileRequests);
            }
            return true;
        }).orElseGet(() -> false);
    }

    public Boolean remove(Long noticeNo) {
        return repository.remove(noticeNo);
    }
}
