package com.ead.authuser.controllers;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.dtos.UserDto;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/auth")
public class AutenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if(userService.existsByUsername(userDto.username())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken");
        if(userService.existsByEmail(userDto.email())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken");
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
