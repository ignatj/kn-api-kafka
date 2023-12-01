package com.knits.enterprise.repository.asset;

import com.knits.enterprise.model.asset.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}