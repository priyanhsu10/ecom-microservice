package com.pro.order_service.config;

import com.pro.order_service.constants.KafkaConstants;
import com.pro.order_service.kafka.OrderStatusEvent;
import com.pro.order_service.kafka.PaymentStatusEvent;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.Map;

@Configuration
public class OrderKafkaConfig {

    @Value("${kafka.bootstrap-server}")
    private String KAFKA_BOOTSTRAP_SERVERS;
    @Value("${kafka.payment.group.instance.id}")
    private String paymentGroupInstanceId;
    @Value("${kafka.order.group.instance.id}")
    private String orderGroupInstanceId;

    // consumers
    @Bean
    public ReceiverOptions<Integer, PaymentStatusEvent> paymentReceiverOption() {

        var config = Map.<String, Object>of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "*",
                ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.PAYMENT_GROUP_ID,
                ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, paymentGroupInstanceId
        );

        return ReceiverOptions.<Integer, PaymentStatusEvent>create(config).subscription(Collections.singletonList(KafkaConstants.ORDER_PAYMENT_STATUS_TOPIC));
    }

    @Bean
    @Qualifier("kafkaConsumerPaymentTemplate")
    public ReactiveKafkaConsumerTemplate<Integer, PaymentStatusEvent> kafkaConsumerPaymentTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(paymentReceiverOption());
    }

    @Bean
    public ReceiverOptions<Long, OrderStatusEvent> OrderReceiverOption() {

        var config = Map.<String, Object>of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "*",
                ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.ORDER_GROUP_ID,
                ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, orderGroupInstanceId
        );


        return ReceiverOptions.<Long, OrderStatusEvent>create(config).subscription(Collections.singletonList(KafkaConstants.ORDER_STATUS_TOPIC));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<Long, OrderStatusEvent> kafkaConsumerOrderTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(OrderReceiverOption());
    }
    
    

    @Bean
    public SenderOptions<Long,OrderStatusEvent> orderStatusEventSenderOptions() {
        var config=Map.<String, Object>of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "*"
        );
        return SenderOptions.<Long,OrderStatusEvent>create(config);
    }
    @Bean
    public ReactiveKafkaProducerTemplate<Long, OrderStatusEvent> kafkaProducerOrderTemplate() {
        return new ReactiveKafkaProducerTemplate<>(orderStatusEventSenderOptions());
    }
}
