package com.example.service;

import com.example.config.HorariosConfig;
import com.example.model.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    private final HorariosConfig configuracionHorarios;
    private final TurnoService turnoService;

    @Autowired
    public AgendaService(HorariosConfig configuracionHorarios, TurnoService turnoService) {
        this.configuracionHorarios = configuracionHorarios;
        this.turnoService = turnoService;
    }

    public List<LocalDate> generarFechasDisponibles() {
        List<LocalDate> fechasDisponibles = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for (int i = 0; i < 14; i++) {
            LocalDate fecha = hoy.plusDays(i);

            // Verificar si el día tiene horarios disponibles y si no están todos ocupados
            if (!configuracionHorarios.getHorariosPorDia(fecha.getDayOfWeek()).isEmpty() &&
                    !generarHorariosDisponibles(fecha).isEmpty()) {
                fechasDisponibles.add(fecha);
            }
        }

        return fechasDisponibles;
    }

    public List<LocalTime> generarHorariosDisponibles(LocalDate fecha) {
        List<LocalTime> horariosPosibles = configuracionHorarios.getHorariosPorDia(fecha.getDayOfWeek());

        List<Turno> turnosRegistrados = turnoService.obtenerAgendaTurnos();
        List<LocalTime> horariosOcupados = turnosRegistrados.stream()
                .filter(turno -> turno.getFechaHora().toLocalDate().equals(fecha))
                .map(turno -> turno.getFechaHora().toLocalTime())
                .toList();

        return horariosPosibles.stream()
                .filter(horario -> !horariosOcupados.contains(horario))
                .collect(Collectors.toList());
    }
}