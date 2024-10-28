package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Representa un pago realizado por un cliente a un restaurante en el sistema.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

 @Entity
public class ServicioEntrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idServicioEntrega;

    @Column(name = "fechaRecepcion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRecepcion;

    @Column(name = "fechaEntrega", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;

    @OneToOne
    @JoinColumn(name = "idPedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idRepartidor", nullable = false)
    private Repartidor repartidor;

    public ServicioEntrega() {
    }

    public ServicioEntrega(int idServicioEntrega, Date fechaRecepcion, Date fechaEntrega, Pedido pedido, Repartidor repartidor) {
        this.idServicioEntrega = idServicioEntrega;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaEntrega = fechaEntrega;
        this.pedido = pedido;
        this.repartidor = repartidor;
    }

    // Getters y setters para los atributos específicos de ServicioEntrega

    public int getIdServicioEntrega() {
        return idServicioEntrega;
    }
    
    public void setIdServicioEntrega(int idServicioEntrega) {
        this.idServicioEntrega = idServicioEntrega;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

}
