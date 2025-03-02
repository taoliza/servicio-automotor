package com.example.util.validator;

public class ValidadorTelefono {

    private static final String TELEFONO_REGEX = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{3,4}$";

    public static boolean validar(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            return false; // Teléfono nulo o vacío no es válido
        }
        return telefono.matches(TELEFONO_REGEX);
    }
}