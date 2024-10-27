package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

/**
 * Representa un cliente en el sistema con un identificador, contraseña, tipoUsuario, nombre, apellido y dni.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

@Entity
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Cliente extends Usuario {

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellido;

    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @ManyToOne
    @JoinColumn(name = "Direccion", nullable = true)
    private Direccion direccion;

    public Cliente() {
    }

    /**
     * Constructor para crear un cliente con un identificador, contraseña, tipoUsuario, nombre, apellido y email específicos.
     *
     * @param idUsuario El identificador único del usuario.
     * @param pass      La contraseña del usuario.
     * @param tipoUsuario El tipoUsuario del usuario en el sistema.
     * @param nombre    El nombre del cliente.
     * @param apellido  El apellido del cliente.
     * @param dni     El dni del cliente.
     */
    public Cliente(String idUsuario, String pass, String tipoUsuario, String nombre, String apellido, String dni, Direccion direccion) {
        super(idUsuario, pass, tipoUsuario);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
    }

    // Getters y setters para los atributos específicos de Cliente

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

    public String getdni() {
        return dni;
    }

    public void setdni(String dni) {
        this.dni = dni;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Cliente [idUsuario=" + idUsuario + ", pass=" + pass + ", tipoUsuario=" + tipoUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", direccion=" + direccion.toString() + "]";
    }
}