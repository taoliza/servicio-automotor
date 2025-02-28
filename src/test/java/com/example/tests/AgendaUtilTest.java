package com.example.tests;

import com.example.model.TipoServicio;
import com.example.model.Turno;
import com.example.util.AgendaUtil;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaUtilTest {

    @Test
    public void testGenerarFechasDisponibles() {
        // Act
        List<LocalDate> fechasDisponibles = AgendaUtil.generarFechasDisponibles();

        // Assert
        assertEquals(12, fechasDisponibles.size()); // 14 días menos 2 domingos
        assertFalse(fechasDisponibles.contains(LocalDate.now().with(DayOfWeek.SUNDAY))); // No debe incluir domingos
    }

    @Test
    public void testGenerarHorariosDisponiblesSinTurnos() {
        // Arrange
        LocalDate fecha = LocalDate.now().plusDays(1); // Mañana
        List<Turno> turnosRegistrados = List.of(); // Sin turnos registrados

        // Act
        List<LocalTime> horariosDisponibles = AgendaUtil.generarHorariosDisponibles(fecha, turnosRegistrados);

        // Assert
        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            assertEquals(2, horariosDisponibles.size()); // Sábado: 2 horarios
        } else {
            assertEquals(4, horariosDisponibles.size()); // Lunes a viernes: 4 horarios
        }
    }

    @Test
    public void testGenerarHorariosDisponiblesConTurnos() {
        // Arrange
        LocalDate fecha = LocalDate.now().plusDays(1); // Mañana
        LocalDateTime fechaHoraOcupada = LocalDateTime.of(fecha, LocalTime.of(9, 0)); // Turno a las 9:00
        Turno turnoOcupado = new Turno(fechaHoraOcupada, "ABC123", "cliente@example.com", TipoServicio.LAVADO_BASICO, 100.0);
        List<Turno> turnosRegistrados = List.of(turnoOcupado);

        // Act
        List<LocalTime> horariosDisponibles = AgendaUtil.generarHorariosDisponibles(fecha, turnosRegistrados);

        // Assert
        assertFalse(horariosDisponibles.contains(LocalTime.of(9, 0))); // El horario de 9:00 está ocupado
        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            assertEquals(1, horariosDisponibles.size()); // Sábado: 1 horario disponible
        } else {
            assertEquals(3, horariosDisponibles.size()); // Lunes a viernes: 3 horarios disponibles
        }
    }
}