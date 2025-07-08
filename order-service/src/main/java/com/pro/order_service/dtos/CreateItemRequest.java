package com.pro.order_service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class CreateItemRequest {
    private Long orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
    private String productDescription;
    private Integer discount;
    private String productName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private BigDecimal totalPrice;
}
