package com.ead.authuser.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        @JsonView(UserView.RegistrationPost.class) String username,
        @JsonView(UserView.RegistrationPost.class) String email,
        @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class}) String password,
        @JsonView(UserView.PasswordPut.class) String oldPassword,
        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class}) String fullName,
        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class}) String phoneNumber,
        @JsonView(UserView.RegistrationPost.class) String cpf,
        @JsonView(UserView.ImagePut.class) String imageUrl){

    public interface UserView{
        public static interface RegistrationPost{}
        public static interface UserPut{}
        public static interface PasswordPut{}
        public static interface ImagePut{}
    }

}
