package com.isoii.deliveringsolutions.dominio.entidades;
import java.util.List;

public class ConfirmacionPedidoDTO {
    private Pedido pedido;
    private List<ItemMenu> items;
    private Pago pago;
    private Restaurante restaurante;
    private Cliente cliente;
    private double total;
    private Direccion direccionRecogida;
    private Direccion direccionEntrega;
    
    // Getters y Setters
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<ItemMenu> getItems() {
        return items;
    }
    public void setItems(List<ItemMenu> items) {
        this.items = items;
    }

    public Pago getPago() {
        return pago;
    }
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public Direccion getDireccionRecogida() {
        return direccionRecogida;
    }
    public void setDireccionRecogida(Direccion direccionRecogida) {
        this.direccionRecogida = direccionRecogida;
    }

    public Direccion getDireccionEntrega() {
        return direccionEntrega;
    }
    public void setDireccionEntrega(Direccion direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }
}
