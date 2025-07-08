package com.pro.order_service.controllers;

import com.pro.order_service.dtos.CreateItemRequest;
import com.pro.order_service.dtos.CreateOrderRequest;
import com.pro.order_service.dtos.OrderDto;
import com.pro.order_service.dtos.OrderItemDto;
import com.pro.order_service.entites.OrderStatus;
import com.pro.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user/{userId}")
    public Flux<OrderDto> getOrderByUserId(@PathVariable Long userId) {
       return orderService.getOrdersByUserId(userId);
    }
    @GetMapping("/{id}")
    public Mono<OrderDto> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
    @PostMapping
    public Mono<OrderDto> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.create(createOrderRequest);
    }
    @PostMapping("/item")
    public Mono<OrderItemDto> addOrderItem(@RequestBody CreateItemRequest orderItemRequest) {
        return  orderService.createOrderItem(orderItemRequest);
    }
}
