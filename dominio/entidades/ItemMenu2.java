//package dominio.entidades;

/**
 * Representa un ítem del menú con un nombre y un precio.
 */
public class ItemMenu2 {

    private String nombre;
    private double precio;

    /**
     * Constructor para crear un ítem del menú con un nombre y un precio específicos.
     *
     * @param nombre El nombre del ítem del menú.
     * @param precio El precio del ítem del menú.
     */
    public ItemMenu2(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
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
        return "ItemMenu2{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}