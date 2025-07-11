package com.pro.order_service.kafka;

import com.pro.order_service.entites.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusEvent {
    private long orderId;
    private Long userId;
    private OrderStatus status;
}

