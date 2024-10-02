package com.IsoII.DeliveringSolutions.dominio.entidades;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class CodigoPostal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "codigo", nullable = false, length = 5)
    private String codigo;

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

    @Override
    public String toString() {
        return "CodigoPostal{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                '}';
    }
    
}
