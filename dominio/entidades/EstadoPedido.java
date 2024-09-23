//package dominio.entidades;

/**
 * Enumeración que representa los posibles estados de un pedido.
 */
public enum EstadoPedido {
    
    /**
     * Estado cuando el pedido ha sido realizado pero aún no ha sido pagado.
     */
    PEDIDO,
    
    /**
     * Estado cuando el pedido ha sido pagado pero aún no ha sido recogido ni entregado.
     */
    PAGADO,
    
    /**
     * Estado cuando el pedido ha sido recogido pero aún no ha sido entregado al cliente.
     */
    RECOGIDO,
    
    /**
     * Estado cuando el pedido ha sido entregado al cliente.
     */
    ENTREGADO
}