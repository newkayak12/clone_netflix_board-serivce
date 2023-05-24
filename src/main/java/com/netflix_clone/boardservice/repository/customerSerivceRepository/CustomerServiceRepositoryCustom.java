package com.netflix_clone.boardservice.repository.customerSerivceRepository;

import com.netflix_clone.boardservice.repository.dto.reference.CustomerServiceDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveCsRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerServiceRepositoryCustom {
    PageImpl<CustomerServiceDto> customerServices(Pageable request);
    CustomerServiceDto customerService(Long csNo);
    Boolean remove(Long csNo);
}
