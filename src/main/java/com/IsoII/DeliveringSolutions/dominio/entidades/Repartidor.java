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

    public Repartidor() {
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
    public Repartidor(String idUsuario, String pass, String tipoUsuario, String nombre, String apellido, String dni, Zona zona) {
        super(idUsuario, pass, tipoUsuario);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.eficiencia = 0;
        this.zona = zona;
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
    
}
