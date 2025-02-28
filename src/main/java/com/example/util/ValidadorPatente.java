package com.example.util;

public class ValidadorPatente {

    private static final String PATENTE_REGEX = "^[A-Z]{3}[0-9]{3}$|^[A-Z]{2}[0-9]{3}[A-Z]{2}$";

    public static boolean validar(String patente) {
        if (patente == null || patente.isEmpty()) {
            return false; // Patente nula o vacía no es válida
        }
        return patente.matches(PATENTE_REGEX);
    }
}