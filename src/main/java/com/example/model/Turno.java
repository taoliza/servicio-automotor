package com.example.model;

import java.time.LocalDateTime;

/* Turno u Orden de trabajo */
public class Turno {
    private LocalDateTime fechaHora;
    private TipoServicio tipoServicio;
    private String patenteVehiculo;
    private String emailCliente;
    private double precio;

    public Turno(LocalDateTime fechaHora, String patente, String emailCliente, TipoServicio tipoServicio, double precio) {
        this.fechaHora = fechaHora;
        this.patenteVehiculo = patente;
        this.emailCliente = emailCliente;
        this.tipoServicio = tipoServicio;
        this.precio = precio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getPatenteVehiculo() {
        return patenteVehiculo;
    }

    public void setPatenteVehiculo(String patente) {
        this.patenteVehiculo = patente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCLiente(String email) {
        this.emailCliente = email;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}