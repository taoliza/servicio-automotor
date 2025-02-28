package com.example.util;

import com.example.model.Cliente;
import com.example.model.TipoServicio;
import com.example.model.Turno;

import java.time.LocalDateTime;

public class TurnoUtil {
    public static Turno crearTurno(LocalDateTime fechaHora, String patenteVehiculo, Cliente cliente, TipoServicio tipoServicio){
        double precio = calcularPrecio(cliente, tipoServicio);
        return new Turno(fechaHora, patenteVehiculo, cliente.getEmail(), tipoServicio, precio);
    }
    private static double calcularPrecio(Cliente cliente, TipoServicio tipoServicio){
        if(cliente.tieneServicioGratis()) return 0;
        else return 100; //TODO PRECIOS
    }
}
