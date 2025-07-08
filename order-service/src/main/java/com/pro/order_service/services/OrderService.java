package com.pro.order_service.services;

import com.pro.order_service.dtos.CreateItemRequest;
import com.pro.order_service.dtos.CreateOrderRequest;
import com.pro.order_service.dtos.OrderDto;
import com.pro.order_service.dtos.OrderItemDto;
import com.pro.order_service.entites.Order;
import com.pro.order_service.entites.OrderItem;
import com.pro.order_service.entites.OrderStatus;
import com.pro.order_service.repositories.CustomOrderRepository;
import com.pro.order_service.repositories.OrderItemRepository;
import com.pro.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomOrderRepository customOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public Flux<OrderDto> getOrdersByUserId(Long userId) {
        return customOrderRepository.getOrderbyUserId(userId);
    }

    public  Mono<OrderDto> getOrderById(Long id){
        return customOrderRepository.getOrderById(id);
    }
    @Transactional
    public Mono<OrderDto> create(CreateOrderRequest createOrderRequest) {

        var orderEntity = getOrderEntity(createOrderRequest);
        return orderRepository.save(orderEntity)
                .flatMap(order -> {
                            OrderDto orderDto = getOrderDto(order);
                            Stream<OrderItem> orderItemStream = getOrderItemStream(createOrderRequest, order);
                            Flux<OrderItem> orderItemFlux = Flux.fromStream(orderItemStream);
                            return orderItemRepository.saveAll(orderItemFlux)
                                    .map(OrderService::getOrderItemDto)
                                    .collectList()
                                    .map(x -> {
                                        orderDto.setOrderItems(x);
                                        return orderDto;
                                    });
                        }
                );


    }



    private static OrderItemDto getOrderItemDto(OrderItem x) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderId(x.getOrderId());
        orderItemDto.setProductId(x.getProductId());
        orderItemDto.setProductName(x.getProductName());
        orderItemDto.setQuantity(x.getQuantity());
        orderItemDto.setPrice(x.getPrice());
        orderItemDto.setDiscount(x.getDiscount());
        orderItemDto.setTotalPrice(x.getTotalPrice());
        orderItemDto.setId(x.getId());
        return orderItemDto;
    }

    private static Stream<OrderItem> getOrderItemStream(CreateOrderRequest createOrderRequest, Order x) {
        return createOrderRequest.getOrderItems().stream().map(item -> {
            var orderItem = new OrderItem();
            orderItem.setOrderId(x.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setProductName(item.getProductName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setDiscount(item.getDiscount());
            orderItem.setTotalPrice(item.getTotalPrice());
            return orderItem;
        });
    }

    private static OrderDto getOrderDto(Order x) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(x.getId());
        orderDto.setUserId(x.getUserId());
        orderDto.setBillingAddress(x.getBillingAddress());
        orderDto.setTotalPrice(x.getTotalPrice());
        orderDto.setOrderStatus(x.getStatus());
        orderDto.setCreateTime(x.getCreateTime());
        orderDto.setUpdateTime(x.getUpdateTime());
        orderDto.setId(x.getId());
        orderDto.setOrderItems(new ArrayList<>());
        return orderDto;
    }

    private Order getOrderEntity(CreateOrderRequest orderDto) {
        orderDto.getOrderItems().forEach(item -> {
            var total = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            BigDecimal discount = total.multiply(BigDecimal.valueOf(item.getDiscount() / 100.0));
            BigDecimal finalAmount = total.subtract(discount);
            item.setTotalPrice(finalAmount);
        });
        double sum = orderDto.getOrderItems().stream().mapToDouble(x -> x.getTotalPrice().doubleValue()).sum();
        var order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setBillingAddress(orderDto.getBillingAddress());
        order.setTotalPrice(BigDecimal.valueOf(sum));
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setCreateTime(LocalDateTime.now());
        return order;
    }

    public Mono<OrderItemDto> createOrderItem(CreateItemRequest orderItemRequest) {

        var orderItem = new OrderItem();
        orderItem.setOrderId(orderItemRequest.getOrderId());
        orderItem.setProductId(orderItemRequest.getProductId());
        orderItem.setProductName(orderItemRequest.getProductName());
        orderItem.setQuantity(orderItemRequest.getQuantity());
        orderItem.setPrice(orderItemRequest.getPrice());
        orderItem.setDiscount(orderItemRequest.getDiscount());
        orderItem.setTotalPrice(orderItemRequest.getTotalPrice());

        return orderItemRepository.save(orderItem)
                .map(OrderService::getOrderItemDto);
    }
}
