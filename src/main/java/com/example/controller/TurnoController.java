package com.example.controller;

import com.example.model.Servicio;
import com.example.model.Turno;
import com.example.service.AgendaService;
import com.example.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
@CrossOrigin(origins = "http://localhost:3000")
public class TurnoController {

    private final TurnoService turnoService;
    private final AgendaService agendaService;

    public TurnoController(TurnoService turnoService, AgendaService agendaService) {
        this.turnoService = turnoService;
        this.agendaService = agendaService;
    }

    @GetMapping
    public ResponseEntity<List<Turno>> obtenerTurnos() {
        List<Turno> turnos = turnoService.obtenerTurnos();
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> obtenerTurnoPorId(@PathVariable Long id) {
        Optional<Turno> turno = turnoService.obtenerTurnoPorId(id);
        return turno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/proximos")
    public ResponseEntity<List<Turno>> obtenerProximosTurnos() {
        List<Turno> turnos = turnoService.obtenerAgendaTurnos();
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Turno>> obtenerHistorialTurnos() {
        List<Turno> turnos = turnoService.obtenerHistorialTurnos();
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/actual")
    public ResponseEntity<Turno> obtenerTurnoActual() {
        Turno turno = turnoService.obtenerOrdenTrabajoActual().orElse(null);
        return turno!=null?ResponseEntity.ok(turno):ResponseEntity.notFound().build();
    }

    @GetMapping("/fechas-disponibles")
    public ResponseEntity<List<LocalDate>> obtenerFechasDisponibles() {
        List<LocalDate> fechasDisponibles = agendaService.generarFechasDisponibles();
        return ResponseEntity.ok(fechasDisponibles);
    }

    @GetMapping("/horarios-disponibles")
    public ResponseEntity<List<LocalTime>> obtenerHorariosDisponibles(@RequestParam LocalDate fecha) {
        List<LocalTime> horariosDisponibles = agendaService.generarHorariosDisponibles(fecha);
        return ResponseEntity.ok(horariosDisponibles);
    }

    @PostMapping
    public ResponseEntity<Turno> crearTurno(
            @RequestParam String fechaHora,
            @RequestParam Long tipoServicio,
            @RequestParam String patenteVehiculo,
            @RequestParam String emailCliente) {

        Turno turno = turnoService.crearTurno(fechaHora, patenteVehiculo, emailCliente, tipoServicio);
        return ResponseEntity.ok(turno);
    }
}