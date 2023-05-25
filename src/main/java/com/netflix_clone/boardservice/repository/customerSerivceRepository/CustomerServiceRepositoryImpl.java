package com.netflix_clone.boardservice.repository.customerSerivceRepository;

import com.netflix_clone.boardservice.repository.domains.CustomerService;
import com.netflix_clone.boardservice.repository.domains.QCustomerService;
import com.netflix_clone.boardservice.repository.dto.reference.CustomerServiceDto;
import com.netflix_clone.boardservice.repository.dto.reference.QCustomerServiceDto;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.netflix_clone.boardservice.repository.domains.QCustomerService.customerService;
public class CustomerServiceRepositoryImpl extends QuerydslRepositorySupport implements CustomerServiceRepositoryCustom {
    private JPQLQueryFactory query;

    @Autowired
    public CustomerServiceRepositoryImpl(JPQLQueryFactory query) {
        super(CustomerService.class);
        this.query = query;
    }

    @Override
    public PageImpl<CustomerServiceDto> customerServices(Pageable request) {
        List<CustomerServiceDto> list = query.select(
                        new QCustomerServiceDto(
                                customerService.csNo,
                                customerService.type,
                                customerService.email,
                                customerService.question,
                                customerService.answer,
                                customerService.askedDate,
                                customerService.replyDate,
                                customerService.isReplSent
                        )
                )
                .from(customerService)
                .offset(request.getOffset())
                .limit(request.getPageSize())
                .orderBy(customerService.csNo.desc())
                .fetch();
        Long count = from(customerService).fetchCount();
        return new PageImpl<>(list, request, count);
    }

    @Override
    public CustomerServiceDto customerService(Long csNo) {
        return query.select(
                        new QCustomerServiceDto(
                                customerService.csNo,
                                customerService.type,
                                customerService.email,
                                customerService.question,
                                customerService.answer,
                                customerService.askedDate,
                                customerService.replyDate,
                                customerService.isReplSent
                        )
                )
                .from(customerService)
                .where(customerService.csNo.eq(csNo))
                .fetchOne();
    }

    @Override
    public Boolean remove(Long csNo) {
        return query.delete(customerService).where(customerService.csNo.eq(csNo)).execute() > 0;
    }
}
