package com.example.project.table;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredientes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ingrediente implements Serializable{
	private static final long serialVersionUID = 2L;
	
	@Id
	@Column(name="idingredientes")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional=false)
	private Integer idingredientes;
	
	@Column(name="Nombre")
	private String nombre;
	
	@Column(name="stock")
	private Integer stock;

	public Integer getIdIngredientes() {
		return idingredientes;
	}

	public void setIdIngredientes(Integer idIngredientes) {
		this.idingredientes = idIngredientes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
