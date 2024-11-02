package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

/**
 * Representa un pago realizado por un cliente a un restaurante en el sistema.
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

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

    public Pago() {
    }

    public Pago(int idPago, String metodoPago, Pedido pedido) {
        this.idPago = idPago;
        this.metodoPago = metodoPago;
        this.pedido = pedido;
    }

    // Getters y setters para los atributos específicos de Pago

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

    @Override
    public String toString() {
        return "Pago [idPago=" + idPago + ", metodoPago=" + metodoPago + ", pedido=" + pedido + "]";
    }
}
