package com.netflix_clone.boardservice.repository.faqRepository;

import com.netflix_clone.boardservice.repository.dto.reference.FaqDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface FaqRepositoryCustom {
    PageImpl faqs(Pageable pageable);
    FaqDto faq(Long faqNo);
    Boolean remove(Long faqNo);
}
