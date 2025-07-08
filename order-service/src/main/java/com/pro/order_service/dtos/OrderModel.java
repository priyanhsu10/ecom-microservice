package com.pro.order_service.dtos;

import com.pro.order_service.entites.OrderStatus;
import lombok.Data;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderModel {
    private Long id;
    private Long userId;
    private String billingAddress;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
//order item dto
    private String productId;
    private Integer quantity;
    private BigDecimal price;
    private String productDescription;
    private Integer discount;
    private String productName;
    private String productImageUrl;
    private LocalDateTime itemCreateTime;
    private LocalDateTime itemUpdateTime;

}
