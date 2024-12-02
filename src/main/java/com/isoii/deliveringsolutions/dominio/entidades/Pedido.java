package com.isoii.deliveringsolutions.dominio.entidades;

import jakarta.persistence.*;

// Entidad que representa la tabla Pedido en la base de datos
@Entity
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPedido;

    @Column(name = "fecha", nullable = false, length = 10)
    private long fecha;

    @Column(name = "estadoPedido", nullable = false, length = 50)
    private String estadoPedido;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "idRestaurante", nullable = false)
    private Restaurante idRestaurante;

    // Constructores
    public Pedido() {
    }
    
    public Pedido(int idPedido, long fecha, String estadoPedido, Cliente idCliente, Restaurante idRestaurante) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estadoPedido = estadoPedido;
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
    }

    // Getters y setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public Cliente getCliente() {
        return idCliente;
    }

    public void setCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Restaurante getRestaurante() {
        return idRestaurante;
    }

    public void setRestaurante(Restaurante idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    // toString
    @Override
    public String toString() {
        return "Pedido [estadoPedido=" + estadoPedido + ", fecha=" + fecha + ", idCliente=" + idCliente + ", idPedido="
                + idPedido + ", idRestaurante=" + idRestaurante + "]";
    }
}
