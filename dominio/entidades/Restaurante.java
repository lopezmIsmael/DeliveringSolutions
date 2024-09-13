//package dominio.entidades;

import java.util.*;

public class Restaurante extends Usuario {

	Collection<Pedido> pedidos;
	Collection<CartaMenu> cartasMenu;
	Direccion direccion;
	private String nombre;
	private String cif;

	/**
	 * 
	 * @param idRestaurante
	 */
	public List<ItemMenu> listarMenu(String idRestaurante) {
		// TODO - implement Restaurante.listarMenu
		throw new UnsupportedOperationException();
	}

}