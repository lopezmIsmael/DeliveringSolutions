package com.IsoII.DeliveringSolutions.dominio.entidades;

/**
 * Representa un usuario genérico en el sistema con un identificador, contraseña y rol.
 */
public class Usuario {

    private String idUsuario;
    private String pass;
    private String rol;

    /**
     * Constructor para crear un usuario con un identificador, contraseña y rol específicos.
     *
     * @param idUsuario El identificador único del usuario.
     * @param pass      La contraseña del usuario.
     * @param rol       El rol del usuario en el sistema.
     */
    public Usuario(String idUsuario, String pass, String rol) {
        this.idUsuario = idUsuario;
        this.pass = pass;
        this.rol = rol;
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
     * Obtiene el rol del usuario en el sistema.
     *
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario en el sistema.
     *
     * @param rol El rol a establecer.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Devuelve una representación en formato de cadena de este usuario.
     *
     * @return Una cadena que representa al usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", pass='" + pass + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}