package com.pro.product_service.dtos;

import com.pro.product_service.entites.Product;
import lombok.Data;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private int stockQuantity;
    private String brand;
    public  ProductDto() {
    }
    public ProductDto(Product product) {
        this.id =product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.imageUrl = product.getImageUrl();
        this.stockQuantity = product.getStockQuantity();
        this.brand = product.getBrand();
    }

}
