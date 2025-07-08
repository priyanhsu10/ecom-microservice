package com.pro.order_service.dtos;

import com.pro.order_service.entites.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateOrderRequest {
private  Long orderId;
private Long userId;
private String billingAddress;
private List<OrderItemDto> orderItems = new ArrayList<>();
private BigDecimal totalPrice;
}

