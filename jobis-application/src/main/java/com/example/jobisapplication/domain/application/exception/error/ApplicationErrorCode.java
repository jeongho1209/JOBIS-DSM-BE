package com.example.jobisapplication.domain.application.exception.error;

import com.example.jobisapplication.common.error.ErrorProperty;
import com.example.jobisapplication.common.error.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements ErrorProperty {

    INVALID_DATE(HttpStatus.BAD_REQUEST, "Invalid Date"),
    APPLICATION_STATUS_CANNOT_CHANGE(HttpStatus.BAD_REQUEST, "ApplicationEntity Status Cannot be changed"),

    INVALID_STUDENT(HttpStatus.UNAUTHORIZED, "Invalid Student"),
    INVALID_GRADE(HttpStatus.UNAUTHORIZED, "Invalid Grade"),

    FIELD_TRAIN_DATE_CANNOT_CHANGE(HttpStatus.FORBIDDEN, "Field Train Date Cannot Changed"),
    APPLICATION_CANNOT_DELETE(HttpStatus.FORBIDDEN, "ApplicationEntity Cannot Deleted"),

    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "ApplicationEntity Not Found"),

    APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "ApplicationEntity Already Exists");

    private final HttpStatus status;
    private final String message;
}
