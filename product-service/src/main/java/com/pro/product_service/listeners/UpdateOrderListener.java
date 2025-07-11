package com.pro.product_service.listeners;

import com.pro.product_service.dtos.InventoryUpdate;
import com.pro.product_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateOrderListener implements CommandLineRunner {

    private final KafkaReceiver<String, InventoryUpdate> inventoryUpdateKafkaTemplate;
    private final InventoryService inventoryService;

    public Flux<Void> updateInventory() {

        return inventoryUpdateKafkaTemplate.receive()
                .flatMap(this::processInventoryUpdate)
                .onErrorContinue((throwable, o) -> {
                    // Handle error
                    System.err.println("Error processing inventory update: " + throwable.getMessage());
                });
    }

    private Mono<Void> processInventoryUpdate(ConsumerRecord<String, InventoryUpdate> record) {

        return Mono.just(record)

                .flatMap(this::processPipeline)
                .then();

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("--------------------------------Starting update order listener------------------");
        updateInventory().subscribe();
    }

    private Mono<Void> processPipeline(ConsumerRecord<String, InventoryUpdate> consumerRecord) {
        return Mono.just(consumerRecord)
                .flatMap(x -> {
                    InventoryUpdate inventoryUpdate = consumerRecord.value();
                    String productId = inventoryUpdate.getProductId();
                    int quantity = inventoryUpdate.getQuantity();

                    return inventoryService.updateStock(productId, quantity)
                            .doOnSuccess(aVoid -> log.info("Inventory updated for product: {} ", productId))
                            .doOnError(error -> log.info("Failed to update inventory for product: {}  Error: {}", productId, error.getMessage()));


                })
                .then();

    }
}
