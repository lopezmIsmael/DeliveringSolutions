package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

/**
 * Representa una carta de menú en un restaurante, que contiene una colección de ítems de menú.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín 
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */
@Entity
@Table(name = "CartaMenu")
public class CartaMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "restaurante_id") // Cambia @Column a @JoinColumn
    private Restaurante restaurante;

    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Constructor para crear una CartaMenu con un restaurante, una colección de ítems de menú y un nombre.
     *
     * @param restaurante El restaurante al que pertenece la carta.
     * @param items La colección de ítems de menú que forman parte de la carta.
     * @param nombre El nombre de la carta de menú.
     */
    public CartaMenu(Restaurante restaurante, int id, String nombre) {
        this.restaurante = restaurante;
        this.id = id;
        this.nombre = nombre;
    }
    /**
     * Obtiene el restaurante asociado a esta carta de menú.
     *
     * @return El restaurante asociado.
     */
    public Restaurante getRestaurante() {
        return restaurante;
    }

    /**
     * Establece el restaurante para esta carta de menú.
     *
     * @param restaurante El restaurante a establecer.
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Obtiene id de la carta de menú.
     * 
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Establece id de la carta de menú.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de esta carta de menú.
     *
     * @return El nombre de la carta de menú.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre para esta carta de menú.
     *
     * @param nombre El nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve una representación en formato de cadena de la carta de menú.
     *
     * @return Una cadena que representa la carta de menú.
     */
    @Override
    public String toString() {
        return "CartaMenu{" +
                "restaurante=" + restaurante +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}