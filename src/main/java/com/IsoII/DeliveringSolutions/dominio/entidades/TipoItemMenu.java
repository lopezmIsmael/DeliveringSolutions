package com.IsoII.DeliveringSolutions.dominio.entidades;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "TipoItemMenu")
public class TipoItemMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tipo;
}