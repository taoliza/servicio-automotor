package com.example.tests;

import com.example.model.Cliente;
import com.example.model.TipoServicio;
import com.example.model.Turno;
import com.example.repository.TurnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        cliente = new Cliente("test@example.com", "Juan");
        entityManager.persist(cliente);
        ahora = LocalDateTime.now();
    }

    @Test
    void testFindByFechaHoraBetween() {
        Turno turno1 = new Turno(ahora.minusDays(2), TipoServicio.LAVADO_BASICO, "AB123CD", cliente, 100.0);
        Turno turno2 = new Turno(ahora.plusDays(1), TipoServicio.ALINEACION_BALANCEO_CUBIERTAS, "XY987ZT", cliente, 150.0);
        entityManager.persist(turno1);
        entityManager.persist(turno2);

        List<Turno> turnos = turnoRepository.findByFechaHoraBetween(ahora.minusDays(3), ahora.plusDays(2));

        assertEquals(2, turnos.size());
        assertTrue(turnos.contains(turno1));
        assertTrue(turnos.contains(turno2));
    }

    @Test
    void testFindByFechaHoraBefore() {
        Turno turno = new Turno(ahora.minusDays(1), TipoServicio.ALINEACION_BALANCEO_CUBIERTAS, "LMN456", cliente, 120.0);
        entityManager.persist(turno);

        List<Turno> turnos = turnoRepository.findByFechaHoraBefore(ahora);

        assertEquals(1, turnos.size());
        assertEquals("LMN456", turnos.get(0).getPatenteVehiculo());
        assertEquals(TipoServicio.ALINEACION_BALANCEO_CUBIERTAS, turnos.get(0).getTipoServicio());
    }

    @Test
    void testFindByFechaHoraAfter() {
        Turno turno = new Turno(ahora.plusDays(2), TipoServicio.LAVADO_COMPLETO, "PQR789", cliente, 200.0);
        entityManager.persist(turno);

        List<Turno> turnos = turnoRepository.findByFechaHoraAfter(ahora);

        assertEquals(1, turnos.size());
        assertEquals("PQR789", turnos.get(0).getPatenteVehiculo());
        assertEquals(TipoServicio.LAVADO_COMPLETO, turnos.get(0).getTipoServicio());
    }

    @Test
    void testFindTurnoActual() {
        Turno turno = new Turno(ahora.minusMinutes(30), TipoServicio.FILTROS_ALTO_RENDIMIENTO_NAFTA, "UVW321", cliente, 300.0);
        entityManager.persist(turno);

        List<Turno> turnos = turnoRepository.findTurnoActual(ahora);

        assertFalse(turnos.isEmpty());
        assertEquals("UVW321", turnos.get(0).getPatenteVehiculo());
        assertEquals(TipoServicio.FILTROS_ALTO_RENDIMIENTO_NAFTA, turnos.get(0).getTipoServicio());
    }

    @Test
    void testFindByFechaHoraBetween_EmptyRange() {
        List<Turno> turnos = turnoRepository.findByFechaHoraBetween(ahora.plusDays(10), ahora.plusDays(20));

        assertTrue(turnos.isEmpty());
    }
}