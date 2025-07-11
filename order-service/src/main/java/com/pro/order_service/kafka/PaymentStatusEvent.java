package com.pro.order_service.kafka;

import lombok.Data;

@Data
public class PaymentStatusEvent {
    private long orderId;
    private Long userId;
    
    private PaymentStatus status;
}
