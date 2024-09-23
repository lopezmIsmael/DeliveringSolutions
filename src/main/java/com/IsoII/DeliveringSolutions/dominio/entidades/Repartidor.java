package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.util.*;

/**
 * Representa un repartidor que extiende la clase Usuario, incluyendo información sobre los servicios de entrega que realiza, las zonas donde trabaja y sus datos personales.
 */
public class Repartidor extends Usuario {

    private Collection<ServicioEntrega> servicios;
    private Collection<Integer> zonas;
    private String nombre;
    private String apellidos;
    private String nif;
    private int eficiencia;

    /**
     * Constructor para crear un repartidor con sus datos personales, servicios de entrega y zonas de trabajo.
     *
     * @param idUsuario El identificador único del usuario.
     * @param pass      La contraseña del usuario.
     * @param rol       El rol del usuario en el sistema.
     * @param servicios  Colección de servicios de entrega asociados al repartidor.
     * @param zonas      Colección de códigos postales donde el repartidor realiza entregas.
     * @param nombre     Nombre del repartidor.
     * @param apellidos  Apellidos del repartidor.
     * @param nif        NIF del repartidor.
     * @param eficiencia Nivel de eficiencia del repartidor.
     */
    public Repartidor(String idUsuario, String pass, String rol, Collection<ServicioEntrega> servicios, Collection<Integer> zonas, String nombre, String apellidos, String nif, int eficiencia) {
        super(idUsuario, pass, rol);
        this.servicios = (servicios != null) ? servicios : new ArrayList<>();
        this.zonas = (zonas != null) ? zonas : new ArrayList<>();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.eficiencia = eficiencia;
    }

    /**
     * Obtiene la colección de servicios de entrega asociados al repartidor.
     *
     * @return Colección de servicios de entrega.
     */
    public Collection<ServicioEntrega> getServicios() {
        return servicios;
    }

    /**
     * Establece la colección de servicios de entrega asociados al repartidor.
     *
     * @param servicios Colección de servicios de entrega a establecer.
     */
    public void setServicios(Collection<ServicioEntrega> servicios) {
        this.servicios = (servicios != null) ? servicios : new ArrayList<>();
    }

    /**
     * Obtiene la colección de códigos postales donde el repartidor realiza entregas.
     *
     * @return Colección de códigos postales.
     */
    public Collection<Integer> getZonas() {
        return zonas;
    }

    /**
     * Establece la colección de códigos postales donde el repartidor realiza entregas.
     *
     * @param zonas Colección de códigos postales a establecer.
     */
    public void setZonas(Collection<Integer> zonas) {
        this.zonas = (zonas != null) ? zonas : new ArrayList<>();
    }

    /**
     * Obtiene el nombre del repartidor.
     *
     * @return El nombre del repartidor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del repartidor.
     *
     * @param nombre El nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del repartidor.
     *
     * @return Los apellidos del repartidor.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del repartidor.
     *
     * @param apellidos Los apellidos a establecer.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el NIF del repartidor.
     *
     * @return El NIF del repartidor.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del repartidor.
     *
     * @param nif El NIF a establecer.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene el nivel de eficiencia del repartidor.
     *
     * @return El nivel de eficiencia.
     */
    public int getEficiencia() {
        return eficiencia;
    }

    /**
     * Establece el nivel de eficiencia del repartidor.
     *
     * @param eficiencia El nivel de eficiencia a establecer.
     */
    public void setEficiencia(int eficiencia) {
        this.eficiencia = eficiencia;
    }

    /**
     * Devuelve una representación en formato de cadena de este repartidor.
     *
     * @return Una cadena que representa al repartidor.
     */
    @Override
    public String toString() {
        return "Repartidor{" +
                "servicios=" + servicios +
                ", zonas=" + zonas +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nif='" + nif + '\'' +
                ", eficiencia=" + eficiencia +
                '}';
    }
}