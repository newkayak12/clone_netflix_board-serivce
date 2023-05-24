package com.netflix_clone.boardservice.repository.noticeRepository;

import com.netflix_clone.boardservice.repository.domains.Notice;
import com.netflix_clone.boardservice.repository.domains.QNotice;
import com.netflix_clone.boardservice.repository.dto.QNoticeDto;
import com.netflix_clone.boardservice.repository.dto.reference.NoticeDto;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.netflix_clone.boardservice.repository.domains.QNotice.notice;
public class NoticeRepositoryImpl extends QuerydslRepositorySupport implements NoticeRepositoryCustom {
    private JPQLQueryFactory query;

    @Autowired
    public NoticeRepositoryImpl(JPQLQueryFactory query) {
        super(Notice.class);
        this.query = query;
    }

    @Override
    public PageImpl<NoticeDto> notices(Pageable pageable) {
        List<NoticeDto> list =  query.select(new QNoticeDto(
                                notice.noticeNo,
                                notice.title,
                                notice.content,
                                notice.regDate
                            ))
                    .from(notice)
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .orderBy(notice.noticeNo.desc())
                    .fetch();
        Long count = from(notice).fetchCount();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public NoticeDto notice(Long noticeNo) {
        return query.select(new QNoticeDto(
                                notice.noticeNo,
                                notice.title,
                                notice.content,
                                notice.regDate
                            ))
                            .from(notice)
                            .where(notice.noticeNo.eq(noticeNo)).fetchOne();
    }

    @Override
    public Boolean remove(Long noticeNo) {
        return query.delete(notice).where(notice.noticeNo.eq(noticeNo)).execute() > 0;
    }
}
