package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.*;
import com.IsoII.DeliveringSolutions.persistencia.*;


import java.util.List;

public class GestorPedidos {

	PedidoDAO pedidoDAO;
	ServicioEntregaDAO servicioEntregaDAO;
	Pedido pedidoEnMarcha;

	/**
	 * 
	 * @param c
	 * @param r
	 * @param items
	 */
	public void realizarPedido(Cliente c, Restaurante r, List<ItemMenu> items) {
		// TODO - implement GestorPedidos.realizarPedido
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	private boolean realizarPago(Pedido p) {
		// TODO - implement GestorPedidos.realizarPago
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param d
	 */
	private ServicioEntrega crearServicioEntrega(Pedido p, Direccion d) {
		// TODO - implement GestorPedidos.crearServicioEntrega
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param item
	 */
	public void añadirItemMenu(ItemMenu item) {
		// TODO - implement GestorPedidos.a�adirItemMenu
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param item
	 */
	public void eliminarItemMenu(ItemMenu item) {
		// TODO - implement GestorPedidos.eliminarItemMenu
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param resaturante
	 */
	public void comenzarPedido(Restaurante resaturante) {
		// TODO - implement GestorPedidos.comenzarPedido
		throw new UnsupportedOperationException();
	}

}