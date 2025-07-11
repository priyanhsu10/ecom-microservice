package com.pro.order_service.kafka.listeners;

import com.pro.order_service.kafka.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderStatusEventListener implements CommandLineRunner {
    private final ReactiveKafkaConsumerTemplate<Integer, OrderStatusEvent> kafkaConsumerOrderTemplate;

    @Override
    public void run(String... args) throws Exception {

        kafkaConsumerOrderTemplate
                .receive()
                .flatMap(this::processorPipeline, 3)
                .subscribe();

    }

    private Publisher<Void> processorPipeline(ReceiverRecord<Integer, OrderStatusEvent> integerOrderStatusEventReceiverRecord) {
        return Mono.just(integerOrderStatusEventReceiverRecord)

                .doOnNext(x -> {
                    int key = x.key();
                    OrderStatusEvent orderStatusEvent = x.value();
                    log.info("Received order status event key: {}", key);
                    log.info("Received order status event: {}", orderStatusEvent);
                    // Here you can add logic to process the order status event
                    x.receiverOffset().acknowledge();

                })
                .retry(3)

                .then();

    }

}
