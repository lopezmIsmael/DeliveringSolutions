//package dominio.entidades;

import java.util.*;

/**
 * Representa un restaurante que extiende la clase Usuario, incluyendo información sobre pedidos, cartas de menú, dirección y datos del restaurante.
 */
public class Restaurante extends Usuario {

    private Collection<Pedido> pedidos;
    private Collection<CartaMenu> cartasMenu;
    private Direccion direccion;
    private String nombre;
    private String cif;

    /**
     * Constructor para crear un restaurante con sus datos específicos.
     *
     * @param pedidos     Colección de pedidos asociados al restaurante.
     * @param cartasMenu  Colección de cartas de menú del restaurante.
     * @param direccion   Dirección del restaurante.
     * @param nombre      Nombre del restaurante.
     * @param cif         CIF del restaurante.
     */
    public Restaurante(Collection<Pedido> pedidos, Collection<CartaMenu> cartasMenu, Direccion direccion, String nombre, String cif) {
        this.pedidos = (pedidos != null) ? pedidos : new ArrayList<>();
        this.cartasMenu = (cartasMenu != null) ? cartasMenu : new ArrayList<>();
        this.direccion = direccion;
        this.nombre = nombre;
        this.cif = cif;
    }

    /**
     * Obtiene la colección de pedidos del restaurante.
     *
     * @return Colección de pedidos.
     */
    public Collection<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * Establece la colección de pedidos del restaurante.
     *
     * @param pedidos Colección de pedidos a establecer.
     */
    public void setPedidos(Collection<Pedido> pedidos) {
        this.pedidos = (pedidos != null) ? pedidos : new ArrayList<>();
    }

    /**
     * Obtiene la colección de cartas de menú del restaurante.
     *
     * @return Colección de cartas de menú.
     */
    public Collection<CartaMenu> getCartasMenu() {
        return cartasMenu;
    }

    /**
     * Establece la colección de cartas de menú del restaurante.
     *
     * @param cartasMenu Colección de cartas de menú a establecer.
     */
    public void setCartasMenu(Collection<CartaMenu> cartasMenu) {
        this.cartasMenu = (cartasMenu != null) ? cartasMenu : new ArrayList<>();
    }

    /**
     * Obtiene la dirección del restaurante.
     *
     * @return Dirección del restaurante.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del restaurante.
     *
     * @param direccion Dirección a establecer.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el nombre del restaurante.
     *
     * @return Nombre del restaurante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del restaurante.
     *
     * @param nombre Nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el CIF del restaurante.
     *
     * @return CIF del restaurante.
     */
    public String getCif() {
        return cif;
    }

    /**
     * Establece el CIF del restaurante.
     *
     * @param cif CIF a establecer.
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Lista los ítems del menú del restaurante sin requerir un identificador de restaurante.
     *
     * @return Lista de ítems del menú.
     */
    public List<ItemMenu> listarMenu() {
        List<ItemMenu> listaItems = new ArrayList<>();
        if (cartasMenu != null) {
            for (CartaMenu carta : cartasMenu) {
                if (carta.getItems() != null) {
                    listaItems.addAll(carta.getItems());
                }
            }
        }
        return listaItems;
    }

    /**
     * Devuelve una representación en formato de cadena de este restaurante.
     *
     * @return Una cadena que representa al restaurante.
     */
    @Override
    public String toString() {
        return "Restaurante{" +
                "pedidos=" + pedidos +
                ", cartasMenu=" + cartasMenu +
                ", direccion=" + direccion +
                ", nombre='" + nombre + '\'' +
                ", cif='" + cif + '\'' +
                '}';
    }
}