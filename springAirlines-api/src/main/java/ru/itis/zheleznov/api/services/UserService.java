package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> userByEmail(String email);
}
