package com.distantlight.MovieTracker.handlers;

import jakarta.annotation.Priority;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<?> handleConnectException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Нет соединения со сторонним сервисом");
    }

    @ExceptionHandler(StringIndexOutOfBoundsException.class)
    public ResponseEntity<?> handleStringOutOfBoundsException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка в названиях фильмов");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Произошла ошибка на сервере");
    }


}
