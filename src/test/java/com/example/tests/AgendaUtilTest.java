package com.example.tests;

import com.example.model.Cliente;
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
        List<LocalDate> fechasDisponibles = AgendaUtil.generarFechasDisponibles();

        assertEquals(12, fechasDisponibles.size()); // 14 días menos 2 domingos
        assertFalse(fechasDisponibles.contains(LocalDate.now().with(DayOfWeek.SUNDAY))); // No domingos
    }

    @Test
    public void testGenerarHorariosDisponiblesSinTurnos() {
        LocalDate fecha = LocalDate.now().plusDays(1); // Mañana
        List<Turno> turnosRegistrados = List.of(); // Lista vacia (sin turnos)

        List<LocalTime> horariosDisponibles = AgendaUtil.generarHorariosDisponibles(fecha, turnosRegistrados);

        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            assertEquals(2, horariosDisponibles.size()); // Sábado: 2 horarios
        } else {
            assertEquals(4, horariosDisponibles.size()); // Lunes a viernes: 4 horarios
        }
    }

    @Test
    public void testGenerarHorariosDisponiblesConTurnos() {
        LocalDate fecha = LocalDate.now().plusDays(1); // Mañana
        LocalDateTime fechaHoraOcupada = LocalDateTime.of(fecha, LocalTime.of(9, 0)); // Turno a las 9:00
        Cliente cliente = new Cliente("juan@gmail.com","Juan");
        Turno turnoOcupado = new Turno(fechaHoraOcupada,TipoServicio.LAVADO_BASICO,"AA123BB",cliente,100);
        List<Turno> turnosRegistrados = List.of(turnoOcupado);

        List<LocalTime> horariosDisponibles = AgendaUtil.generarHorariosDisponibles(fecha, turnosRegistrados);

        assertFalse(horariosDisponibles.contains(LocalTime.of(9, 0))); // El horario de 9:00 está ocupado
        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            assertEquals(1, horariosDisponibles.size()); // Sábado: 1 horario disponible
        } else {
            assertEquals(3, horariosDisponibles.size()); // Lunes a viernes: 3 horarios disponibles
        }
    }
}