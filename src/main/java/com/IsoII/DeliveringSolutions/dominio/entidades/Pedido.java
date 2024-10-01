package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.util.*;
import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Representa un pedido realizado por un cliente en un restaurante, incluyendo detalles como el pago, los ítems del menú, el estado del pedido y la fecha.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

 @Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;

    @ManyToOne
    @JoinColumn (name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn (name = "id_transaccion", nullable = false)
    private Pago pago;

    @ManyToOne
    @JoinColumn (name = "repartidor_id", nullable = false)
    private Repartidor repartidor;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<ItemMenu> items;

    @ManyToOne
    @JoinColumn (name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn (name = "idEntrega", nullable = false)
    private ServicioEntrega entrega;

    @Enumerated(EnumType.STRING)
    @Column (name = "estado", nullable = false)
    private EstadoPedido estado;

    @Column (name = "fecha", nullable = false)
    private Date fecha;

    /**
     * Constructor para crear un pedido con los detalles específicos.
     *
     * @param idPedido    El identificador único del pedido.
     * @param cliente     El cliente que realiza el pedido.
     * @param pago        El pago asociado al pedido.
     * @param items       La colección de ítems del menú incluidos en el pedido.
     * @param restaurante El restaurante donde se realiza el pedido.
     * @param entrega     El servicio de entrega asociado al pedido.
     * @param estado      El estado actual del pedido.
     * @param fecha       La fecha en que se realizó el pedido.
     */
    public Pedido(int idPedido, Cliente cliente, Pago pago, Collection<ItemMenu> items, Restaurante restaurante, Repartidor repartidor, ServicioEntrega entrega, EstadoPedido estado, Date fecha) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.pago = pago;
        this.items = (items != null) ? items : new ArrayList<>();
        this.restaurante = restaurante;
        this.repartidor = repartidor;
        this.entrega = entrega;
        this.estado = estado;
        this.fecha = fecha;
    }

    /**
     * Obtiene el identificador único del pedido.
     *
     * @return El identificador del pedido.
     */

    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Establece el identificador único del pedido.
     *
     * @param idPedido El identificador del pedido a establecer.
     */

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }


    /**
     * Obtiene el cliente que realizó el pedido.
     *
     * @return El cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente que realizó el pedido.
     *
     * @param cliente El cliente a establecer.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el pago asociado al pedido.
     *
     * @return El pago.
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * Establece el pago asociado al pedido.
     *
     * @param pago El pago a establecer.
     */
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    /**
     * Obtiene la colección de ítems del menú incluidos en el pedido.
     *
     * @return La colección de ítems del menú.
     */
    public Collection<ItemMenu> getItems() {
        return items;
    }

    /**
     * Establece la colección de ítems del menú para el pedido.
     *
     * @param items La colección de ítems del menú a establecer.
     */
    public void setItems(Collection<ItemMenu> items) {
        this.items = (items != null) ? items : new ArrayList<>();
    }

    /**
     * Obtiene el restaurante donde se realizó el pedido.
     *
     * @return El restaurante.
     */
    public Restaurante getRestaurante() {
        return restaurante;
    }

    /**
     * Obtiene el repartidor asignado al pedido.
     *
     * @return El repartidor asignado.
     */

    public Repartidor getRepartidor() {
        return repartidor;
    }
    
    /**
     * Obtiene el repartidor asignado al pedido.
     *
     * @return El repartidor asignado.
     */

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    /**
     * Establece el restaurante donde se realizó el pedido.
     *
     * @param restaurante El restaurante a establecer.
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Obtiene el servicio de entrega asociado al pedido.
     *
     * @return El servicio de entrega.
     */
    public ServicioEntrega getEntrega() {
        return entrega;
    }

    /**
     * Establece el servicio de entrega asociado al pedido.
     *
     * @param entrega El servicio de entrega a establecer.
     */
    public void setEntrega(ServicioEntrega entrega) {
        this.entrega = entrega;
    }

    /**
     * Obtiene el estado actual del pedido.
     *
     * @return El estado del pedido.
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del pedido.
     *
     * @param estado El estado del pedido a establecer.
     */
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la fecha en que se realizó el pedido.
     *
     * @return La fecha del pedido.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en que se realizó el pedido.
     *
     * @param fecha La fecha del pedido a establecer.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Añade un ítem del menú a la colección de ítems del pedido.
     *
     * @param itemMenu El ítem del menú a añadir.
     */
    public void add(ItemMenu itemMenu) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(itemMenu);
    }

    /**
     * Elimina un ítem del menú de la colección de ítems del pedido.
     *
     * @param itemMenu El ítem del menú a eliminar.
     */
    public void delete(ItemMenu itemMenu) {
        if (items != null) {
            items.remove(itemMenu);
        }
    }

    /**
     * Devuelve una representación en formato de cadena de este pedido.
     *
     * @return Una cadena que representa el pedido.
     */
    @Override
    public String toString() {
        return "Pedido{" +
                "cliente=" + cliente +
                ", pago=" + pago +
                ", items=" + items +
                ", restaurante=" + restaurante +
                ", entrega=" + entrega +
                ", estado=" + estado +
                ", fecha=" + fecha +
                '}';
    }
}