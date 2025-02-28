package com.example.tests;

import com.example.model.Cliente;
import com.example.model.TipoServicio;
import com.example.model.Turno;
import com.example.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TurnoClienteRelationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TurnoRepository turnoRepository;

    @Test
    public void testRelacionTurnoCliente() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Pérez");
        entityManager.persist(cliente);

        Turno turno = new Turno(LocalDateTime.now(), TipoServicio.LAVADO_BASICO, "ABC123", cliente, 100.0);
        entityManager.persist(turno);
        entityManager.flush();

        Optional<Turno> turnoEncontrado = turnoRepository.findById(turno.getId());

        assertTrue(turnoEncontrado.isPresent()); // Verifica que el turno fue encontrado
        assertEquals(cliente.getId(), turnoEncontrado.get().getCliente().getId()); // Verifica la relación con el cliente
        assertEquals("cliente@example.com", turnoEncontrado.get().getCliente().getEmail()); // Verifica el email del cliente
        assertEquals("Juan Pérez", turnoEncontrado.get().getCliente().getNombre()); // Verifica el nombre del cliente
    }
}