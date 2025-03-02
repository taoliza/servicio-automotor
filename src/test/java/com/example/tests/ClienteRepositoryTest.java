package com.example.tests;

import com.example.model.Cliente;
import com.example.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class ClienteRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testFindByEmail() {

        Cliente cliente = new Cliente("cliente@example.com", "Juan Pérez","3329690254");
        entityManager.persist(cliente);
        entityManager.flush();

        Optional<Cliente> encontrado = clienteRepository.findByEmail("cliente@example.com");

        assertTrue(encontrado.isPresent());
        assertEquals("cliente@example.com", encontrado.get().getEmail());
        assertEquals("Juan Pérez", encontrado.get().getNombre());
    }

    @Test
    public void testFindByEmail_NoExiste() {
        Optional<Cliente> encontrado = clienteRepository.findByEmail("noexiste@example.com");

        assertFalse(encontrado.isPresent());
    }
}