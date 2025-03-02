package com.example.tests;

import com.example.util.validator.ValidadorPatente;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class ValidadorPatenteTest {

    @Test
    public void testPatenteValidaFormatoViejo() {
        assertTrue(ValidadorPatente.validar("ABC123")); // Formato viejo válido
    }

    @Test
    public void testPatenteValidaFormatoNuevo() {
        assertTrue(ValidadorPatente.validar("AB123CD")); // Formato nuevo válido
    }

    @Test
    public void testPatenteInvalida() {
        assertFalse(ValidadorPatente.validar("A1B2C3")); // Formato inválido
        assertFalse(ValidadorPatente.validar("123ABC")); // Formato inválido
        assertFalse(ValidadorPatente.validar("ABCD123")); // Formato inválido
        assertFalse(ValidadorPatente.validar("ABC123A")); // Formato inválido
        assertFalse(ValidadorPatente.validar("AB123")); // Formato inválido
        assertFalse(ValidadorPatente.validar("")); // Patente vacía
        assertFalse(ValidadorPatente.validar(null)); // Patente nula
    }
}