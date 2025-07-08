package com.pro.order_service.repositories;

import com.pro.order_service.dtos.OrderDto;
import com.pro.order_service.dtos.OrderModel;
import com.pro.order_service.dtos.OrderItemDto;
import com.pro.order_service.entites.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepository {

    private final DatabaseClient databaseClient;

    public Mono<OrderDto> getOrderById(long id) {
        String query = """
                select o.id,o.user_id,o.order_date,o.status,o.total_price,o.billing_address,o.create_time,o.updatetime
                 ,oi.id as orderitemid, oi.product_id,oi.product_name,oi.quantity,oi.price,oi.create_time as item_created,oi.update_time as item_updated, 
                  oi.discount from orders o left join order_items oi on o.id = oi.order_id where o.id = :id
                """;
        return databaseClient.sql(query)
                .bind("id",id)
                .map(CustomOrderRepository::rowmapper)
                .all()
                .collectList()
                .map(x->{

                    var firstOrder = x.getFirst();
                    OrderDto orderDto = getOrderDto(firstOrder);
                    for (OrderModel order : x) {
                        OrderItemDto itemDto = getOrderItemDto(order, firstOrder);
                        orderDto.getOrderItems().add(itemDto);
                    }
                    return  orderDto;


                });

    }

    //    user_id | order_date | status | total_price | billing_address | create_time | updatetime | orderitemid | product_id |
//    product_name | quantity | price | item_created | item_updated | discount
    public Flux<OrderDto> getOrderbyUserId(long userId) {
        String query = """
                select o.id,o.user_id,o.order_date,o.status,o.total_price,o.billing_address,o.create_time,o.updatetime
                 ,oi.id as orderitemid, oi.product_id,oi.product_name,oi.quantity,oi.price,oi.create_time as item_created,oi.update_time as item_updated, 
                  oi.discount from orders o left join order_items oi on o.id = oi.order_id where user_id = :userId
                """;


        return databaseClient.sql(query).bind("userId", userId).map(CustomOrderRepository::rowmapper)

                .all()
                .groupBy(OrderModel::getId, orderDto -> orderDto)
                .flatMap(groupedFlux -> groupedFlux.collectList()
                        .map(orderList -> {
                            var firstOrder = orderList.getFirst();
                            OrderDto orderDto = getOrderDto(firstOrder);
                            for (OrderModel order : orderList) {
                                OrderItemDto itemDto = getOrderItemDto(order, firstOrder);
                                orderDto.getOrderItems().add(itemDto);

                            }
                            return orderDto;
                        }));


    }

    private static OrderModel rowmapper(io.r2dbc.spi.Readable x) {
        OrderModel order = new OrderModel();
        order.setId(x.get("orderitemid", Long.class));
        order.setUserId(x.get("user_id", Long.class));
        order.setCreateTime(x.get("create_time", LocalDateTime.class));
        order.setUpdateTime(x.get("updatetime", LocalDateTime.class));
        order.setBillingAddress(x.get("billing_address", String.class));
        order.setTotalPrice(Objects.requireNonNull(x.get("total_price", BigDecimal.class)));
        order.setOrderStatus(OrderStatus.valueOf(x.get("status", String.class)));
        order.setId(x.get("id", Long.class));
        order.setProductId(x.get("product_id", String.class));
        order.setQuantity(x.get("quantity", Integer.class));
        order.setPrice(x.get("price", BigDecimal.class));
        order.setProductName(x.get("product_name", String.class));
        order.setDiscount(x.get("discount", Integer.class));
        order.setItemCreateTime(x.get("item_created", LocalDateTime.class));
        order.setItemUpdateTime(x.get("item_updated", LocalDateTime.class));
        return order;
    }

    private static OrderDto getOrderDto(OrderModel firstOrder) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(firstOrder.getId());
        orderDto.setId(firstOrder.getId());
        orderDto.setUserId(firstOrder.getUserId());
        orderDto.setBillingAddress(firstOrder.getBillingAddress());
        orderDto.setTotalPrice(firstOrder.getTotalPrice());
        orderDto.setOrderStatus(firstOrder.getOrderStatus());
        orderDto.setCreateTime(firstOrder.getCreateTime());
        orderDto.setUpdateTime(firstOrder.getUpdateTime());
        return orderDto;
    }

    private static OrderItemDto getOrderItemDto(OrderModel order, OrderModel firstOrder) {
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setId(order.getId());
        itemDto.setOrderId(firstOrder.getId());
        itemDto.setProductId(order.getProductId());
        itemDto.setQuantity(order.getQuantity());
        itemDto.setPrice(order.getPrice());
        itemDto.setProductName(order.getProductName());
        itemDto.setDiscount(order.getDiscount());
        itemDto.setCreateTime(order.getItemCreateTime());
        itemDto.setUpdateTime(order.getItemUpdateTime());
        return itemDto;
    }

}
