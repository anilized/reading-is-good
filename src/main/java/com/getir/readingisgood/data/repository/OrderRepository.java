package com.getir.readingisgood.data.repository;

import com.getir.readingisgood.data.entity.Customer;
import com.getir.readingisgood.data.entity.Order;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

/*    @Query(value = "select o from Order where o.customer:=customerId")
    Set<Order> getOrderByCustomer(@Param("customerId") Long customerId);*/

}
