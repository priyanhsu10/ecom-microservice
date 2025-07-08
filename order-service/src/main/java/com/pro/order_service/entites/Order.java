package com.pro.order_service.entites;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Table("orders")
public class Order {
    @Id
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String billingAddress;


}
//Column      |            Type             | Collation | Nullable |              Default
//-----------------+-----------------------------+-----------+----------+------------------------------------
//id              | bigint                      |           | not null | nextval('orders_id_seq'::regclass)
//user_id         | bigint                      |           | not null |
//order_date      | timestamp without time zone |           | not null |
//status          | integer                     |           | not null |
//total_price     | numeric(10,2)               |           | not null |
//billing_address | character varying(400)      |           |          |