package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Representa un servicio de entrega para un pedido, incluyendo información
 * sobre el pedido, dirección, repartidor y fechas de recepción y entrega.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */
@Entity
@Table(name = "ServicioEntrega")
public class ServicioEntrega {

    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "pedido_id")
    private Pedido pedido;

    @Column(name = "fecha_Recepcion", nullable = false)
    private Date fechaRecepcion;

    @Column(name = "fecha_Entrega", nullable = false)
    private Date fechaEntrega;

    public ServicioEntrega() {}

    /**
     * Constructor para crear un servicio de entrega con los detalles específicos.
     *
     * @param pedido         El pedido asociado al servicio de entrega.
     * @param direccion      La dirección de entrega.
     * @param repartidor     El repartidor asignado al servicio.
     * @param fechaRecepcion La fecha en que se recibió el pedido para entrega.
     * @param fechaEntrega   La fecha en que se realizó la entrega al cliente.
     */
    public ServicioEntrega(int id, Pedido pedido, Date fechaRecepcion, Date fechaEntrega) {
        this.id = id;
        this.pedido = pedido;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaEntrega = fechaEntrega;
    }



    /**
     * Obtiene el identificador del servicio de entrega.
     *
     * @return El identificador del servicio.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del servicio de entrega.
     *
     * @param id El identificador a establecer.
     */
    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                "pedido=" + pedido +
                ", fechaRecepcion=" + fechaRecepcion +
                ", fechaEntrega=" + fechaEntrega +
                '}';
    }
}