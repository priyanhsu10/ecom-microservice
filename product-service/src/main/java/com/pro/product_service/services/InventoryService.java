package com.pro.product_service.services;

import com.pro.product_service.entites.Product;
import com.pro.product_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class InventoryService implements  IInventoryService{
    private  final ProductRepository productRepository;
    @Override
    public Mono<Boolean> checkStock(String productId, int quantity) {
        return productRepository.findById(productId)
                .map(product -> product.getStockQuantity() >= quantity)
                .switchIfEmpty(Mono.just(false)); // If product not found, assume stock is insufficient
    }

    @Override
    public Mono<Void> updateStock(String productId, int quantity) {
        return productRepository.findById(productId)
                .flatMap(product -> {
                    if (product.getStockQuantity() < quantity) {
                        return Mono.error(new RuntimeException("Insufficient stock"));
                    }
                    product.setStockQuantity(product.getStockQuantity() - quantity);
                    return productRepository.save(product).then();
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    @Override
    public Mono<Integer> getStock(String productId) {
        return productRepository.findById(productId).map(Product::getStockQuantity)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }
}
