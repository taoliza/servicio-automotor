package com.example.service;

import com.example.exception.CustomValidationException;
import com.example.exception.ErrorResponse;
import com.example.model.Cliente;
import com.example.model.Servicio;
import com.example.model.Turno;
import com.example.repository.ClienteRepository;
import com.example.repository.ServicioRepository;
import com.example.repository.TurnoRepository;
import com.example.util.validator.ValidadorPatente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final ClienteService clienteService;
    private final ServicioRepository servicioRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, ClienteService clienteService, ServicioRepository servicioRepository, ClienteRepository clienteRepository) {
        this.turnoRepository = turnoRepository;
        this.clienteService = clienteService;
        this.servicioRepository = servicioRepository;
        this.clienteRepository = clienteRepository;
    }


    public Turno crearTurno(String fechaHoraString, String patenteVehiculo, String emailCliente, Long id_tipoServicio) {
        ErrorResponse errorResponse = new ErrorResponse();

        // Validar patente
        if (!ValidadorPatente.validar(patenteVehiculo)) {
            errorResponse.addError("Formato de patente invalido");
        }

        // Validar si el cliente existe
        Cliente cliente = clienteService.obtenerClientePorEmail(emailCliente).orElse(null);
        if (cliente == null) {
            errorResponse.addError("No existe un cliente con ese email");
        }

        // Validar si el servicio existe
        Servicio servicio = servicioRepository.findById(id_tipoServicio).orElse(null);
        if (servicio == null) {
            errorResponse.addError("Servicio invalido");
        }

        // Si hay errores, lanzar una excepción personalizada con la lista de errores
        if (!errorResponse.getErrors().isEmpty()) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST, errorResponse.getErrors());
        }

        // Crear y guardar el turno
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraString);
        double precio = cliente.tieneServicioGratis() ? 0.0 : servicio.getPrecio();
        Turno turno = new Turno(fechaHora, id_tipoServicio, patenteVehiculo, emailCliente, precio);

        // Incrementar contador de servicios y guardar el cliente
        cliente.incrementarContadorServicios();
        clienteRepository.save(cliente);

        // Guardar el turno
        return turnoRepository.save(turno);
    }

    public List<Turno> listarTurnosPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return turnoRepository.findByFechaHoraBetween(inicio, fin); // Busca turnos en un rango de fechas
    }

    // Obtener todos los turnos ordenados
    public List<Turno> obtenerTurnos() {
        return turnoRepository.findAll(Sort.by(Sort.Order.asc("fechaHora")));
    }

    // Historial de turnos: turnos pasados
    public List<Turno> obtenerHistorialTurnos() {
        LocalDateTime ahora = LocalDateTime.now();
        // Obtener todos los turnos
        List<Turno> turnos = obtenerTurnos();

        // Filtrar los turnos pasados
        return turnos.stream()
                .filter(turno -> turno.getFechaHora().isBefore(ahora)) // Filtrar pasados
                .collect(Collectors.toList());
    }

    // Agenda de turnos: turnos futuros
    public List<Turno> obtenerAgendaTurnos() {
        LocalDateTime ahora = LocalDateTime.now();
        // Obtener todos los turnos
        List<Turno> turnos = obtenerTurnos();

        // Filtrar los turnos futuros
        return turnos.stream()
                .filter(turno -> turno.getFechaHora().isAfter(ahora)) // Filtrar futuros
                .collect(Collectors.toList()); // Convertir a lista
    }


    // Orden de trabajo actual: el turno más reciente que aún no ha comenzado
    public Optional<Turno> obtenerOrdenTrabajoActual() {
        LocalDateTime ahora = LocalDateTime.now();
        List<Turno> turnos = turnoRepository.findTurnoActual(ahora);
        return turnos.isEmpty() ? Optional.empty() : Optional.of(turnos.get(0));
    }

    public Optional<Turno> obtenerTurnoPorId(Long id) {
        return turnoRepository.findById(id);
    }
}