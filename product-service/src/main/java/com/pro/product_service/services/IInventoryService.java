package com.pro.product_service.services;

import reactor.core.publisher.Mono;

public interface IInventoryService {


    Mono<Boolean> checkStock(String productId, int quantity);

    Mono<Void> updateStock(String productId, int quantity);

    Mono<Integer> getStock(String productId);
}
