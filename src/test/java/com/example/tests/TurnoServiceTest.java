package com.example.tests;

import com.example.model.Cliente;
import com.example.model.TipoServicio;
import com.example.model.Turno;
import com.example.repository.TurnoRepository;
import com.example.service.ClienteService;
import com.example.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private TurnoService turnoService;

    @Test
    public void testCrearTurno() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");
        Turno turno = new Turno(LocalDateTime.now(), TipoServicio.LAVADO_BASICO, "ABC123", cliente, 500.0);

        when(clienteService.obtenerClientePorEmail("cliente@example.com")).thenReturn(Optional.of(cliente));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);

        Turno resultado = turnoService.crearTurno(LocalDateTime.now(), "ABC123", "cliente@example.com", TipoServicio.LAVADO_BASICO);

        assertNotNull(resultado);
        assertEquals("ABC123", resultado.getPatenteVehiculo());
        verify(clienteService, times(1)).incrementarContadorServicios("cliente@example.com");
    }

    @Test
    public void testListarTurnosPorFecha() {
        LocalDateTime inicio = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime fin = LocalDateTime.of(2023, 10, 31, 18, 0);

        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");
        Turno turno1 = new Turno(LocalDateTime.of(2023, 10, 10, 9, 0), TipoServicio.LAVADO_BASICO, "ABC123", cliente, 500.0);
        Turno turno2 = new Turno(LocalDateTime.of(2023, 10, 15, 12, 0), TipoServicio.ALINEACION_BALANCEO, "XYZ789", cliente, 1500.0);

        when(turnoRepository.findByFechaHoraBetween(inicio, fin)).thenReturn(List.of(turno1, turno2));

        List<Turno> resultado = turnoService.listarTurnosPorFecha(inicio, fin);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(turno1));
        assertTrue(resultado.contains(turno2));
    }

    @Test
    public void testObtenerHistorialTurnos() {
        LocalDateTime ahora = LocalDateTime.now();
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");

        Turno turnoPasado1 = new Turno(ahora.minusDays(1), TipoServicio.LAVADO_BASICO, "ABC123", cliente, 500.0);
        Turno turnoPasado2 = new Turno(ahora.minusHours(2), TipoServicio.ALINEACION_BALANCEO, "XYZ789", cliente, 1500.0);

        when(turnoRepository.findByFechaHoraBefore(ahora)).thenReturn(List.of(turnoPasado1, turnoPasado2));

        List<Turno> resultado = turnoService.obtenerHistorialTurnos();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(turnoPasado1));
        assertTrue(resultado.contains(turnoPasado2));
    }

    @Test
    public void testObtenerAgendaTurnos() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");
        Turno turnoFuturo1 = new Turno(LocalDateTime.now().plusDays(1), TipoServicio.LAVADO_BASICO, "ABC123", cliente, 500.0);
        Turno turnoFuturo2 = new Turno(LocalDateTime.now().plusHours(2), TipoServicio.ALINEACION_BALANCEO, "XYZ789", cliente, 1500.0);

        when(turnoRepository.findByFechaHoraAfter(any(LocalDateTime.class))).thenReturn(List.of(turnoFuturo1, turnoFuturo2));

        List<Turno> resultado = turnoService.obtenerAgendaTurnos();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(turnoFuturo1));
        assertTrue(resultado.contains(turnoFuturo2));
    }

    @Test
    public void testObtenerOrdenTrabajoActual_ExisteTurno() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");
        Turno turnoActual = new Turno(LocalDateTime.now().plusHours(1), TipoServicio.LAVADO_BASICO, "ABC123", cliente, 500.0);

        when(turnoRepository.findTurnoActual(any(LocalDateTime.class))).thenReturn(List.of(turnoActual));

        Optional<Turno> resultado = turnoService.obtenerOrdenTrabajoActual();

        assertTrue(resultado.isPresent());
        assertEquals(turnoActual, resultado.get());
    }

    @Test
    public void testObtenerOrdenTrabajoActual_NoExisteTurno() {
        when(turnoRepository.findTurnoActual(any(LocalDateTime.class))).thenReturn(List.of());

        Optional<Turno> resultado = turnoService.obtenerOrdenTrabajoActual();

        assertTrue(resultado.isEmpty());
    }
}