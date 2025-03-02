package com.example.util.validator;

public class ValidadorEmail {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static boolean validar(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Email nulo o vacío no es válido
        }
        return email.matches(EMAIL_REGEX);
    }
}
