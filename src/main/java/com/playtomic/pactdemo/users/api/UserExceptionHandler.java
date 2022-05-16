package com.playtomic.pactdemo.users.api;

import com.playtomic.pactdemo.users.domain.UserNotFoundException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUSerNotFoundException(UserNotFoundException ex) {
        Map<String, Object> body = Map.of("message", ex.getMessage());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

}
