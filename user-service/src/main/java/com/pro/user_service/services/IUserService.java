package com.pro.user_service.services;

import com.pro.user_service.dtos.CreateUserRequest;
import com.pro.user_service.dtos.UserDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IUserService {
     Mono<UserDto> createUser(CreateUserRequest userRequest);
     Mono<UserDto> getUserById(Long id);
     Flux<UserDto> getAllUsers();
     Mono<Void> deleteUser(Long id);
     Mono<UserDto> updateUser(Long id, UserDto user);
}
