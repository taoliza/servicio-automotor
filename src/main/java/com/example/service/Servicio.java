package com.example.service;

public class Servicio {
    private final TipoServicio tipo;
    private double precio;

    public Servicio(TipoServicio tipo, double precio) {
        this.tipo = tipo;
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TipoServicio getTipo() {
        return tipo;
    }
}
