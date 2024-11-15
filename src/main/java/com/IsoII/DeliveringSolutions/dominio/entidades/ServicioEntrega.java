package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

// Entidad que representa la tabla ServicioEntrega en la base de datos
 @Entity
public class ServicioEntrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idServicioEntrega;

    @Column(name = "fechaRecepcion", nullable = false, length = 10)
    private long fechaRecepcion;

    @Column(name = "fechaEntrega", nullable = false, length = 10)
    private long fechaEntrega;

    @OneToOne
    @JoinColumn(name = "idPedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idRepartidor", nullable = false)
    private Repartidor repartidor;

    // Constructores
    public ServicioEntrega() {
    }

    public ServicioEntrega(int idServicioEntrega, long fechaRecepcion, long fechaEntrega, Pedido pedido, Repartidor repartidor) {
        this.idServicioEntrega = idServicioEntrega;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaEntrega = fechaEntrega;
        this.pedido = pedido;
        this.repartidor = repartidor;
    }

    // Getters y setters
    public int getIdServicioEntrega() {
        return idServicioEntrega;
    }
    
    public void setIdServicioEntrega(int idServicioEntrega) {
        this.idServicioEntrega = idServicioEntrega;
    }

    public long getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(long fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public long getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(long fechaEntrega) {
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

    // toString
    @Override
    public String toString() {
        return "ServicioEntrega [idServicioEntrega=" + idServicioEntrega + ", fechaRecepcion=" + fechaRecepcion + ", fechaEntrega=" + fechaEntrega + ", pedido=" + pedido + ", repartidor=" + repartidor + "]";
    }

}
