//package dominio.entidades;

/**
 * Representa un ítem del menú en un restaurante, incluyendo su tipo, nombre y precio.
 */
public class ItemMenu {

    private TipoItemMenu tipo;
    private String nombre;
    private double precio;

    /**
     * Constructor para crear un ítem del menú con un tipo, nombre y precio específicos.
     *
     * @param tipo El tipo de ítem del menú.
     * @param nombre El nombre del ítem del menú.
     * @param precio El precio del ítem del menú.
     */
    public ItemMenu(TipoItemMenu tipo, String nombre, double precio) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Obtiene el tipo de ítem del menú.
     *
     * @return El tipo de ítem del menú.
     */
    public TipoItemMenu getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de ítem del menú.
     *
     * @param tipo El tipo de ítem del menú a establecer.
     */
    public void setTipo(TipoItemMenu tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre del ítem del menú.
     *
     * @return El nombre del ítem del menú.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del ítem del menú.
     *
     * @param nombre El nombre del ítem del menú a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del ítem del menú.
     *
     * @return El precio del ítem del menú.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del ítem del menú.
     *
     * @param precio El precio del ítem del menú a establecer.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve una representación en formato de cadena de este ítem del menú.
     *
     * @return Una cadena que representa el ítem del menú.
     */
    @Override
    public String toString() {
        return "ItemMenu{" +
                "tipo=" + tipo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}