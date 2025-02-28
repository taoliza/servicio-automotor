package com.example.service;

import com.example.model.Cliente;
import com.example.model.TipoServicio;
import com.example.model.Turno;
import com.example.repository.TurnoRepository;
import com.example.util.TurnoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final ClienteService clienteService;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, ClienteService clienteService) {
        this.turnoRepository = turnoRepository;
        this.clienteService = clienteService;
    }


    public Turno crearTurno(LocalDateTime fechaHora, String patenteVehiculo, String emailCliente, TipoServicio tipoServicio) {
        Optional<Cliente> cliente= clienteService.obtenerClientePorEmail(emailCliente);
        if (cliente.isEmpty())
            return null;
        Turno turno = TurnoUtil.crearTurno(fechaHora, patenteVehiculo, cliente.get(), tipoServicio);
        clienteService.incrementarContadorServicios(emailCliente); // Incrementar contador de servicios
        return turnoRepository.save(turno);
    }

    public List<Turno> listarTurnosPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return turnoRepository.findByFechaHoraBetween(inicio, fin); // Busca turnos en un rango de fechas
    }

    // Historial de turnos: turnos pasados
    public List<Turno> obtenerHistorialTurnos() {
        LocalDateTime ahora = LocalDateTime.now();
        return turnoRepository.findByFechaHoraBefore(ahora);
    }

    // Agenda de turnos: turnos futuros
    public List<Turno> obtenerAgendaTurnos() {
        LocalDateTime ahora = LocalDateTime.now();
        return turnoRepository.findByFechaHoraAfter(ahora);
    }

    // Orden de trabajo actual: el turno más reciente que aún no ha comenzado
    public Optional<Turno> obtenerOrdenTrabajoActual() {
        LocalDateTime ahora = LocalDateTime.now();
        List<Turno> turnos = turnoRepository.findTurnoActual(ahora);
        return turnos.isEmpty() ? Optional.empty() : Optional.of(turnos.get(0));
    }
}