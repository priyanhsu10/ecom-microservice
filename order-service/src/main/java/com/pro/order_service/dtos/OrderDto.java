package com.pro.order_service.dtos;

import com.pro.order_service.entites.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private String billingAddress;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
