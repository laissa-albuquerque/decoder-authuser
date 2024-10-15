package com.ead.authuser.controllers;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.dtos.UserDto;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(
            SpecificationTemplate.UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserModel> userModelPage = userService.findall(spec, pageable);

        if(userModelPage.hasContent()) {
            userModelPage.forEach(user -> {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
            });
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

   @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID id) {
       Optional<UserModel> userModelOptional = userService.findById(id);
       if (userModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

       return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
   }

   @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
   }

   @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID id, @RequestBody @Validated(UserDto.UserView.UserPut.class) @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
       Optional<UserModel> userModelOptional = userService.findById(id);
       if (userModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

       var userModelToUpdate = userModelOptional.get();
       userModelToUpdate.setFullName(userDto.fullName());
       userModelToUpdate.setPhoneNumber(userDto.phoneNumber());
       userService.save(userModelToUpdate);
       return ResponseEntity.status(HttpStatus.OK).body(userModelToUpdate);
   }

    @PatchMapping("/changePassword/{userId}")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID id, @RequestBody @Validated(UserDto.UserView.PasswordPut.class) @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        var userModelToUpdate = userModelOptional.get();
        if(!userDto.oldPassword().equals(userModelToUpdate.getPassword())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Old password does not match");
        userModelToUpdate.setPassword(userDto.password());
        userService.save(userModelToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PatchMapping("/changeImage/{userId}")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID id, @RequestBody @Validated(UserDto.UserView.ImagePut.class) @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        var userModelToUpdate = userModelOptional.get();
        userModelToUpdate.setImageUrl(userDto.imageUrl());
        userService.save(userModelToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("Image updated successfully");
    }
}
