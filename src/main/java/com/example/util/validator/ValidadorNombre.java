package com.example.util.validator;

public class ValidadorNombre {

    private static final String NOMBRE_REGEX = "^[\\p{L} ]+$";
    //     \\p{L} es cualquier letra Unicode (mayúsculas, minúsculas, con acentos, etc.).

    public static String validar(String nombre) {
        // Nombre vacío
        if (nombre == null || nombre.isEmpty())
            return "El nombre no puede estar vacío.";
        // Eliminar espacios adicionales al inicio y al final
        nombre = nombre.trim();
        // Validar longitud
        if (nombre.length() < 2 || nombre.length() > 50)
            return "El nombre debe tener entre 2 y 50 caracteres.";
        // Validar caracteres
        if (!nombre.matches(NOMBRE_REGEX))
            return "El nombre solo puede contener letras y espacios.";
        return null; // Si el nombre es válido, retorna null
    }

}