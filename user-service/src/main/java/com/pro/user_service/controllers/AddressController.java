package com.pro.user_service.controllers;

import com.pro.user_service.dtos.AddressDto;
import com.pro.user_service.dtos.CreateAddressRequest;
import com.pro.user_service.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    // Define endpoints for address management here
    @PostMapping("/{userId}")
    Mono<AddressDto> createAddress(@PathVariable Long userId, @RequestBody CreateAddressRequest request) {
        return addressService.createAddress(userId, request);
    }

    @GetMapping("/{userId}/{id}")
    Mono<AddressDto> getAddressById(@PathVariable Long userId, @PathVariable Long id) {
        return addressService.getAddressById(userId, id);
    }

    @GetMapping("/{userId}")
    Flux<AddressDto> getAllAddresses(@PathVariable Long userId) {
        return addressService.getAllAddresses(userId);
    }

    @DeleteMapping("/{userId}/{id}")
    Mono<Boolean> deleteAddress(@PathVariable Long userId, @PathVariable Long id) {
        return addressService.deleteAddress(userId, id);
    }

    @PostMapping("/{userId}/{id}")
    Mono<AddressDto> updateAddress(Long userId, Long id, AddressDto address) {
        return addressService.updateAddress(userId, id, address);
    }
}
