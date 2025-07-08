package com.pro.order_service.repositories;

import com.pro.order_service.entites.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, Long> {
}
