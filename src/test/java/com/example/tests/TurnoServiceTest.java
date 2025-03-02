package com.example.tests;

import com.example.exception.CustomValidationException;
import com.example.model.Cliente;
import com.example.model.Servicio;
import com.example.model.Turno;
import com.example.repository.ClienteRepository;
import com.example.repository.ServicioRepository;
import com.example.repository.TurnoRepository;
import com.example.service.ClienteService;
import com.example.service.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private TurnoService turnoService;

    private Cliente cliente;
    private Servicio servicio;
    private Turno turno;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("cliente@example.com", "Juan Perez", "543329480668");
        servicio = new Servicio("Lavado Básico", 500.0);
        turno = new Turno(LocalDateTime.now(), 1L, "ABC123", "cliente@example.com", 500.0);
        servicio.setId(1L);
    }

    @Test
    public void testCrearTurno_PatenteValida() {
        when(clienteService.obtenerClientePorEmail("cliente@example.com")).thenReturn(Optional.of(cliente));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Turno resultado = turnoService.crearTurno(LocalDateTime.now().toString(), "ABC123", "cliente@example.com", 1L);

        assertNotNull(resultado);
        assertEquals("ABC123", resultado.getPatenteVehiculo());
        assertEquals(1L, resultado.getTipoServicio());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    @Test
    public void testCrearTurno_PatenteInvalida() {
        CustomValidationException exception = assertThrows(CustomValidationException.class, () -> {
            turnoService.crearTurno(LocalDateTime.now().toString(), "A1B2C3", "cliente@example.com", 1L);
        });

        assertTrue(exception.getErrors().contains("Formato de patente invalido"));
    }

    @Test
    public void testCrearTurno_ClienteNoExiste() {
        when(clienteService.obtenerClientePorEmail("cliente@example.com")).thenReturn(Optional.empty());

        CustomValidationException exception = assertThrows(CustomValidationException.class, () -> {
            turnoService.crearTurno(LocalDateTime.now().toString(), "ABC123", "cliente@example.com", 1L);
        });

        assertTrue(exception.getErrors().contains("No existe un cliente con ese email"));
    }

    @Test
    public void testCrearTurno_ServicioNoExiste() {
        when(clienteService.obtenerClientePorEmail("cliente@example.com")).thenReturn(Optional.of(cliente));
        when(servicioRepository.findById(1L)).thenReturn(Optional.empty());

        CustomValidationException exception = assertThrows(CustomValidationException.class, () -> {
            turnoService.crearTurno(LocalDateTime.now().toString(), "ABC123", "cliente@example.com", 1L);
        });

        assertTrue(exception.getErrors().contains("Servicio invalido"));
    }

    @Test
    public void testListarTurnosPorFecha() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fin = LocalDateTime.now().plusDays(1);
        when(turnoRepository.findByFechaHoraBetween(inicio, fin)).thenReturn(Collections.singletonList(turno));


        List<Turno> resultado = turnoService.listarTurnosPorFecha(inicio, fin);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(turno, resultado.get(0));
    }


    @Test
    public void testObtenerAgendaTurnos() {
        Turno turnoFuturo = new Turno(LocalDateTime.now().plusDays(1), 1L, "ABC123", "cliente@example.com", 500.0);

        when(turnoRepository.findAll(Sort.by(Sort.Order.asc("fechaHora")))).thenReturn(Collections.singletonList(turnoFuturo));

        List<Turno> resultado = turnoService.obtenerAgendaTurnos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size()); // Debería haber 1 turno futuro
        assertEquals(turnoFuturo, resultado.get(0)); // El turno futuro debe ser el mismo que creamos
    }

    @Test
    public void testObtenerHistorialTurnos() {
        Turno turnoPasado = new Turno(LocalDateTime.now().minusDays(1), 1L, "ABC123", "cliente@example.com", 500.0);
        when(turnoRepository.findAll(Sort.by(Sort.Order.asc("fechaHora")))).thenReturn(Collections.singletonList(turnoPasado));

        List<Turno> resultado = turnoService.obtenerHistorialTurnos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(turnoPasado, resultado.get(0));
    }


}