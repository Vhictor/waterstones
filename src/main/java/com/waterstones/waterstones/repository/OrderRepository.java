package com.waterstones.waterstones.repository;

import com.waterstones.waterstones.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
