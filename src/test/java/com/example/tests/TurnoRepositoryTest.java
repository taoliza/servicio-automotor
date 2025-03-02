package com.example.tests;

import com.example.model.Cliente;
import com.example.model.Turno;
import com.example.repository.TurnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class TurnoRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TurnoRepository turnoRepository;

    private Cliente cliente;
    private LocalDateTime ahora;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("test@example.com", "Juan", "1173665542");
        entityManager.persist(cliente);
        ahora = LocalDateTime.now();
    }

    @Test
    void testFindByFechaHoraBetween() {
        Long servicioId1 = 1L; // Lavado Básico
        Long servicioId2 = 2L; // Alineación y Balanceo con Cubiertas
        Turno turno1 = new Turno(ahora.minusDays(2), servicioId1, "AB123CD", cliente.getEmail(), 100.0);
        Turno turno2 = new Turno(ahora.plusDays(1), servicioId2, "XY987ZT", cliente.getEmail(), 150.0);
        entityManager.persist(turno1);
        entityManager.persist(turno2);

        List<Turno> turnos = turnoRepository.findByFechaHoraBetween(ahora.minusDays(3), ahora.plusDays(2));

        assertEquals(2, turnos.size());
        assertTrue(turnos.contains(turno1));
        assertTrue(turnos.contains(turno2));
    }

    @Test
    void testFindByFechaHoraBefore() {
        Long servicioId = 2L; // Alineación y Balanceo con Cubiertas
        Turno turno = new Turno(ahora.minusDays(1), servicioId, "LMN456", cliente.getEmail(), 120.0);
        entityManager.persist(turno);

        List<Turno> turnos = turnoRepository.findByFechaHoraBefore(ahora);

        assertEquals(1, turnos.size());
        assertEquals("LMN456", turnos.get(0).getPatenteVehiculo());
        assertEquals(servicioId, turnos.get(0).getTipoServicio()); // Verifica el servicioId
        assertEquals(cliente.getEmail(), turnos.get(0).getEmailCliente()); // Verifica el email del cliente
    }

    @Test
    void testFindByFechaHoraAfter() {
        Long servicioId = 3L; // Lavado Completo
        Turno turno = new Turno(ahora.plusDays(2), servicioId, "PQR789", cliente.getEmail(), 200.0);
        entityManager.persist(turno);

        List<Turno> turnos = turnoRepository.findByFechaHoraAfter(ahora);

        assertEquals(1, turnos.size());
        assertEquals("PQR789", turnos.get(0).getPatenteVehiculo());
        assertEquals(servicioId, turnos.get(0).getTipoServicio()); // Verifica el servicioId
        assertEquals(cliente.getEmail(), turnos.get(0).getEmailCliente()); // Verifica el email del cliente
    }

    @Test
    void testFindTurnoActual() {
        Long servicioId = 4L; // Filtros Alto Rendimiento Nafta
        Turno turno = new Turno(ahora.minusMinutes(30), servicioId, "UVW321", cliente.getEmail(), 300.0);
        entityManager.persist(turno);

        List<Turno> turnos = turnoRepository.findTurnoActual(ahora);

        assertFalse(turnos.isEmpty());
        assertEquals("UVW321", turnos.get(0).getPatenteVehiculo());
        assertEquals(servicioId, turnos.get(0).getTipoServicio()); // Verifica el servicioId
        assertEquals(cliente.getEmail(), turnos.get(0).getEmailCliente()); // Verifica el email del cliente
    }

    @Test
    void testFindByFechaHoraBetween_EmptyRange() {
        List<Turno> turnos = turnoRepository.findByFechaHoraBetween(ahora.plusDays(10), ahora.plusDays(20));

        assertTrue(turnos.isEmpty());
    }
}