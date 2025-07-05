package com.pro.user_service.services;

import com.pro.user_service.dtos.AddressDto;
import com.pro.user_service.dtos.CreateAddressRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAddressService {
     Mono<AddressDto> createAddress(Long userId,CreateAddressRequest request);
     Mono<AddressDto> getAddressById(Long userId,Long id);
     Flux<AddressDto> getAllAddresses(Long userId);
     Mono<Boolean> deleteAddress(Long userId,Long id);
     Mono<AddressDto> updateAddress(Long userId,Long id, AddressDto address);
}
