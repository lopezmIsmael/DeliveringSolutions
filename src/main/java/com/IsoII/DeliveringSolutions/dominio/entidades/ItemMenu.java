package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;

/**
 * Representa un usuario genérico en el sistema con un nombre, precio, cartamenu, tipo.
 * 
 * @autor Pablo Verdúguez Gervaso
 * @version 1.0
 */

@Entity
public class ItemMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idItemMenu;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "precio", nullable = false)
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private double precio;

    @ManyToOne
    @JoinColumn(name = "idCarta")
    @JsonIgnore
    private CartaMenu cartamenu;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    public ItemMenu() {
    }

    public ItemMenu(int idItemMenu, String nombre, double precio, CartaMenu cartamenu, String tipo) {
        this.idItemMenu = idItemMenu;
        this.nombre = nombre;
        this.precio = precio;
        this.cartamenu = cartamenu;
        this.tipo = tipo;
    }

    public int getIdItemMenu() {
        return idItemMenu;
    }

    public void setIdItemMenu(int idItemMenu) {
        this.idItemMenu = idItemMenu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public CartaMenu getCartamenu() {
        return cartamenu;
    }

    public void setCartamenu(CartaMenu cartamenu) {
        this.cartamenu = cartamenu;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ItemMenu [cartamenu=" + cartamenu + ", nombre=" + nombre + ", precio=" + precio + ", tipo=" + tipo + "]";
    }
}
