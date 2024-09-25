package com.IsoII.DeliveringSolutions.dominio.entidades;

/**
 * Enumeración que representa diferentes códigos postales.
 * Cada constante tiene asociado el valor del código postal como una cadena.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */
public enum CodigoPostal {

    /**
     * Código postal 45600.
     */
    CP_45600("45600"),

    /**
     * Código postal 28000.
     */
    CP_28000("28000");

    private final String codigo;

    /**
     * Constructor que asigna el valor del código postal.
     *
     * @param codigo El valor del código postal.
     */
    CodigoPostal(String codigo) {
        this.codigo = codigo;
    }

}