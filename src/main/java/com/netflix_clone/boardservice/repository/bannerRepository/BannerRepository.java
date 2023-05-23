package com.netflix_clone.boardservice.repository.bannerRepository;

import com.netflix_clone.boardservice.repository.domains.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>, BannerRepositoryCustom {
}
