package com.ead.authuser.services;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findall();
    Optional<UserModel> findById(UUID id);
    void delete(UserModel user);
    void save(UserModel user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
