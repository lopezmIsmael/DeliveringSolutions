package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Representa a un cliente que extiende la clase Usuario y contiene información sobre sus restaurantes favoritos, pedidos, direcciones y datos personales.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

 @Entity
public class Cliente extends Usuario {

    private Collection<Restaurante> favoritos;
    private Collection<Pedido> pedidos;
    private Collection<Direccion> direcciones;

    
    private String nombre;


    private String apellidos;

    @Id ()
    private String dni;

    /**
     * Constructor para crear un cliente con sus datos personales, credenciales de usuario, restaurantes favoritos, pedidos y direcciones.
     *
     * @param idUsuario   El identificador único del usuario.
     * @param pass        La contraseña del usuario.
     * @param rol         El rol del usuario en el sistema.
     * @param favoritos   Colección de restaurantes favoritos del cliente.
     * @param pedidos     Colección de pedidos realizados por el cliente.
     * @param direcciones Colección de direcciones asociadas al cliente.
     * @param nombre      Nombre del cliente.
     * @param apellidos   Apellidos del cliente.
     * @param dni         DNI del cliente.
     */
    public Cliente(String idUsuario, String pass, String rol,
                   Collection<Restaurante> favoritos, Collection<Pedido> pedidos,
                   Collection<Direccion> direcciones, String nombre, String apellidos, String dni) {
        super(idUsuario, pass, rol);
        this.favoritos = (favoritos != null) ? favoritos : new ArrayList<>();
        this.pedidos = (pedidos != null) ? pedidos : new ArrayList<>();
        this.direcciones = (direcciones != null) ? direcciones : new ArrayList<>();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
    }

    /**
     * Obtiene la colección de restaurantes favoritos del cliente.
     *
     * @return Colección de restaurantes favoritos.
     */
    public Collection<Restaurante> getFavoritos() {
        return favoritos;
    }

    /**
     * Establece la colección de restaurantes favoritos del cliente.
     *
     * @param favoritos Colección de restaurantes favoritos a establecer.
     */
    public void setFavoritos(Collection<Restaurante> favoritos) {
        this.favoritos = favoritos;
    }

    /**
     * Obtiene la colección de pedidos realizados por el cliente.
     *
     * @return Colección de pedidos.
     */
    public Collection<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * Establece la colección de pedidos realizados por el cliente.
     *
     * @param pedidos Colección de pedidos a establecer.
     */
    public void setPedidos(Collection<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    /**
     * Obtiene la colección de direcciones asociadas al cliente.
     *
     * @return Colección de direcciones.
     */
    public Collection<Direccion> getDirecciones() {
        return direcciones;
    }

    /**
     * Establece la colección de direcciones asociadas al cliente.
     *
     * @param direcciones Colección de direcciones a establecer.
     */
    public void setDirecciones(Collection<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre El nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del cliente.
     *
     * @return Los apellidos del cliente.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del cliente.
     *
     * @param apellidos Los apellidos a establecer.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el DNI del cliente.
     *
     * @return El DNI del cliente.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del cliente.
     *
     * @param dni El DNI a establecer.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Devuelve una representación en formato de cadena de este cliente.
     *
     * @return Una cadena que representa al cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "idUsuario='" + getIdUsuario() + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", favoritos=" + favoritos +
                ", pedidos=" + pedidos +
                ", direcciones=" + direcciones +
                '}';
    }
}