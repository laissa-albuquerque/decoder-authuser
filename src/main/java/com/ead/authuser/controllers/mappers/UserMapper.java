package com.ead.authuser.controllers.mappers;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.dtos.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserModel toModel(final UserDto user) {
        return UserModel.builder()
                .username(user.username())
                .email(user.email())
                .password(user.password())
                .fullName(user.fullName())
                .phoneNumber(user.phoneNumber())
                .cpf(user.cpf())
                .imageUrl(user.imageUrl())
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.STUDENT)
                .build();
    }

    public UserDto toDto(final UserModel user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .cpf(user.getCpf())
                .imageUrl(user.getImageUrl())
                .build();
    }

}
