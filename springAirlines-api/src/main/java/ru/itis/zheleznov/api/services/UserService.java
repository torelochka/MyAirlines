package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> userByEmail(String email);
    Optional<UserDto> userByEmailAndPassword(String email, String password);

    Optional<UserDto> userById(Long id);
}
