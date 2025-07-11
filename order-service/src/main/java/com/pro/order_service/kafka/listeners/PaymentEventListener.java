package com.pro.order_service.kafka.listeners;

import com.pro.order_service.kafka.PaymentStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener implements CommandLineRunner {
    private final ReactiveKafkaConsumerTemplate<Integer, PaymentStatusEvent> kafkaConsumerTemplate;

    @Override
    public void run(String... args) throws Exception {
        kafkaConsumerTemplate.receive()
                .flatMap(this::separateProcessing, 3)
                .subscribe();
    }

//    public static Retry retrySpec() {
//        return Retry.fixedDelay(3, Duration.ofSeconds(1))
//                .filter(x -> IndexOutOfBoundsException.class.isInstance(x))
//                .onRetryExhaustedThrow((spec, signal) -> signal.failure());
//    }


    private Publisher<Void> separateProcessing(ReceiverRecord<Integer, PaymentStatusEvent> integerPaymentStatusEventReceiverRecord) {
        return Mono.just(integerPaymentStatusEventReceiverRecord)
                .doOnNext(x -> {
                    int key = x.key();
                    ;
                    PaymentStatusEvent paymentStatusEvent = x.value();
                    log.info("Received payment event key: {}", key);
                    log.info("Received payment event: {}", paymentStatusEvent);
                    // Here you can add logic to process the payment status event
                    x.receiverOffset().acknowledge();
                })
                .retryWhen(retrySpec())
                .then();

    }
    private  Retry retrySpec(){

        return  Retry.fixedDelay(3, Duration.ofSeconds(1))
                .filter(Exception.class::isInstance)
                .onRetryExhaustedThrow((spec,signal) -> signal.failure());
    }
}
