package com.ejercicio.rest.dto;

public class SismosPorFechaDto {
	
	private String starttime;
	private String endtime;

	public SismosPorFechaDto() {
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
