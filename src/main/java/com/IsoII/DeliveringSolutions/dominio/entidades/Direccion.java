package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;

@Entity
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calle", nullable = false, length = 50)
    private String calle;

    @Column(name = "numero", nullable = false, length = 5)
    private String numero;

    @Column(name = "complemento", length = 50)
    private String complemento;

    @Column(name = "municipio", nullable = false, length = 50)
    private String municipio;

    @OneToOne
    @JoinColumn(name = "idCodigoPostal", referencedColumnName = "id", nullable = false, unique = true)
    private CodigoPostal codigoPostal;

    public Direccion() {
    }

    public Direccion(String calle, String numero, String complemento, String municipio, CodigoPostal codigoPostal) {
        this.calle = calle;
        this.numero = numero;
        this.complemento = complemento;
        this.municipio = municipio;
        this.codigoPostal = codigoPostal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", municipio='" + municipio + '\'' +
                ", codigoPostal=" + codigoPostal +
                '}';
    }
}
