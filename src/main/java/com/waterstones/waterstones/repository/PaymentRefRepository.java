package com.waterstones.waterstones.repository;

import com.waterstones.waterstones.model.PaymentRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRefRepository extends JpaRepository<PaymentRef,Long> {
}
