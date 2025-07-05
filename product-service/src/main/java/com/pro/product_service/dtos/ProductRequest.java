package com.pro.product_service.dtos;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private int stockQuantity;
    private String brand;

}
