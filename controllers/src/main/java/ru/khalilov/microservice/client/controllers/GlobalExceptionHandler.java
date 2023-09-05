package ru.khalilov.microservice.client.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.khalilov.microservice.common.exceptions.RemoteServiceException;
import ru.khalilov.microservice.client.models.ApiError;
import ru.khalilov.microservice.client.exceptions.ValidationException;
import ru.khalilov.microservice.common.exceptions.CredentialsException;
import ru.khalilov.microservice.common.exceptions.NestedDaoException;
import ru.khalilov.microservice.common.exceptions.ServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public ApiError catchException(Exception e) {
        return new ApiError(HttpStatus.I_AM_A_TEAPOT, "Something went wrong and i don't know what exactly :(   |   " + e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError catchHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ApiError(HttpStatus.BAD_REQUEST, "Some validation wasn't passed: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError catchConstraintViolationException(ConstraintViolationException e) {
        return new ApiError(HttpStatus.BAD_REQUEST, "Some arguments are out of borders: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError catchValidationException(ValidationException e) {
        return new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError catchMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ApiError(HttpStatus.BAD_REQUEST, "Invalid format: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiError catchServiceException(ServiceException e) {
        return new ApiError(HttpStatus.NO_CONTENT, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError catchDaoException(NestedDaoException e) {
        return new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError catchCredentialsException(CredentialsException e) {
        return new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError catchRemoteServiceException(RemoteServiceException e) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
