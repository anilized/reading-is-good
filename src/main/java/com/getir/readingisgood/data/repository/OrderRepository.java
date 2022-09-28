package com.getir.readingisgood.data.repository;

import com.getir.readingisgood.data.domain.request.IOrderReport;
import com.getir.readingisgood.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select o from Order o where o.customer.id=:customerId")
    Page<Order> findAllByCustomer(@Param("customerId") Long customerId, Pageable pageable);
    Page<Order> findAllByCreateDateBetween(Date startDate, Date endDate, Pageable pageable);
    @Query(nativeQuery = true, value = "select cast(date_part('month',ot.create_date) as int8) as monthNum, count(*) as orderCount ,sum(od.amount) as bookAmount, sum(od.price) as totalPrice from order_table ot left join order_detail od ON ot.id = od.order_id where ot.customer_id=:customerId group by date_part('month',ot.create_date);")
    List<IOrderReport> generateReportForCustomer(@Param("customerId") Long customerId);

    @Query(nativeQuery = true, value = "select cast(date_part('month',ot.create_date) as int8) as monthNum, count(*) as orderCount ,sum(od.amount) as bookAmount, sum(od.price) as totalPrice from order_table ot left join order_detail od ON ot.id = od.order_id group by date_part('month',ot.create_date);")
    List<IOrderReport> generateReportForAllOrders();

}
