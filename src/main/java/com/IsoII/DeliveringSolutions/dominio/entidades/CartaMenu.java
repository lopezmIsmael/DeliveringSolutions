package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// Entidad que representa la tabla CartaMenu en la base de datos
@Entity
public class CartaMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCarta;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "cif", nullable = false)
    Restaurante restaurante;

    @OneToMany(mappedBy = "cartamenu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ItemMenu> items;

    // Constructores
    public CartaMenu() {
    }

    public CartaMenu(int idCarta, String nombre, Restaurante restaurante) {
        this.idCarta = idCarta;
        this.nombre = nombre;
        this.restaurante = restaurante;
    }

    // Getters y Setters
    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public List<ItemMenu> getItems() {
        return items;
    }

    public void setItems(List<ItemMenu> items) {
        this.items = items;
    }
}