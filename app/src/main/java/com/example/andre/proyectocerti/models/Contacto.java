package com.example.andre.proyectocerti.models;

/**
 * Created by Adrian on 12/13/2017.
 */

public class Contacto {
    private String nombre;
    private String numero;

    public Contacto(String name, String number){
        this.nombre = name;
        this.numero = number;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}