package dominio.controladores;

import persistencia.*;
import dominio.entidades.*;

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
	public void añadirItemMenu(itemMenu item) {
		// TODO - implement GestorPedidos.añadirItemMenu
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param item
	 */
	public void eliminarItemMenu(itemMenu item) {
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