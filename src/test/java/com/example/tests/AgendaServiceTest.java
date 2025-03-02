package com.example.tests;

import com.example.config.HorariosConfig;
import com.example.model.Turno;
import com.example.service.AgendaService;
import com.example.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @Mock
    private HorariosConfig configuracionHorarios;

    @Mock
    private TurnoService turnoService;

    @InjectMocks
    private AgendaService agendaService;

    @Test
    public void testGenerarFechasDisponibles() {
        when(configuracionHorarios.getHorariosPorDia(any(DayOfWeek.class))).thenReturn(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0)));
        when(turnoService.obtenerAgendaTurnos()).thenReturn(Collections.emptyList());

        List<LocalDate> fechasDisponibles = agendaService.generarFechasDisponibles();

        assertNotNull(fechasDisponibles);
        assertEquals(14, fechasDisponibles.size()); // 14 días, todos disponibles
    }

    @Test
    public void testGenerarFechasDisponibles_TodosLosHorariosOcupados() {
        LocalDate fechaFija = LocalDate.of(2023, 10, 2); // Lunes 2 de octubre de 2023
        when(configuracionHorarios.getHorariosPorDia(any(DayOfWeek.class))).thenReturn(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0)));

        Turno turnoOcupado = new Turno(LocalDateTime.of(fechaFija, LocalTime.of(9, 0)), 1L, "ABC123", "cliente@example.com", 100);
        when(turnoService.obtenerAgendaTurnos()).thenReturn(List.of(turnoOcupado));

        List<LocalDate> fechasDisponibles = agendaService.generarFechasDisponibles();

        assertNotNull(fechasDisponibles);
        assertFalse(fechasDisponibles.contains(fechaFija)); // La fecha fija no debe estar disponible
    }

    @Test
    public void testGenerarHorariosDisponibles() {
        LocalDate fechaFija = LocalDate.of(2023, 10, 2); // Lunes 2 de octubre de 2023
        when(configuracionHorarios.getHorariosPorDia(fechaFija.getDayOfWeek())).thenReturn(Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0)));

        Turno turnoOcupado = new Turno(LocalDateTime.of(fechaFija, LocalTime.of(9, 0)), 1L, "ABC123", "cliente@example.com", 100);
        when(turnoService.obtenerAgendaTurnos()).thenReturn(List.of(turnoOcupado));

        List<LocalTime> horariosDisponibles = agendaService.generarHorariosDisponibles(fechaFija);

        assertEquals(1, horariosDisponibles.size()); // Solo el horario de 12:00 está disponible
        assertFalse(horariosDisponibles.contains(LocalTime.of(9, 0))); // El horario de 9:00 está ocupado
        assertTrue(horariosDisponibles.contains(LocalTime.of(12, 0))); // El horario de 12:00 está disponible
    }
}