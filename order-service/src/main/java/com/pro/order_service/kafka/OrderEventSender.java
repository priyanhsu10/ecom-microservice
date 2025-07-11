package com.pro.order_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderEventSender {

    private final ReactiveKafkaProducerTemplate<Long, OrderStatusEvent> orderStatusEventSender;

    public Mono<Void> sendOrderStatusEvent(OrderStatusEvent orderStatusEvent) {
        return orderStatusEventSender.send("order-status-topic", orderStatusEvent.getOrderId(), orderStatusEvent)
                .doOnSuccess(result -> System.out.println("Order status event sent successfully: " + orderStatusEvent))
                .doOnError(error -> System.err.println("Failed to send order status event: " + error.getMessage()))
                .then();
    }

}
