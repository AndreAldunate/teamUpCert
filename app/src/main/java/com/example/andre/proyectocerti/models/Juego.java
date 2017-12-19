package com.example.andre.proyectocerti.models;

/**
 * Created by Adrian on 12/18/2017.
 */

public class Juego {
    private String nombre;
    private String photoUrl;
    private String description;

    public Juego(String nombre, String photoUrl, String description) {
        this.nombre = nombre;
        this.photoUrl = photoUrl;
        this.description = description;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
