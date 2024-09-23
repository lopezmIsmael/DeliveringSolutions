package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.util.*;
import java.util.Date;

/**
 * Representa un servicio de entrega para un pedido, incluyendo información sobre el pedido, dirección, repartidor y fechas de recepción y entrega.
 */
public class ServicioEntrega {

    private Pedido pedido;
    private Direccion direccion;
    private Repartidor repartidor;
    private Date fechaRecepcion;
    private Date fechaEntrega;

    /**
     * Constructor para crear un servicio de entrega con los detalles específicos.
     *
     * @param pedido          El pedido asociado al servicio de entrega.
     * @param direccion       La dirección de entrega.
     * @param repartidor      El repartidor asignado al servicio.
     * @param fechaRecepcion  La fecha en que se recibió el pedido para entrega.
     * @param fechaEntrega    La fecha en que se realizó la entrega al cliente.
     */
    public ServicioEntrega(Pedido pedido, Direccion direccion, Repartidor repartidor, Date fechaRecepcion, Date fechaEntrega) {
        this.pedido = pedido;
        this.direccion = direccion;
        this.repartidor = repartidor;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * Obtiene el pedido asociado al servicio de entrega.
     *
     * @return El pedido asociado.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Establece el pedido asociado al servicio de entrega.
     *
     * @param pedido El pedido a establecer.
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Obtiene la dirección de entrega.
     *
     * @return La dirección de entrega.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de entrega.
     *
     * @param direccion La dirección a establecer.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el repartidor asignado al servicio de entrega.
     *
     * @return El repartidor asignado.
     */
    public Repartidor getRepartidor() {
        return repartidor;
    }

    /**
     * Establece el repartidor asignado al servicio de entrega.
     *
     * @param repartidor El repartidor a establecer.
     */
    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    /**
     * Obtiene la fecha en que se recibió el pedido para entrega.
     *
     * @return La fecha de recepción.
     */
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * Establece la fecha en que se recibió el pedido para entrega.
     *
     * @param fechaRecepcion La fecha de recepción a establecer.
     */
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    /**
     * Obtiene la fecha en que se realizó la entrega al cliente.
     *
     * @return La fecha de entrega.
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * Establece la fecha en que se realizó la entrega al cliente.
     *
     * @param fechaEntrega La fecha de entrega a establecer.
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * Devuelve una representación en formato de cadena de este servicio de entrega.
     *
     * @return Una cadena que representa el servicio de entrega.
     */
    @Override
    public String toString() {
        return "ServicioEntrega{" +
                "pedido=" + pedido +
                ", direccion=" + direccion +
                ", repartidor=" + repartidor +
                ", fechaRecepcion=" + fechaRecepcion +
                ", fechaEntrega=" + fechaEntrega +
                '}';
    }
}