package com.ecommerce.order.repository;

import com.ecommerce.order.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

  //  @Query // not needed as this resolved by naming convention
  List<Order> findAllByUserId(String userId);

  Optional<Order> findByTrackingNumber(String trackingNumber);

}
