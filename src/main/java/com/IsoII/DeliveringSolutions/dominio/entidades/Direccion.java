package com.IsoII.DeliveringSolutions.dominio.entidades;

/**
 * Representa una dirección física, incluyendo detalles como la calle, número, complemento, código postal y municipio.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */
public class Direccion {

    private String calle;
    private String numero;
    private String complemento;
    private String municipio;

    /**
     * Constructor para crear una dirección con los detalles específicos.
     *
     * @param calle        El nombre de la calle.
     * @param numero       El número de la calle.
     * @param complemento  Información adicional sobre la dirección (ej. piso, departamento).
     * @param municipio    El nombre del municipio.
     */
    public Direccion(String calle, String numero, String complemento, String municipio) {
        this.calle = calle;
        this.numero = numero;
        this.complemento = complemento;
        this.municipio = municipio;
    }

    /**
     * Obtiene el nombre de la calle.
     *
     * @return El nombre de la calle.
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Establece el nombre de la calle.
     *
     * @param calle El nombre de la calle a establecer.
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtiene el número de la calle.
     *
     * @return El número de la calle.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la calle.
     *
     * @param numero El número de la calle a establecer.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el complemento de la dirección (ej. piso, departamento).
     *
     * @return El complemento de la dirección.
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Establece el complemento de la dirección (ej. piso, departamento).
     *
     * @param complemento El complemento de la dirección a establecer.
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * Obtiene el nombre del municipio.
     *
     * @return El nombre del municipio.
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Establece el nombre del municipio.
     *
     * @param municipio El nombre del municipio a establecer.
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Devuelve una representación en formato de cadena de esta dirección.
     *
     * @return Una cadena que representa la dirección.
     */
    @Override
    public String toString() {
        return "Direccion{" +
                "calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", municipio='" + municipio + '\'' +
                '}';
    }
}