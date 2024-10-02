package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Column;

/**
 * Representa un usuario genérico en el sistema con un identificador, contraseña y tipoUsuario.
 * 
 * @author Jorge López Gómez
 * @version 1.0
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @Column(name = "idUsuario", nullable = false, length = 50)
    protected String idUsuario;

    @Column(name = "pass", nullable = false, length = 50)
    protected String pass;

    @Column(name = "tipoUsuario", nullable = false, length = 20)
    protected String tipoUsuario;
    
    public Usuario() {}

    public Usuario(String idUsuario, String pass, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.pass = pass;
        this.tipoUsuario = tipoUsuario;
    }

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
}
