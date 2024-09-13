package com.ead.authuser.controllers;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.dtos.UserDto;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findall());
    }

   @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID id) {
       Optional<UserModel> userModelOptional = userService.findById(id);
       if (userModelOptional.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
       }
       return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
   }

   @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
   }

   @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID id, @RequestBody @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
       Optional<UserModel> userModelOptional = userService.findById(id);
       if (userModelOptional.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
       }
       var userModelToUpdate = userModelOptional.get();
       userModelToUpdate.setFullName(userDto.fullName());
       userModelToUpdate.setPhoneNumber(userDto.phoneNumber());
       userService.save(userModelToUpdate);
       return ResponseEntity.status(HttpStatus.OK).body(userModelToUpdate);
   }

    @PatchMapping("/changePassword/{userId}")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID id, @RequestBody @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var userModelToUpdate = userModelOptional.get();

        if(!userDto.oldPassword().equals(userModelToUpdate.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password does not match");
        }
        userModelToUpdate.setPassword(userDto.password());
        userService.save(userModelToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PatchMapping("/changeImage/{userId}")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID id, @RequestBody @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var userModelToUpdate = userModelOptional.get();
        userModelToUpdate.setImageUrl(userDto.imageUrl());
        userService.save(userModelToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("Image updated successfully");
    }
}
