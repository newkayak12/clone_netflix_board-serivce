package com.netflix_clone.boardservice.repository.ReportRepository;

import com.netflix_clone.boardservice.repository.domains.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
