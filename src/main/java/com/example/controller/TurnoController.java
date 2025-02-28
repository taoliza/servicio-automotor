package com.example.controller;

import com.example.model.TipoServicio;
import com.example.model.Turno;
import com.example.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<Turno> crearTurno(
            @RequestParam LocalDateTime fechaHora,
            @RequestParam String patenteVehiculo,
            @RequestParam String emailCliente,
            @RequestParam String tipoServicio) {
        Turno turno = turnoService.crearTurno(fechaHora, patenteVehiculo, emailCliente, TipoServicio.valueOf(tipoServicio));
        return ResponseEntity.ok(turno);
    }

    @GetMapping("/por-fecha")
    public ResponseEntity<List<Turno>> listarTurnosPorFecha(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        List<Turno> turnos = turnoService.listarTurnosPorFecha(inicio, fin);
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Turno>> obtenerHistorialTurnos() {
        List<Turno> historial = turnoService.obtenerHistorialTurnos();
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<Turno>> obtenerAgendaTurnos() {
        List<Turno> agenda = turnoService.obtenerAgendaTurnos();
        return ResponseEntity.ok(agenda);
    }

    @GetMapping("/orden-actual")
    public ResponseEntity<Turno> obtenerOrdenTrabajoActual() {
        Optional<Turno> turno = turnoService.obtenerOrdenTrabajoActual();
        return turno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
