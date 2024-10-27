package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

/**
 * Representa un restaurante en el sistema con un identificador, contraseña, tipoUsuario, nombre, dirección y teléfono.
 * 
 * @autor Jorge López Gómez
 * @autor Ismael López Marín
 * @autor Pablo Verdúguez Gervaso
 * @autor Marco Muñoz García
 * @version 1.0
 */
@Entity
@PrimaryKeyJoinColumn(name = "idUsuario") // Indica que la clave primaria es heredada de Usuario
public class Restaurante extends Usuario {
    
    @Column(name = "cif", nullable = false, length = 50)
    private String cif;


    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @OneToOne
    @JoinColumn(name = "Direccion", nullable = true)
    private Direccion direccion;

    public Restaurante() {
    }

    /**
     * Constructor para crear un restaurante con un identificador, contraseña, tipoUsuario, nombre, dirección y teléfono específicos.
     *
     * @param idUsuario El identificador único del usuario.
     * @param pass      La contraseña del usuario.
     * @param tipoUsuario El tipoUsuario del usuario en el sistema.
     * @param cif       El cif del restaurante.
     * @param nombre    El nombre del restaurante.
     */
    public Restaurante(String idUsuario, String pass, String tipoUsuario, String cif, Direccion direccion, String nombre) {
        super(idUsuario, pass, tipoUsuario);
        this.cif = cif;
        this.direccion = direccion;
        this.nombre = nombre;
    }

    // Getters y setters para los atributos específicos de Restaurante
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
