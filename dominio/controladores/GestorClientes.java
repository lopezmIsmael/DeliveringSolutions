package dominio.controladores;

import persistencia.*;
import dominio.entidades.*;

public class GestorClientes {

	RestauranteDAO restauranteDAO;

	/**
	 * 
	 * @param zona
	 */
	public List<Restaurante> buscarRestaurante(CodigoPostal zona) {
		// TODO - implement GestorClientes.buscarRestaurante
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cadenaBusqueda
	 */
	public List<Restaurante> buscarRestaurante(String cadenaBusqueda) {
		// TODO - implement GestorClientes.buscarRestaurante
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cliente
	 * @param r
	 */
	public void favorito(Cliente cliente, Restaurante r) {
		// TODO - implement GestorClientes.favorito
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nombre
	 * @param apellido
	 * @param d
	 */
	public Cliente registrarCliente(String nombre, String apellido, Direccion d) {
		// TODO - implement GestorClientes.registrarCliente
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param calle
	 * @param numeero
	 * @param complemento
	 * @param cp
	 * @param municipio
	 */
	private Direccion altaDirecion(String calle, String numeero, String complemento, CodigoPostal cp, String municipio) {
		// TODO - implement GestorClientes.altaDirecion
		throw new UnsupportedOperationException();
	}

}