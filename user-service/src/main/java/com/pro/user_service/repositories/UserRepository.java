package com.pro.user_service.repositories;

import com.pro.user_service.dtos.UserDto;
import com.pro.user_service.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {
}
