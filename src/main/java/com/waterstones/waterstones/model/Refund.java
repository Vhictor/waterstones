package com.waterstones.waterstones.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private Long paymentRefId;
    private Date refundDate;
    private String status;

    @OneToMany(mappedBy = "refund")
    private List<RefundItem> refundItems;

}
