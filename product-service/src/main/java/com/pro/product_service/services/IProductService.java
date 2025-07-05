package com.pro.product_service.services;

import com.pro.product_service.dtos.ProductDto;
import com.pro.product_service.dtos.ProductRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    // Define methods for product service operations here
    // For example:
     Mono<ProductDto> createProduct(ProductRequest request);
     Mono<ProductDto> getProductById(String id);
     Flux<ProductDto> getAllProducts();
     Mono<Boolean> deleteProduct(String id);
     Mono<ProductDto> updateProduct(String id, ProductDto product);
}
