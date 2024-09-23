package com.IsoII.DeliveringSolutions.dominio.entidades;

/**
 * Representa una dirección física, incluyendo detalles como la calle, número, complemento, código postal y municipio.
 */
public class Direccion {

    private int codigoPostal;
    private String calle;
    private String numero;
    private String complemento;
    private int codigoPostalNum;
    private String municipio;

    /**
     * Constructor para crear una dirección con los detalles específicos.
     *
     * @param codigoPostal El objeto que representa el código postal.
     * @param calle El nombre de la calle.
     * @param numero El número de la calle.
     * @param complemento Información adicional sobre la dirección (ej. piso, departamento).
     * @param codigoPostalNum El código postal en formato numérico.
     * @param municipio El nombre del municipio.
     */
    public Direccion(int codigoPostal, String calle, String numero, String complemento, int codigoPostalNum, String municipio) {
        this.codigoPostal = codigoPostal;
        this.calle = calle;
        this.numero = numero;
        this.complemento = complemento;
        this.codigoPostalNum = codigoPostalNum;
        this.municipio = municipio;
    }

    /**
     * Obtiene el objeto que representa el código postal.
     *
     * @return El objeto CodigoPostal.
     */
    public int getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el objeto que representa el código postal.
     *
     * @param codigoPostal El objeto CodigoPostal a establecer.
     */
    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
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
     * Obtiene el código postal en formato numérico.
     *
     * @return El código postal en formato numérico.
     */
    public int getCodigoPostalNum() {
        return codigoPostalNum;
    }

    /**
     * Establece el código postal en formato numérico.
     *
     * @param codigoPostalNum El código postal en formato numérico a establecer.
     */
    public void setCodigoPostalNum(int codigoPostalNum) {
        this.codigoPostalNum = codigoPostalNum;
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
                "codigoPostal=" + codigoPostal +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", codigoPostalNum=" + codigoPostalNum +
                ", municipio='" + municipio + '\'' +
                '}';
    }
}