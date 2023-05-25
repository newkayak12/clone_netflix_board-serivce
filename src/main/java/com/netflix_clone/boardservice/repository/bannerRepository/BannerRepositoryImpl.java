package com.netflix_clone.boardservice.repository.bannerRepository;

import com.netflix_clone.boardservice.repository.domains.Banner;
//import com.netflix_clone.boardservice.repository.dto.QBannerDto;
//import com.netflix_clone.boardservice.repository.dto.QFaqDto;
import com.netflix_clone.boardservice.repository.dto.reference.BannerDto;
import com.netflix_clone.boardservice.repository.dto.reference.QBannerDto;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.netflix_clone.boardservice.repository.domains.QBanner.banner;
public class BannerRepositoryImpl extends QuerydslRepositorySupport implements BannerRepositoryCustom {
    private JPQLQueryFactory query;

    @Autowired
    public BannerRepositoryImpl(JPQLQueryFactory query) {
        super(Banner.class);
        this.query = query;
    }

    @Override
    public PageImpl<BannerDto> banners(Pageable pageable) {
        List<BannerDto> list =  query.select( new QBannerDto(
                                                    banner.bannerNo,
                                                    banner.title,
                                                    banner.url
                                                ))
                                    .from(banner)
                                    .limit(pageable.getOffset())
                                    .offset(pageable.getPageSize())
                                    .orderBy(banner.bannerNo.desc())
                                    .fetch();

        Long count = from(banner).fetchCount();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public BannerDto banner(Long bannerNo) {
        return query.select(new QBannerDto(
                            banner.bannerNo,
                            banner.title,
                            banner.url
                         ))
                    .from(banner)
                    .where(banner.bannerNo.eq(bannerNo))
                    .fetchOne();
    }

    @Override
    public Boolean remove(Long bannerNo) {
        return query.delete(banner).where(banner.bannerNo.eq(bannerNo)).execute() > 0;
    }
}
