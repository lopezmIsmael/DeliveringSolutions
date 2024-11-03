package com.IsoII.DeliveringSolutions.dominio.entidades;
import jakarta.persistence.*;

@Entity
public class ItemsPedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "itemMenu", nullable = false)
    private ItemMenu itemMenu;

    public ItemsPedidos() {
    }

    public ItemsPedidos(int id, Pedido pedido, ItemMenu itemMenu) {
        this.id = id;
        this.pedido = pedido;
        this.itemMenu = itemMenu;
    }

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

    @Override
    public String toString() {
        return "ItemsPedidos{" +
                "id=" + id +
                ", pedido=" + pedido +
                ", itemMenu=" + itemMenu +
                '}';
    }
}
