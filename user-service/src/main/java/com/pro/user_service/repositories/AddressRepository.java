package com.pro.user_service.repositories;

import com.pro.user_service.dtos.AddressDto;
import com.pro.user_service.entities.Address;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AddressRepository extends ReactiveCrudRepository<Address, Long> {
    Mono<Address> findByUserIdAndId(Long userId, Long id);

    Flux<Address> findAllByUserId(Long userId);

    Mono<Boolean> deleteByUserIdAndId(Long userId,long id);
    // Additional query methods can be defined here if needed
}
