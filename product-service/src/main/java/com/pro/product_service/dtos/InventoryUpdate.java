package com.pro.product_service.dtos;

import lombok.Data;

@Data
public class InventoryUpdate {
    private String productId;
    private int quantity;
}
