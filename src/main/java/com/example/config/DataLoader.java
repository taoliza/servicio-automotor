package com.example.config;

import com.example.model.Cliente;
import com.example.model.Turno;
import com.example.repository.ClienteRepository;
import com.example.repository.TurnoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.repository.ServicioRepository;
import com.example.model.Servicio;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("!test")
public class DataLoader {

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TurnoRepository turnoRepository;

    @PostConstruct
    public void loadData() {
        if (servicioRepository.count() == 0) {
            servicioRepository.save(new Servicio("Lavado Basico", 20000.00));
            servicioRepository.save(new Servicio("Lavado Completo", 28000.00));
            servicioRepository.save(new Servicio("Lavado Premium", 35000.00));
            servicioRepository.save(new Servicio("Alineacion y Balanceo", 15000.00));
            servicioRepository.save(new Servicio("Alineación y Balanceo con Cubiertas", 25000.00));
            servicioRepository.save(new Servicio("Cambio de Filtros Basico (Diesel)", 12000.00));
            servicioRepository.save(new Servicio("Cambio de Filtros Basico (Nafta)", 10000.00));
            servicioRepository.save(new Servicio("Cambio de Filtros Alto Rendimiento (Diesel)", 18000.00));
            servicioRepository.save(new Servicio("Cambio de Filtros Alto Rendimiento (Nafta)", 15000.00));
        }

        if (clienteRepository.count() == 0) {
            // Cliente con 6 servicios realizados
            Cliente cliente1 = new Cliente("juan.perez@mail.com", "Juan Pérez", "3329690254");
            cliente1.setContadorServicios(6);
            clienteRepository.save(cliente1);
            crearTurnos(cliente1, 6, LocalDateTime.now().minusMonths(3)); // 6 servicios anteriores
            // Cliente sin servicios realizados
            Cliente cliente2 = new Cliente("maria.gomez@mail.com", "María Gómez", "3329690255");
            clienteRepository.save(cliente2);

            // Cliente adicional con algunos turnos futuros
            Cliente cliente3 = new Cliente("carlos.martinez@mail.com", "Carlos Martínez", "3329690256");
            cliente3.setContadorServicios(4);
            clienteRepository.save(cliente3);
            crearTurnos(cliente3, 4, LocalDateTime.now().plusMonths(2)); // 4 turnos futuros

            // Cliente con 2 servicios pasados y 2 futuros
            Cliente cliente4 = new Cliente("ana.rodriguez@mail.com", "Ana Rodríguez", "3329690257");
            cliente4.setContadorServicios(4);
            clienteRepository.save(cliente4);
            crearTurnos(cliente4, 2, LocalDateTime.now().minusMonths(1)); // 2 turnos pasados
            crearTurnos(cliente4, 2, LocalDateTime.now().plusMonths(1)); // 2 turnos futuros
        }
    }

    private void crearTurnos(Cliente cliente, int cantidad, LocalDateTime fechaInicio) {
        List<Servicio> servicios = servicioRepository.findAll();
        for (int i = 0; i < cantidad; i++) {
            LocalDateTime fechaTurno = fechaInicio.plusDays(i);
            if (i % 2 == 0) {
                fechaTurno = fechaTurno.plusMonths(2);
            } else {
                fechaTurno = fechaTurno.minusMonths(2);
            }
            Servicio servicio = servicios.get(i % servicios.size()); // Selección cíclica de servicios

            Turno turno = new Turno();
            turno.setFechaHora(fechaTurno);
            turno.setTipoServicio(servicio.getId());
            turno.setPatenteVehiculo("ABC123" + i); // Patente genérica
            turno.setEmailCliente(cliente.getEmail());
            turno.setPrecio(servicio.getPrecio());
            turnoRepository.save(turno);
        }
    }

}
