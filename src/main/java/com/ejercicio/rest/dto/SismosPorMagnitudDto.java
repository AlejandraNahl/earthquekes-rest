package com.ejercicio.rest.dto;

public class SismosPorMagnitudDto {

	private String minMagnitude;
	private String maxMagnitude;

	public SismosPorMagnitudDto() {
	}

	public String getMinMagnitude() {
		return minMagnitude;
	}

	public void setMinMagnitude(String minMagnitude) {
		this.minMagnitude = minMagnitude;
	}

	public String getMaxMagnitude() {
		return maxMagnitude;
	}

	public void setMaxMagnitude(String maxMagnitude) {
		this.maxMagnitude = maxMagnitude;
	}
}
