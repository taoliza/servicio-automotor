package com.example.tests;

import com.example.model.Cliente;
import com.example.model.TipoServicio;
import com.example.model.Turno;
import com.example.util.TurnoUtil;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TurnoUtilTest {
    @Test
    public void testCrearTurnoClientePremium() {
        Cliente clientePremium = new Cliente("cliente@example.com", "Juan Pérez");
        clientePremium.setContadorServicios(6); // Cliente premium

        Turno turno = TurnoUtil.crearTurno(
                LocalDateTime.now(), // Fecha y hora actual
                "ABC123",           // Patente
                clientePremium,     // Cliente premium
                TipoServicio.LAVADO_BASICO // Tipo de servicio
        );

        assertEquals(0, turno.getPrecio()); // El precio debe ser 0 para clientes premium
    }

    @Test
    public void testCrearTurnoClienteNoPremium() {
        Cliente clienteNoPremium = new Cliente("cliente@example.com", "Juan Pérez");
        clienteNoPremium.setContadorServicios(3); // Cliente no premium

        Turno turno = TurnoUtil.crearTurno(
                LocalDateTime.now(),
                "ABC123",
                clienteNoPremium,
                TipoServicio.LAVADO_BASICO
        );

        assertEquals(100, turno.getPrecio());
    }
}