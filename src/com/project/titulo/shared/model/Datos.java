package com.project.titulo.shared.model;

public class Datos extends Object {
	public String nombre;
	public String[] labels;
	public String Ruta;
	public double[][] Datos;
	public int Large;

	Datos() {
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
