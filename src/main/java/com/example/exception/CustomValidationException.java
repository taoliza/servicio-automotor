package com.example.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
@Setter
public class CustomValidationException extends ResponseStatusException {
    private final List<String> errors;
    public CustomValidationException(HttpStatus status, List<String> errors) {
        super(status, "Validation failed");
        this.errors = errors;
    }

}