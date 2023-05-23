package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.exception.BecauseOf;
import com.netflix_clone.boardservice.exception.CommonException;
import com.netflix_clone.boardservice.repository.domains.Faq;
import com.netflix_clone.boardservice.repository.dto.reference.FaqDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveFaqRequest;
import com.netflix_clone.boardservice.repository.faqRepository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class FaqService {
    private final ModelMapper mapper;
    private final FaqRepository repository;

    @Transactional(readOnly = true)
    public PageImpl faqs(PageableRequest pageDto) {
        Pageable pageable = PageRequest.of(pageDto.getPage(), pageDto.getLimit());
        return repository.faqs(pageable);
    }

    @Transactional(readOnly = true)
    public FaqDto faq(Long faqNo) throws CommonException {
        return Optional.ofNullable(repository.faq(faqNo)).orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
    }

    public Boolean save(SaveFaqRequest request) {
        Faq faq = mapper.map(request, Faq.class);
        return Optional.ofNullable(repository.save(faq)).map(Objects::nonNull).orElseGet(() -> false);
    }

    public Boolean remove(Long faqNo) {
        return repository.remove(faqNo);
    }
}
