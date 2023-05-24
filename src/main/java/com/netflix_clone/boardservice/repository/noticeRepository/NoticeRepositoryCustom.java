package com.netflix_clone.boardservice.repository.noticeRepository;

import com.netflix_clone.boardservice.repository.dto.reference.NoticeDto;
import com.netflix_clone.boardservice.repository.dto.request.SaveNoticeRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    PageImpl<NoticeDto> notices(Pageable pageable);
    NoticeDto notice(Long noticeNo);
    Boolean remove(Long noticeNo);
}
