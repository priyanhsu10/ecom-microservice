package com.pro.order_service.entites;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    private Long id;

    private Long orderId; // Foreign key to Order
    @NotBlank
    private String productId; // Product ID
    @NotBlank
    @Column(value = "product_name")
    private String productName; // Product name
    @NotBlank
    @Min(value=1, message = "Quantity must be at least 1")
    private int quantity; // Quantity of the product in the order
    @Min(value = 1,message = "Price must be at least 1")
    private BigDecimal price; // Price of the product at the time of order
    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value=0, message = "Discount cannot exceed 100")
    private int discount; // Discount applied to the product, if any
   private LocalDateTime createTime;
   private  LocalDateTime updateTime;
   private BigDecimal totalPrice;


}
//Column    |            Type             | Collation | Nullable |                 Default
//--------------+-----------------------------+-----------+----------+-----------------------------------------
//id           | bigint                      |           | not null | nextval('order_items_id_seq'::regclass)
//order_id     | bigint                      |           | not null |
//product_id   | character varying(50)       |           | not null |
//product_name | character varying(200)      |           | not null |
//quantity     | integer                     |           | not null |
//price        | numeric(10,2)               |           | not null |
//create_time  | timestamp without time zone |           |          | CURRENT_TIMESTAMP
//update_time  | timestamp without time zone |           |          |
//discount     | integer                     |           |          | 0