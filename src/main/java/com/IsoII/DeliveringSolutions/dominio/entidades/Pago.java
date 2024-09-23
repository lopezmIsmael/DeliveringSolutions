package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.sql.Date;
import java.util.UUID;

/**
 * Representa un pago asociado a un pedido, incluyendo el método de pago, identificador de transacción y fecha de transacción.
 */
public class Pago {

    private Pedido pedido;
    private MetodoPago tipo;
    private UUID idTransaccion;
    private Date fechaTransaccion;

    /**
     * Constructor para crear un pago con un pedido asociado, tipo de pago, identificador de transacción y fecha de transacción.
     *
     * @param pedido           El pedido asociado a este pago.
     * @param tipo             El método de pago utilizado.
     * @param idTransaccion    El identificador único de la transacción.
     * @param fechaTransaccion La fecha en que se realizó la transacción.
     */
    public Pago(Pedido pedido, MetodoPago tipo, UUID idTransaccion, Date fechaTransaccion) {
        this.pedido = pedido;
        this.tipo = tipo;
        this.idTransaccion = idTransaccion;
        this.fechaTransaccion = fechaTransaccion;
    }

    /**
     * Obtiene el pedido asociado a este pago.
     *
     * @return El pedido asociado.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Establece el pedido asociado a este pago.
     *
     * @param pedido El pedido a establecer.
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Obtiene el método de pago utilizado.
     *
     * @return El método de pago utilizado.
     */
    public MetodoPago getTipo() {
        return tipo;
    }

    /**
     * Establece el método de pago utilizado.
     *
     * @param tipo El método de pago a establecer.
     */
    public void setTipo(MetodoPago tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el identificador único de la transacción.
     *
     * @return El identificador único de la transacción.
     */
    public UUID getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * Establece el identificador único de la transacción.
     *
     * @param idTransaccion El identificador único de la transacción a establecer.
     */
    public void setIdTransaccion(UUID idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    /**
     * Obtiene la fecha en que se realizó la transacción.
     *
     * @return La fecha de la transacción.
     */
    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * Establece la fecha en que se realizó la transacción.
     *
     * @param fechaTransaccion La fecha de la transacción a establecer.
     */
    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    /**
     * Devuelve una representación en formato de cadena de este pago.
     *
     * @return Una cadena que representa el pago.
     */
    @Override
    public String toString() {
        return "Pago{" +
                "pedido=" + pedido +
                ", tipo=" + tipo +
                ", idTransaccion=" + idTransaccion +
                ", fechaTransaccion=" + fechaTransaccion +
                '}';
    }
}