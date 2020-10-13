package com.ejercicio.rest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sismos")
public class Sismos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


	public Sismos() {
	}


	public Sismos(String fecha, String magnitud) {
		this.fecha = fecha;
		this.magnitud = magnitud;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fecha;
	private String magnitud;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMagnitud() {
		return magnitud;
	}

	public void setMagnitud(String magnitud) {
		this.magnitud = magnitud;
	}

}
