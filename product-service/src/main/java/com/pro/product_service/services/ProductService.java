package com.pro.product_service.services;

import com.pro.product_service.dtos.ProductDto;
import com.pro.product_service.dtos.ProductRequest;
import com.pro.product_service.entites.Product;
import com.pro.product_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    @Override
    public Mono<ProductDto> createProduct(ProductRequest request) {
        var product=new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());
        product.setStockQuantity(request.getStockQuantity());
        return productRepository.save(product)
                .map(ProductDto::new)
                .switchIfEmpty(Mono.error(new RuntimeException("Product creation failed")));
    }

    @Override
    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id)
                .map(ProductDto::new)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    @Override
    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll().map(ProductDto::new);
    }

    @Override
    public Mono<Boolean> deleteProduct(String id) {
        return productRepository.deleteById(id)
                .thenReturn(true)
                .onErrorReturn(false); // Return false if deletion fails
    }

    @Override
    public Mono<ProductDto> updateProduct(String id, ProductDto product) {
        return  productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setImageUrl(product.getImageUrl());
                    return productRepository.save(existingProduct);
                })
                .map(ProductDto::new)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found for update")));
    }
}
