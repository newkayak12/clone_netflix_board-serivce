package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.repository.ReportRepository.ReportRepository;
import com.netflix_clone.boardservice.repository.domains.Report;
import com.netflix_clone.boardservice.repository.dto.request.SaveReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ReportService {
    private final ReportRepository repository;
    private final ModelMapper mapper;

    public Boolean report(SaveReport saveReport) {
        return Optional.ofNullable(repository.save(mapper.map(saveReport, Report.class)))
                       .map(Objects::nonNull)
                       .orElseGet(() -> false);
    }
}
