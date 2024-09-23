package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.*;
import com.IsoII.DeliveringSolutions.persistencia.*;

import java.util.List;


public class GestorClientes {

	RestauranteDAO restauranteDAO;

	/**
	 * 
	 * @param codigoPostal
	 */
	public List<Restaurante> buscarRestaurante(int codigoPostal) {
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
	private Direccion altaDirecion(String calle, String numeero, String complemento, int cp, String municipio) {
		// TODO - implement GestorClientes.altaDirecion
		throw new UnsupportedOperationException();
	}

}