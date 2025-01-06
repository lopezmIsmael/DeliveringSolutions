package com.isoii.deliveringsolutions.dominio.entidades;

import jakarta.persistence.*;

// Entidad que representa la tabla Restaurante en la base de datos
@Entity
@PrimaryKeyJoinColumn(name = "idUsuario") // Indica que la clave primaria es heredada de Usuario
public class Restaurante extends Usuario {
    
    @Column(name = "cif", nullable = false, length = 50)
    private String cif;


    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    // Constructores
    public Restaurante() {
    }

    public Restaurante(String idUsuario, String pass, String tipoUsuario, String cif, String nombre) {
        super(idUsuario, pass, tipoUsuario);
        this.cif = cif;
        this.nombre = nombre;
    }

    // Getters y setters
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + "Restaurante{" +
                "cif='" + cif + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
