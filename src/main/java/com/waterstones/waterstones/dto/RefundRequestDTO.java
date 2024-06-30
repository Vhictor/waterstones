package com.waterstones.waterstones.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RefundRequestDTO {
    private Long orderId;
    private Long paymentRefId;
    private List<RefundItemDTO> items;

    @Getter @Setter
    public static class RefundItemDTO {
        private Long itemId;
        private int quantity;

    }

}
