package com.isoii.deliveringsolutions.dominio.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;

// Entidad que representa la tabla CodigoPostal en la base de datos
@Entity
public class CodigoPostal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "codigo", nullable = false, length = 5)
    private String codigo;

    // Constructores
    public CodigoPostal() {
    }

    public CodigoPostal(int id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }    

    // toString
    @Override
    public String toString() {
        return "CodigoPostal{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                '}';
    }
    
}
