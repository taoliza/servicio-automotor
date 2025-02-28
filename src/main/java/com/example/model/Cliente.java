package com.example.model;

public class Cliente {

    private String email;
    private String nombre;
    private int contadorServicios;

    public Cliente(String email, String nombre) {
        this.email = email;
        this.nombre = nombre;
        this.contadorServicios = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getContadorServicios() {
        return contadorServicios;
    }

    public void setContadorServicios(int contador) {
        this.contadorServicios = contador;
    }

    public void incrementarContadorServicios(){
        this.contadorServicios++;
    }

    public boolean tieneServicioGratis(){
        return this.contadorServicios == 6; // Si tiene 6 (más de 5), el septimo es gratis
    }

}
