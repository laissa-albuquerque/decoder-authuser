package com.ead.authuser.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(String username, String email, String password, String oldPassword, String fullName, String phoneNumber,
                      String cpf, String imageUrl){}
