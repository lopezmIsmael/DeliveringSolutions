package dominio.entidades;

import java.util.*;

public class Cliente extends Usuario {

	Collection<Restaurante> favoritos;
	Collection<Pedido> pedidos;
	Collection<Direccion> direcciones;
	private String nombre;
	private String apellidos;
	private String dni;

}