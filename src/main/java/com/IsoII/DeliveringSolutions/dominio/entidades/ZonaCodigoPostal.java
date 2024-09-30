package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "ZonaCodigoPostal")
public class ZonaCodigoPostal extends Zona {

    @OneToOne
    @Column(name = "codigo_postal_id", nullable = false)
    private CodigoPostal codigoPostalId;
    
    @OneToOne
    @Column(name = "zona_id", nullable = false)
    private Zona zonaId;

    public ZonaCodigoPostal(String nombre, int id, CodigoPostal codigoPostalId, Zona zonaId) {
        super(nombre, id);
        this.codigoPostalId = codigoPostalId;
        this.zonaId = zonaId;
    }

    public CodigoPostal getCodigoPostalId() {
        return codigoPostalId;
    }

    public Zona getZonaId() {
        return zonaId;
    }

    public void setCodigoPostalId(CodigoPostal codigoPostalId) {
        this.codigoPostalId = codigoPostalId;
    }

    public void setZonaId(Zona zonaId) {
        this.zonaId = zonaId;
    }

    @Override
    public String toString() {
        return "ZonaCodigoPostal{" +
                "codigoPostalId=" + codigoPostalId +
                ", zonaId=" + zonaId +
                '}';
    }
}
