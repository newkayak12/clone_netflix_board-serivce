package com.netflix_clone.boardservice.repository.faqRepository;

import com.netflix_clone.boardservice.repository.domains.Faq;
import com.netflix_clone.boardservice.repository.dto.reference.FaqDto;
import com.netflix_clone.boardservice.repository.dto.reference.QFaqDto;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.netflix_clone.boardservice.repository.domains.QFaq.faq;

public class FaqRepositoryImpl extends QuerydslRepositorySupport implements FaqRepositoryCustom {
    private JPQLQueryFactory query;

    @Autowired
    public FaqRepositoryImpl(JPQLQueryFactory query) {
        super(Faq.class);
        this.query = query;
    }

    @Override
    public PageImpl faqs(Pageable pageable) {
        List<FaqDto> list = query.select(new QFaqDto( faq.faqNo, faq.question,  faq.answer,  faq.regDate))
                                 .from(faq)
                                 .offset(pageable.getOffset())
                                 .limit(pageable.getPageSize())
                                 .orderBy(faq.faqNo.desc())
                                 .fetch();
        Long count = from(faq).fetchCount();
        return new PageImpl(list, pageable, count);
    }

    @Override
    public FaqDto faq(Long faqNo) {
        return query
                .select(
                    new QFaqDto(
                        faq.faqNo,
                        faq.question,
                        faq.answer,
                        faq.regDate
                    )
                )
                .from(faq)
                .where(faq.faqNo.eq(faqNo))
                .fetchOne();
    }

    @Override
    public Boolean remove(Long faqNo) {
        return query.delete(faq).where(faq.faqNo.eq(faqNo)).execute() > 0L;
    }
}
