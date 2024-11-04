package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

// Entidad que representa la tabla Repartidor en la base de datos
@Entity
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Repartidor extends Usuario {

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellido;

    @Column(name = "dni", nullable = false, unique = true, length = 9)
    private String dni;

    @Column(name = "eficiencia")
    private int eficiencia;

    @ManyToOne
    @JoinColumn(name = "zona", referencedColumnName = "id")
    private Zona zona;

    @OneToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", nullable = false)
    private Usuario usuario;

    // Constructores
    public Repartidor() {
    }

    public Repartidor(String idUsuario, String pass, String tipoUsuario, String nombre, String apellido, String dni, Zona zona) {
        super(idUsuario, pass, tipoUsuario);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.eficiencia = 0;
        this.zona = zona;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(int eficiencia) {
        this.eficiencia = eficiencia;
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
        return super.toString() + "Repartidor{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", eficiencia=" + eficiencia +
                ", zona=" + zona +
                '}';
    }
}
