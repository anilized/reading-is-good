package com.getir.readingisgood.data.repository;

import com.getir.readingisgood.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByCustomer(Long customerId, Pageable pageable);
    Page<Order> findAllByCreateDate(Date startDate, Date endDate, Pageable pageable);

}
