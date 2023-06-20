package com.netflix_clone.boardservice.repository.customerSerivceRepository;

import com.netflix_clone.boardservice.repository.domains.CustomerService;
import com.netflix_clone.boardservice.repository.dto.reference.CustomerServiceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long>, CustomerServiceRepositoryCustom {
    @Query(nativeQuery = true, value = "SELECT :msg FROM DUAL;")
    String wakeUpMsg(@Param(value = "msg") String Msg);

    Optional<CustomerService> findCustomerServiceByCsNo(Long csNo);
}
