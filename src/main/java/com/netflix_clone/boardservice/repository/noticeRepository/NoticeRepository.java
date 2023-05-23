package com.netflix_clone.boardservice.repository.noticeRepository;

import com.netflix_clone.boardservice.repository.domains.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
