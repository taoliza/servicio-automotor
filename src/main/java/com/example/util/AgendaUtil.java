package com.example.util;

import com.example.model.Turno;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaUtil {
    // Lista de las fechas en los proximos 14 dias quitando domingos
    public static List<LocalDate> generarFechasDisponibles() {
        List<LocalDate> fechasDisponibles = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        // Generar fechas para 14 días (2 semanas)
        for (int i = 0; i < 14; i++) {
            LocalDate fecha = hoy.plusDays(i);
            if (fecha.getDayOfWeek() != DayOfWeek.SUNDAY) { // Excluir domingos
                fechasDisponibles.add(fecha);
            }
        }
        return fechasDisponibles;
    }

    // Para una fecha, los horarios no ocupados
    public static List<LocalTime> generarHorariosDisponibles(LocalDate fecha, List<Turno> turnosRegistrados) {
        List<LocalTime> horariosPosibles = new ArrayList<>();

        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            // Sábado: turnos de 9:00 y 12:00
            horariosPosibles.add(LocalTime.of(9, 0));
            horariosPosibles.add(LocalTime.of(12, 0));
        } else {
            // Lunes a viernes: turnos de 9:00, 12:00, 15:00 y 18:00
            horariosPosibles.add(LocalTime.of(9, 0));
            horariosPosibles.add(LocalTime.of(12, 0));
            horariosPosibles.add(LocalTime.of(15, 0));
            horariosPosibles.add(LocalTime.of(18, 0));
        }

        // Filtrar horarios ocupados
        List<LocalTime> horariosOcupados = turnosRegistrados.stream()
                .filter(turno -> turno.getFechaHora().toLocalDate().equals(fecha)) // Turnos para la fecha seleccionada
                .map(turno -> turno.getFechaHora().toLocalTime()) // Obtener solo la hora
                .toList();

        // Devolver horarios no ocupados
        return horariosPosibles.stream()
                .filter(horario -> !horariosOcupados.contains(horario))
                .collect(Collectors.toList());
    }
}

