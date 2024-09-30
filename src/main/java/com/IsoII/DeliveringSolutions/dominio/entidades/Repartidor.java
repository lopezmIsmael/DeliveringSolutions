package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;
import java.util.*;

/**
 * Representa un repartidor que extiende la clase Usuario, incluyendo información sobre los servicios de entrega que realiza, las zonas donde trabaja y sus datos personales.
 * 
 * @author Jorge López Gómez
 * @version 1.0
 */
@Entity
@Table(name = "Repartidor")
public class Repartidor {

    @Id
    @Column(name = "dni", nullable = false, unique = true, length = 9)
    private String nif;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellidos;

    @Column(name = "eficiencia")
    private int eficiencia;

    @ManyToOne
    @JoinColumn(name = "zona_id", referencedColumnName = "id")
    private Zona zona; // Zona sería otra entidad mapeada.

    @OneToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", nullable = false)
    private Usuario usuario;

    /**
     * Constructor para crear un repartidor con sus datos personales.
     *
     * @param nif        NIF del repartidor.
     * @param nombre     Nombre del repartidor.
     * @param apellidos  Apellidos del repartidor.
     * @param eficiencia Nivel de eficiencia del repartidor.
     * @param zona       Zona de entrega asignada al repartidor.
     * @param usuario    Usuario asociado al repartidor (relación uno a uno).
     */
    public Repartidor(String nif, String nombre, String apellidos, int eficiencia, Zona zona, Usuario usuario) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.eficiencia = eficiencia;
        this.zona = zona;
        this.usuario = usuario;
    }

    public Repartidor() {}

    // Getters y setters...

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", eficiencia=" + eficiencia +
                ", zona=" + zona +
                ", usuario=" + usuario +
                '}';
    }
}
