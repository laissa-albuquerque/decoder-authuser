package com.ead.authuser.models.dtos;

import com.ead.authuser.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        @JsonView(UserView.RegistrationPost.class)
        @NotBlank(groups = UserView.RegistrationPost.class)
        @UsernameConstraint(groups = UserView.RegistrationPost.class)
        @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class)
        String username,

        @JsonView(UserView.RegistrationPost.class)
        @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        @Email(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        String email,

        @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
        @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        @Size(min = 6, max = 20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        String password,

        @JsonView(UserView.PasswordPut.class)
        @NotBlank(groups = UserView.PasswordPut.class)
        @Size(min = 6, max = 20, groups = UserView.PasswordPut.class)
        String oldPassword,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        @Size(min = 4, max = 100, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
        String fullName,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        @Size(min = 12, max = 12, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
        String phoneNumber,

        @JsonView(UserView.RegistrationPost.class)
        @Size(min = 11, max = 11, groups = UserView.RegistrationPost.class)
        String cpf,

        @JsonView(UserView.ImagePut.class)
        @NotBlank(groups = UserView.ImagePut.class)
        String imageUrl){

    public interface UserView {
        public static interface RegistrationPost{}
        public static interface UserPut{}
        public static interface PasswordPut{}
        public static interface ImagePut{}
    }

}
