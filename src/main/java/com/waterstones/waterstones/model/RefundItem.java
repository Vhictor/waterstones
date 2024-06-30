package com.waterstones.waterstones.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RefundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long refundId;
    private Long itemId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "refundId", insertable = false, updatable = false)
    private Refund refund;

    @ManyToOne
    @JoinColumn(name = "itemId", insertable = false, updatable = false)
    private Item item;
}
