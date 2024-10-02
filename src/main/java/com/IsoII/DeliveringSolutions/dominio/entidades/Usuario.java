package com.IsoII.DeliveringSolutions.dominio.entidades;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Column;
import jakarta.persistence.InheritanceType;


/**
 * Representa un usuario genérico en el sistema con un identificador, contraseña y tipoUsuario.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    protected String idUsuario;

    @Column (name = "pass", nullable = false)
    protected String pass;

    @Column (name = "tipoUsuario", nullable = false)
    protected String tipoUsuario;
    

    public Usuario() {}

    /**
     * Constructor para crear un usuario con un identificador, contraseña y tipoUsuario específicos.
     *
     * @param idUsuario El identificador único del usuario.
     * @param pass      La contraseña del usuario.
     * @param tipoUsuario       El tipoUsuario del usuario en el sistema.
     */
    public Usuario(String idUsuario, String pass, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.pass = pass;
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return El identificador del usuario.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param idUsuario El identificador a establecer.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPass() {
        return pass;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param pass La contraseña a establecer.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Obtiene el tipoUsuario del usuario en el sistema.
     *
     * @return El tipoUsuario del usuario.
     */
    public String gettipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Establece el tipoUsuario del usuario en el sistema.
     *
     * @param tipoUsuario El tipoUsuario a establecer.
     */
    public void settipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}