package com.ead.authuser.controllers.mappers;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.dtos.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserModel toModel(final UserDto userDto) {
        return UserModel.builder()
                .username(userDto.username())
                .email(userDto.email())
                .password(userDto.password())
                .fullName(userDto.fullName())
                .phoneNumber(userDto.phoneNumber())
                .cpf(userDto.cpf())
                .imageUrl(userDto.imageUrl())
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.STUDENT)
                .build();
    }

}
