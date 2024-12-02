package com.isoii.deliveringsolutions.dominio.entidades;

import jakarta.persistence.*;

// Entidad que representa la tabla ZonaCodigoPostal en la base de datos
@Entity
public class ZonaCodigoPostal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "codigoPostal", nullable = false)
    private CodigoPostal codigoPostal;

    @ManyToOne
    @JoinColumn(name = "zona", nullable = false)
    private Zona zona;

    // Constructores
    public ZonaCodigoPostal() {
    }

    public ZonaCodigoPostal(int id, CodigoPostal codigoPostal, Zona zona) {
        this.id = id;
        this.codigoPostal = codigoPostal;
        this.zona = zona;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    // toString
    @Override
    public String toString() {
        return "ZonaCodigoPostal{" +
                "id=" + id +
                ", codigoPostal=" + codigoPostal +
                ", zona=" + zona +
                '}';
    }
}
