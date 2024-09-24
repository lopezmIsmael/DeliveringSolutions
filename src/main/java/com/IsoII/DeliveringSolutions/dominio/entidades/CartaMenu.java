package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.util.*;

/**
 * Representa una carta de menú en un restaurante, que contiene una colección de ítems de menú.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín 
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */
public class CartaMenu {

    private Restaurante restaurante;
    private Collection<ItemMenu> items;
    private String nombre;

    /**
     * Constructor para crear una CartaMenu con un restaurante, una colección de ítems de menú y un nombre.
     *
     * @param restaurante El restaurante al que pertenece la carta.
     * @param items La colección de ítems de menú que forman parte de la carta.
     * @param nombre El nombre de la carta de menú.
     */
    public CartaMenu(Restaurante restaurante, Collection<ItemMenu> items, String nombre) {
        this.restaurante = restaurante;
        this.items = items;
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
     * Obtiene la colección de ítems de menú de esta carta.
     *
     * @return La colección de ítems de menú.
     */
    public Collection<ItemMenu> getItems() {
        return items;
    }

    /**
     * Establece la colección de ítems de menú para esta carta.
     *
     * @param items La colección de ítems de menú a establecer.
     */
    public void setItems(Collection<ItemMenu> items) {
        this.items = items;
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
                ", items=" + items +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}