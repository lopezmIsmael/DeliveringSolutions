package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;
import java.util.*;

/**
 * Representa un método de pago que puede ser utilizado por los clientes para realizar sus pedidos.
 * 
 * @version 1.0
 */

@Entity
@Table(name = "MetodoPago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    public MetodoPago() {}

    /**
     * Constructor para crear un método de pago con un nombre específico.
     *
     * @param nombre El nombre del método de pago.
     */
    public MetodoPago(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del método de pago.
     *
     * @return El identificador del método de pago.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del método de pago.
     *
     * @return El nombre del método de pago.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del método de pago.
     *
     * @param nombre El nombre del método de pago.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}