package com.pro.user_service.controllers;

import com.pro.user_service.dtos.CreateUserRequest;
import com.pro.user_service.dtos.UserDto;
import com.pro.user_service.services.IUserService;
import com.pro.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    Mono<UserDto> createUser(@RequestBody CreateUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    Mono<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    Flux<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    Mono<UserDto> updateUser(Long id, UserDto user) {
        return userService.updateUser(id, user);
    }
}
