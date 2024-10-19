package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

/**
 * Representa un usuario genérico en el sistema con un id, nombre.
 * 
 * @autor Pablo Verdúguez Gervaso
 * @version 1.0
 */
@Entity
public class CartaMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCarta;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "cif")
    Restaurante restaurante;

    public CartaMenu() {
    }

    public CartaMenu(int idCarta, String nombre, Restaurante restaurante) {
        this.idCarta = idCarta;
        this.nombre = nombre;
        this.restaurante = restaurante;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
    
}
