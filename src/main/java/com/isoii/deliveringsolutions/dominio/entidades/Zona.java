package com.isoii.deliveringsolutions.dominio.entidades;

import jakarta.persistence.*;

import java.io.Serializable;


// Entidad que representa la tabla Zona en la base de datos
@Entity 
public class Zona implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    // Constructores
    public Zona() {
    }

    public Zona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }    

    // toString
    @Override
    public String toString() {
        return "Zona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
