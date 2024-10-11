package com.ead.authuser.controllers;

import com.ead.authuser.controllers.mappers.UserMapper;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.dtos.UserDto;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AutenticationController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if(userService.existsByUsername(userDto.username())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken");
        if(userService.existsByEmail(userDto.email())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken");
        UserModel userRegister = userMapper.toModel(userDto);
        userService.save(userRegister);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegister);
    }
}
