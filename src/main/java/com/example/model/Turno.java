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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoServicio tipoServicio;

    @NonNull
    @Column(nullable = false)
    private String patenteVehiculo;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private double precio;


    public Turno(@NonNull LocalDateTime fechaHora, @NonNull TipoServicio tipoServicio, @NonNull String patenteVehiculo, @NonNull Cliente cliente, double precio) {
        this.fechaHora = fechaHora;
        this.tipoServicio = tipoServicio;
        this.patenteVehiculo = patenteVehiculo;
        this.cliente = cliente;
        this.precio = precio;
    }
}