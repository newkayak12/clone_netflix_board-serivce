package com.netflix_clone.boardservice.repository.faqRepository;

import com.netflix_clone.boardservice.repository.domains.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long>, FaqRepositoryCustom {

}
