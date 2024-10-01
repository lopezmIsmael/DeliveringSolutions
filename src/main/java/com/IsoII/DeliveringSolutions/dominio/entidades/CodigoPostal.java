package com.IsoII.DeliveringSolutions.dominio.entidades;

import jakarta.persistence.*;
import java.util.*;

/**
 * Representa un código postal que puede ser utilizado para identificar una
 * dirección de entrega.
 * 
 * @version 1.0
 */
@Entity
@Table(name = "CodigoPostal")
public class CodigoPostal {
    @Id
    @Column(name = "NumCodigoPostal", nullable = false, length = 5)
    private String codigo;
}