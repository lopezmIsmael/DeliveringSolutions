package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;
import java.util.*;

/**
 * Representa un restaurante que extiende la clase Usuario, incluyendo información sobre pedidos, cartas de menú, dirección y datos del restaurante.
 * 
 * @author Jorge López Gómez
 * @version 1.0
 */
@Entity
@Table(name = "Restaurante")
public class Restaurante {

    @Id
    @Column(name = "cif", nullable = false, unique = true, length = 50)
    private String cif;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "direccion_id", referencedColumnName = "id") // Si la tabla Dirección tiene un campo id
    private Direccion direccion;

    @OneToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "restaurante") // Relación uno a muchos con Pedido
    private Collection<Pedido> pedidos;

    @OneToMany(mappedBy = "restaurante") // Relación uno a muchos con CartaMenu
    private Collection<CartaMenu> cartasMenu;

    /**
     * Constructor para crear un restaurante con sus datos específicos.
     *
     * @param cif        CIF del restaurante.
     * @param nombre     Nombre del restaurante.
     * @param direccion  Dirección del restaurante.
     * @param usuario    Usuario asociado al restaurante (relación uno a uno).
     * @param pedidos    Colección de pedidos asociados al restaurante.
     * @param cartasMenu Colección de cartas de menú del restaurante.
     */
    public Restaurante(String cif, String nombre, Direccion direccion, Usuario usuario,
                       Collection<Pedido> pedidos, Collection<CartaMenu> cartasMenu) {
        this.cif = cif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.usuario = usuario;
        this.pedidos = (pedidos != null) ? pedidos : new ArrayList<>();
        this.cartasMenu = (cartasMenu != null) ? cartasMenu : new ArrayList<>();
    }

    public Restaurante() {}

    // Getters y setters...

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Collection<Pedido> pedidos) {
        this.pedidos = (pedidos != null) ? pedidos : new ArrayList<>();
    }

    public Collection<CartaMenu> getCartasMenu() {
        return cartasMenu;
    }

    public void setCartasMenu(Collection<CartaMenu> cartasMenu) {
        this.cartasMenu = (cartasMenu != null) ? cartasMenu : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "cif='" + cif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion=" + direccion +
                ", usuario=" + usuario +
                ", pedidos=" + pedidos +
                ", cartasMenu=" + cartasMenu +
                '}';
    }
}
