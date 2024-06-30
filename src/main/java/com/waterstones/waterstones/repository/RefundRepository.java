package com.waterstones.waterstones.repository;

import com.waterstones.waterstones.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface RefundRepository extends JpaRepository<Refund,Long> {

    @Query("SELECT SUM(r.amount) FROM Refund r WHERE r.paymentRefId = :paymentRefId")
    BigDecimal sumRefundedAmountByPaymentRefId(@Param("paymentRefId") Long paymentRefId);
}
