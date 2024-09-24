package com.IsoII.DeliveringSolutions.dominio.entidades;

/**
 * Enumeración que representa los diferentes métodos de pago disponibles.
 * 
 * @author Jorge López Gómez
 * @author Ismael López Marín
 * @author Pablo Verdúguez Gervaso
 * @author Marco Muñoz García
 * @version 1.0
 */
public enum MetodoPago {
    
    /**
     * Pago a través de PayPal.
     */
    PAYPAL,
    
    /**
     * Pago a través de tarjeta de crédito.
     */
    CREDIT_CARD
}