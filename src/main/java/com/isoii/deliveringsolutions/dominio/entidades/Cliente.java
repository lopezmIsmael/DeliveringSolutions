package com.isoii.deliveringsolutions.dominio.entidades;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

// Entidad que representa la tabla Cliente en la base de datos
@Entity
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Cliente extends Usuario {

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellido;

    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cliente_restaurante_favoritos", joinColumns = @JoinColumn(name = "idCliente"), inverseJoinColumns = @JoinColumn(name = "idRestaurante"))
    private Set<Restaurante> favoritos = new HashSet<>();

    // Constructores
    public Cliente() {
    }

    public Cliente(String idUsuario, String pass, String tipoUsuario, String nombre, String apellido, String dni) {
        super(idUsuario, pass, tipoUsuario);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    // Getters y Setters
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

    public Set<Restaurante> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Set<Restaurante> favoritos) {
        this.favoritos = favoritos;
    }

    public void addFavorito(Restaurante restaurante) {
        this.favoritos.add(restaurante);
    }

    public void removeFavorito(Restaurante restaurante) {
        this.favoritos.remove(restaurante);
    }

    // toString
    @Override
    public String toString() {
        return "Cliente [idUsuario=" + idUsuario + ", pass=" + pass + ", tipoUsuario=" + tipoUsuario + ", nombre="
                + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
    }
}