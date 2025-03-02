package com.example.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/* Turno u Orden de trabajo */
@Data
@Entity
@NoArgsConstructor
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @NonNull
    @Column(nullable = false)
    Long tipoServicio;

    @NonNull
    @Column(nullable = false)
    private String patenteVehiculo;

    @NonNull
    @Column(nullable = false)
    private String emailCliente;

    @Column(nullable = false)
    private double precio;


    public Turno(@NonNull LocalDateTime fechaHora, @NonNull Long tipoServicio, @NonNull String patenteVehiculo, @NonNull String emailCliente, double precio) {
        this.fechaHora = fechaHora;
        this.tipoServicio = tipoServicio;
        this.patenteVehiculo = patenteVehiculo;
        this.emailCliente = emailCliente;
        this.precio = precio;
    }
}