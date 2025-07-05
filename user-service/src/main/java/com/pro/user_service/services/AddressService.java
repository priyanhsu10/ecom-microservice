package com.pro.user_service.services;

import com.pro.user_service.dtos.AddressDto;
import com.pro.user_service.dtos.CreateAddressRequest;
import com.pro.user_service.entities.Address;
import com.pro.user_service.repositories.AddressRepository;
import com.pro.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<AddressDto> createAddress(Long userId, CreateAddressRequest request) {
        return userRepository.existsById(userId)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new RuntimeException("User not found"));
                    }
                    // Create Address
                    Address address = new Address();
                    address.setAddressLine(request.getAddressLine());
                    address.setStreet(request.getStreet());
                    address.setCity(request.getCity());
                    address.setState(request.getState());
                    address.setPostalCode(request.getPostalCode());
                    address.setCountry(request.getCountry());
                    address.setUserId(userId);

                    return addressRepository.save(address)
                            .map(AddressDto::new);
                });
    }

    @Override
    public Mono<AddressDto> getAddressById(Long userId, Long id) {
        return addressRepository.findByUserIdAndId(userId, id)
                .map(AddressDto::new)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found")));
    }

    @Override
    public Flux<AddressDto> getAllAddresses(Long userId) {
        return addressRepository.findAllByUserId(userId)
                .map(AddressDto::new);

    }

    @Override
    public Mono<Boolean> deleteAddress(Long userId, Long id) {
        return addressRepository.deleteByUserIdAndId(userId, id);

    }

    @Override
    public Mono<AddressDto> updateAddress(Long userId, Long id, AddressDto address) {
        return addressRepository.findByUserIdAndId(userId, id)
                .flatMap(address1 -> {
                    address1.setAddressLine(address.getAddressLine());
                    address1.setStreet(address.getStreet());
                    address1.setCity(address.getCity());
                    address1.setState(address.getState());
                    address1.setPostalCode(address.getPostalCode());
                    address1.setCountry(address.getCountry());
                    return addressRepository.save(address1)
                            .map(AddressDto::new);
                }).switchIfEmpty(Mono.error(new RuntimeException("Address not found")));
    }
}
