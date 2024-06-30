package com.waterstones.waterstones.service;

import com.waterstones.waterstones.dto.RefundRequestDTO;
import com.waterstones.waterstones.dto.RefundResponseDTO;
import com.waterstones.waterstones.exception.RefundException;
import com.waterstones.waterstones.model.*;
import com.waterstones.waterstones.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class RefundService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RefundItemRepository refundItemRepository;

    @Autowired
    private PaymentRefRepository paymentRefRepository;

    @Autowired
    private PaymentService paymentService;

    @Transactional
    public RefundResponseDTO processRefund(RefundRequestDTO refundRequest) {
        Order order = orderRepository.findById(refundRequest.getOrderId())
                .orElseThrow(() -> new RefundException("Order not found"));

        BigDecimal refundAmount = calculateRefundAmount(refundRequest);

        validateRefundAmount(refundRequest.getPaymentRefId(), refundAmount);

        paymentService.refund(refundRequest.getPaymentRefId(), refundAmount);

        Refund refund = saveRefundDetails(refundRequest, refundAmount);

        return new RefundResponseDTO(refund.getId(), refund.getStatus(), refund.getRefundDate());
    }

    private BigDecimal calculateRefundAmount(RefundRequestDTO refundRequest) {
        BigDecimal totalRefundAmount = BigDecimal.ZERO;
        for (RefundRequestDTO.RefundItemDTO itemDTO : refundRequest.getItems()) {
            Item item = itemRepository.findById(itemDTO.getItemId())
                    .orElseThrow(() -> new RefundException("Item not found"));
            totalRefundAmount = totalRefundAmount.add(item.getPrice().multiply(new BigDecimal(itemDTO.getQuantity())));
            totalRefundAmount = totalRefundAmount.add(item.getShippingCharge().multiply(new BigDecimal(itemDTO.getQuantity())));
        }
        return totalRefundAmount;
    }

    private void validateRefundAmount(Long paymentRefId, BigDecimal refundAmount) {
        BigDecimal totalRefundedAmount = refundRepository.sumRefundedAmountByPaymentRefId(paymentRefId);
        PaymentRef paymentRef = paymentRefRepository.findById(paymentRefId)
                .orElseThrow(() -> new RefundException("Payment reference not found"));

        if (totalRefundedAmount.add(refundAmount).compareTo(paymentRef.getAmount()) > 0) {
            throw new RefundException("Refund amount exceeds the original payment amount");
        }
    }

    private Refund saveRefundDetails(RefundRequestDTO refundRequest, BigDecimal refundAmount) {
        Refund refund = new Refund();
        refund.setOrderId(refundRequest.getOrderId());
        refund.setAmount(refundAmount);
        refund.setPaymentRefId(refundRequest.getPaymentRefId());
        refund.setRefundDate(new Date());
        refund.setStatus("SUCCESSFUL");

        Refund savedRefund = refundRepository.save(refund);

        for (RefundRequestDTO.RefundItemDTO itemDTO : refundRequest.getItems()) {
            RefundItem refundItem = new RefundItem();
            refundItem.setRefundId(savedRefund.getId());
            refundItem.setItemId(itemDTO.getItemId());
            refundItem.setQuantity(itemDTO.getQuantity());
            refundItemRepository.save(refundItem);
        }

        return savedRefund;
    }
}

