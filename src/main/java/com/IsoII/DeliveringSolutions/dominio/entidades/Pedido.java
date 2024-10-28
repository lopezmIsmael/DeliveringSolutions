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

    @Column(name = "estadoPedido", nullable = false, length = 50)
    private String estadoPedido;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "idRestaurante", nullable = false)
    private Restaurante idRestaurante;

    // @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    // private ServicioEntrega servicioEntrega;

    // @OneToOne(mappedBy = "pedido")
    // private Pago pago;

    public Pedido() {
    }
    
    public Pedido(int idPedido, Date fecha, String estadoPedido, Cliente idCliente, Restaurante idRestaurante, ServicioEntrega servicioEntrega, Pago pago) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estadoPedido = estadoPedido;
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
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
    
}
