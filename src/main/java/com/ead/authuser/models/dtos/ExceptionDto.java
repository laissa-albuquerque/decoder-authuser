package com.ead.authuser.models.dtos;

import org.springframework.http.HttpStatus;

public record ExceptionDto(String message, HttpStatus status) {
}
