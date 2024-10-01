package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa un ítem del menú en un restaurante, incluyendo su tipo, nombre y precio.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

 @Entity
public class ItemMenu {

    @Id
    private int idMenu;

    @ManyToOne
    @JoinColumn (name = "menu_id", nullable = false)
    private TipoItemMenu tipo;

    @ManyToOne
    @JoinColumn (name = "menu_id", nullable = false)
    private CartaMenu menu_id;

    @Column (name = "nombre", nullable = false)
    private String nombre;

    @Column (name = "precio", nullable = false)
    private double precio;
    

    /**
     * Constructor para crear un ítem del menú con un tipo, nombre y precio específicos.
     *
     * @param tipo El tipo de ítem del menú.
     * @param nombre El nombre del ítem del menú.
     * @param precio El precio del ítem del menú.
     */
    public ItemMenu(int idMenu, TipoItemMenu tipo, String nombre, double precio, CartaMenu menu_id) {
        this.idMenu = idMenu;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.menu_id = menu_id;
    }

    /**
     * Obtiene el tipo de ítem del menú.
     *
     * @return El tipo de ítem del menú.
     */
    public TipoItemMenu getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de ítem del menú.
     *
     * @param tipo El tipo de ítem del menú a establecer.
     */
    public void setTipo(TipoItemMenu tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre del ítem del menú.
     *
     * @return El nombre del ítem del menú.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del ítem del menú.
     *
     * @param nombre El nombre del ítem del menú a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del ítem del menú.
     *
     * @return El precio del ítem del menú.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del ítem del menú.
     *
     * @param precio El precio del ítem del menú a establecer.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el identificador
     * @return El identificador del ítem del menú.
     */

    public int getIdMenu() {
        return idMenu;
    }

    /**
     * Establece el identificador del ítem del menú.
     * @param idMenu El identificador del ítem del menú a establecer.
     */

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * Obtiene el menú al que pertenece el ítem.
     * @return El menú al que pertenece el ítem.
     */

    public CartaMenu getMenu_id() {
        return menu_id;
    }

    /**
     * Establece el menú al que pertenece el ítem.
     * @param menu_id El menú al que pertenece el ítem.
     */

    public void setMenu_id(CartaMenu menu_id) {
        this.menu_id = menu_id;
    }

    /**
     * Devuelve una representación en formato de cadena de este ítem del menú.
     *
     * @return Una cadena que representa el ítem del menú.
     */
    @Override
    public String toString() {
        return "ItemMenu{" +
                "tipo=" + tipo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}