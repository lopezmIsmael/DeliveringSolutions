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
    private Pedido idpedido;

    @ManyToOne
    @JoinColumn(name = "idRepartidor", nullable = false)
    private Repartidor idrepartidor;

    public ServicioEntrega() {
    }

    public ServicioEntrega(int idServicioEntrega, Date fechaRecepcion, Date fechaEntrega, Pedido idpedido, Repartidor idrepartidor) {
        this.idServicioEntrega = idServicioEntrega;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaEntrega = fechaEntrega;
        this.idpedido = idpedido;
        this.idrepartidor = idrepartidor;
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
        return idpedido;
    }

    public void setPedido(Pedido idpedido) {
        this.idpedido = idpedido;
    }

    public Repartidor getRepartidor() {
        return idrepartidor;
    }

    public void setRepartidor(Repartidor idrepartidor) {
        this.idrepartidor = idrepartidor;
    }

}
