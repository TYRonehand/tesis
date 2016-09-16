package com.project.titulo.shared.model;

import java.util.ArrayList;
import java.util.List;

public class Points {
	private List<Double> axis = new ArrayList<Double>();//x,y,z,.....
	
	public Points(){
	}
	
	public void add(double value){
		this.axis.add(value);
	}
	public int getDimension(){
		return this.axis.size();
	}
	public List<Double> getAxies(){
		return this.axis;
	}
	public double getAxieIndex(int i){
		return this.axis.get(i);
	}
}
