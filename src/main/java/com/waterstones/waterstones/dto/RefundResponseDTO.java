package com.waterstones.waterstones.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class RefundResponseDTO {
    private Long refundId;
    private String status;
    private Date refundDate;

    public RefundResponseDTO(Long refundId, String status, Date refundDate) {
        this.refundId = refundId;
        this.status = status;
        this.refundDate = refundDate;
    }
}
