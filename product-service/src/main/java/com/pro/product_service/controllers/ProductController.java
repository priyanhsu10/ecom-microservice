package com.pro.product_service.controllers;

import com.pro.product_service.dtos.ProductDto;
import com.pro.product_service.dtos.ProductRequest;
import com.pro.product_service.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    @PostMapping
    Mono<ProductDto> createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    Mono<ProductDto> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping
    Flux<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    Mono<Boolean> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    Mono<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto product) {
        return productService.updateProduct(id, product);
    }
}
