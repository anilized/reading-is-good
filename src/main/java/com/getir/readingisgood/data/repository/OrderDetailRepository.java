package com.getir.readingisgood.data.repository;

import com.getir.readingisgood.data.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value = "update OrderDetail as d set d.order.id=:orderId where d.id=:orderDetailId")
    @Transactional
    @Modifying
    int updateOrderId(@Param("orderId") Long orderId, @Param("orderDetailId") Long orderDetailId);

}
