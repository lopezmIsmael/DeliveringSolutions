package com.IsoII.DeliveringSolutions.dominio.entidades;

import java.sql.Date;

import jakarta.persistence.*;


/**
* Representa un pedido realizado por un cliente a un restaurante en el sistema. 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

@Entity
public class Pedido {
    
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPedido;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idRestaurante", nullable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido")
    private ServicioEntrega servicioEntrega;

    @OneToMany(mappedBy = "pedido")
    private Pago pago;

    public Pedido() {
    }
    
    public Pedido(Date fecha, EstadoPedido estado, Cliente cliente, Restaurante restaurante, ServicioEntrega servicioEntrega, Pago pago) {
        this.fecha = fecha;
        this.estado = estado;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.servicioEntrega = servicioEntrega;
        this.pago = pago;
    }

    // Getters y setters para los atributos específicos de Pedido

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public ServicioEntrega getServicioEntrega() {
        return servicioEntrega;
    }

    public void setServicioEntrega(ServicioEntrega servicioEntrega) {
        this.servicioEntrega = servicioEntrega;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", fecha=" + fecha + ", estado=" + estado + ", cliente=" + cliente + ", restaurante=" + restaurante + ", servicioEntrega=" + servicioEntrega + ", pago=" + pago + "]";
    }
    
}
