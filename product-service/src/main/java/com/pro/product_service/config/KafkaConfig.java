package com.pro.product_service.config;

import com.pro.product_service.dtos.InventoryUpdate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;
import java.util.Map;

@Configuration

public class KafkaConfig {
    @Value("${kafka.topic.inventory_update}")
    private String inventory_update;
    @Value("${kafka.inventory.group-id}")
    private String GROUP_ID = "product-group";
    @Value("${kafka.bootstrap.server}")
    private String BOOTSTRAP_SERVERS;

    @Bean
    public ReceiverOptions<String, InventoryUpdate> inventoryUpdateReceiverOptions() {
        Map<String, Object> consumerProps = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,"1",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                JsonDeserializer.TRUSTED_PACKAGES, "*"
        );

        return ReceiverOptions.<String, InventoryUpdate>create(consumerProps)
                .subscription(List.of(inventory_update));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, InventoryUpdate> inventoryUpdateReactiveTemplate(@Qualifier("inventoryUpdateReceiverOptions") ReceiverOptions<String, InventoryUpdate> receiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }

    @Bean
    KafkaReceiver<String, InventoryUpdate> inventoryUpdateKafkaReceiver() {
        return KafkaReceiver.create(inventoryUpdateReceiverOptions());
    }

}
