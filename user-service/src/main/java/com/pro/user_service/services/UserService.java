package com.pro.user_service.services;

import com.pro.user_service.dtos.CreateUserRequest;
import com.pro.user_service.dtos.UserDto;
import com.pro.user_service.entities.Address;
import com.pro.user_service.entities.User;
import com.pro.user_service.repositories.AddressRepository;
import com.pro.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public Mono<UserDto> createUser(CreateUserRequest userRequest) {

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user)
                .publishOn(Schedulers.boundedElastic())

                .doOnNext(x -> {
                    //save address
                    System.out.println("Saving address for user: " + x.getId());
                    Address  address = new Address();
                    address.setAddressLine(userRequest.getAddressLine());
                    address.setStreet(userRequest.getStreet());
                    address.setCity(userRequest.getCity());
                    address.setState(userRequest.getState());
                    address.setPostalCode(userRequest.getPostalCode());
                    address.setCountry(userRequest.getCountry());
                    address.setUserId(x.getId());
                    address.setAddressLine(userRequest.getAddressLine());
                    addressRepository.save(address)
                            .subscribe();
                })
                .map(x -> new UserDto(user));


    }

    @Override
    public Mono<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::new);
    }

    @Override
    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll().map(UserDto::new);
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<UserDto> updateUser(Long id, UserDto user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPhoneNumber(user.getPhoneNumber());
                    return userRepository.save(existingUser);
                })
                .map(UserDto::new);
    }
}
