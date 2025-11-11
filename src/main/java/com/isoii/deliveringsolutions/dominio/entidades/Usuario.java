package com.isoii.deliveringsolutions.dominio.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Column;


import java.io.Serializable;

// Entidad que representa la tabla Usuario en la base de datos
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idUsuario", nullable = false, length = 50)
    protected String idUsuario;

    // IMPORTANTE: Se aumenta longitud a 255 para almacenar hash BCrypt
    // Los hashes BCrypt tienen longitud de 60 caracteres, pero se usa 255 para futuras migraciones
    @Column(name = "pass", nullable = false, length = 255)
    protected String pass;

    @Column(name = "tipoUsuario", nullable = false, length = 20)
    protected String tipoUsuario;
    
    // Constructores
    public Usuario() {}

    public Usuario(String idUsuario, String pass, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.pass = pass;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String gettipoUsuario() {
        return tipoUsuario;
    }

    public void settipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // toString - NO EXPONE LA CONTRASEÑA
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", pass='[REDACTED]'" +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }
}
