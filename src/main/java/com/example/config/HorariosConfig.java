package com.example.config;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Component
public class HorariosConfig {

    private final Map<DayOfWeek, List<LocalTime>> horariosPorDia;

    public HorariosConfig() {
        this.horariosPorDia = new EnumMap<>(DayOfWeek.class);
        inicializarHorariosPorDefecto();
    }

    private void inicializarHorariosPorDefecto() {
        // Lunes a viernes
        horariosPorDia.put(DayOfWeek.MONDAY, Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0)));
        horariosPorDia.put(DayOfWeek.TUESDAY, Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0)));
        horariosPorDia.put(DayOfWeek.WEDNESDAY, Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0)));
        horariosPorDia.put(DayOfWeek.THURSDAY, Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0)));
        horariosPorDia.put(DayOfWeek.FRIDAY, Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0)));

        // Sábado
        horariosPorDia.put(DayOfWeek.SATURDAY, Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0)));

        // Domingo (sin horarios)
        horariosPorDia.put(DayOfWeek.SUNDAY, Collections.emptyList());
    }

    public List<LocalTime> getHorariosPorDia(DayOfWeek dia) {
        return horariosPorDia.getOrDefault(dia, Collections.emptyList());
    }

    public void agregarHorario(DayOfWeek dia, LocalTime horario) {
        horariosPorDia.computeIfAbsent(dia, k -> new ArrayList<>()).add(horario);
    }

    public void eliminarHorario(DayOfWeek dia, LocalTime horario) {
        horariosPorDia.getOrDefault(dia, Collections.emptyList()).remove(horario);
    }
}