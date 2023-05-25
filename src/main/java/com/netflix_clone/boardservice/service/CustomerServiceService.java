package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.repository.customerSerivceRepository.CustomerServiceRepository;
import com.netflix_clone.boardservice.repository.domains.CustomerService;
import com.netflix_clone.boardservice.repository.dto.reference.CustomerServiceDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.request.SaveCsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceService {
    private final ModelMapper mapper;
    private final CustomerServiceRepository repository;

    public PageImpl<CustomerServiceDto> customerServices(PageableRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
        return repository.customerServices(pageable);
    }

    public CustomerServiceDto customerService(Long csNo) {
        return repository.customerService(csNo);
    }

    public Boolean save(SaveCsRequest request) {
        CustomerService cs = mapper.map(request, CustomerService.class);
        return Optional.ofNullable(repository.save(cs)).map( Objects::nonNull ).orElseGet(() -> false);
    }

    public Boolean remove(Long csNo) {
        return repository.remove(csNo);
    }
}
