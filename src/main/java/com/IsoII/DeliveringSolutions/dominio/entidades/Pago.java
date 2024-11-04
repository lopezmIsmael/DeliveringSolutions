package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

// Entidad que representa la tabla Pago en la base de datos
@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPago;

    @Column(name = "metodoPago", nullable = false, length = 50)
    private String metodoPago;

    @OneToOne
    @JoinColumn(name = "idPedido", nullable = false)
    private Pedido pedido;

    // Constructores
    public Pago() {
    }

    public Pago(int idPago, String metodoPago, Pedido pedido) {
        this.idPago = idPago;
        this.metodoPago = metodoPago;
        this.pedido = pedido;
    }

    // Getters y setters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    // toString
    @Override
    public String toString() {
        return "Pago [idPago=" + idPago + ", metodoPago=" + metodoPago + ", pedido=" + pedido + "]";
    }
}
