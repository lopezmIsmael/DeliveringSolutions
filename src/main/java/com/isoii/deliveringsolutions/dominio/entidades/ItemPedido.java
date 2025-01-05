package com.isoii.deliveringsolutions.dominio.entidades;

import jakarta.persistence.*;

// Entidad que representa la tabla ItemPedido en la base de datos
@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "itemMenu", nullable = false)
    private ItemMenu itemMenu;

    // Constructores
    public ItemPedido() {
    }

    public ItemPedido(int id, Pedido pedido, ItemMenu itemMenu) {
        this.id = id;
        this.pedido = pedido;
        this.itemMenu = itemMenu;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ItemMenu getItemMenu() {
        return itemMenu;
    }

    public void setItemMenu(ItemMenu itemMenu) {
        this.itemMenu = itemMenu;
    }

    // toString
    @Override
    public String toString() {
        return "ItemsPedidos{" +
                "id=" + id +
                ", pedido=" + pedido +
                ", itemMenu=" + itemMenu +
                '}';
    }
}
