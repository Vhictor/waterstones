package com.waterstones.waterstones.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    public void refund(Long paymentRefId, BigDecimal refundAmount) {
        // Call to payment gateway to process the refund
    }
}
