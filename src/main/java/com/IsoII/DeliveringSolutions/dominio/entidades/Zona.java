package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Zona")
public class Zona {
    @Column(name = "nombre", nullable = false, length = 50)
    protected String nombre;

    @Id
    @Column(name = "id", nullable = false)
    protected int id;

    public Zona(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Zona{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
}
