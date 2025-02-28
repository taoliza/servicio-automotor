package com.example.tests;

import com.example.util.ValidadorEmail;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorEmailTest {

    @Test
    public void testEmailValido() {
        assertTrue(ValidadorEmail.validar("usuario@example.com")); // Email válido
        assertTrue(ValidadorEmail.validar("usuario+alias@example.com"));
        assertTrue(ValidadorEmail.validar("usuario.name@example.com"));
    }

    @Test
    public void testEmailInvalido() {
        assertFalse(ValidadorEmail.validar("usuario@com")); // Dominio incompleto
        assertFalse(ValidadorEmail.validar("usuario.example.com")); // Falta @
        assertFalse(ValidadorEmail.validar("usuario@.com")); // Dominio vacío
        assertFalse(ValidadorEmail.validar("")); // Email vacío
        assertFalse(ValidadorEmail.validar(null)); // Email nulo
    }
}
