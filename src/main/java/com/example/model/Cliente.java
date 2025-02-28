package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int contadorServicios;

    public Cliente(@NonNull String email, @NonNull String nombre) {
        this.email = email;
        this.nombre = nombre;
        this.contadorServicios = 0;
    }

    public void incrementarContadorServicios(){
        this.contadorServicios++;
    }

    public boolean tieneServicioGratis(){
        return this.contadorServicios == 6; // Si tiene 6 (más de 5), el septimo es gratis
    }

}
