package com.example.exception;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private List<String> errors = new ArrayList<>();

    public void addError(String error) {
        this.errors.add(error);
    }

}