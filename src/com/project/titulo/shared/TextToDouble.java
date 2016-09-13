package com.project.titulo.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.IsSerializable;

public class TextToDouble implements IsSerializable {

	private double[][] values=null;
	
	public TextToDouble(String numbers,int dim)
	{
		try{
			List<String> valores = new ArrayList<String>();
			
			String delim = "[\n ]"; //insert here all delimitators
			String[] st = numbers.split(delim);
			for(String s:st) 
			{
				valores.add(s);
			}
			//size of array
			int sizeX = valores.size()/dim;
			//string to double
			values = new double[sizeX][dim];
			for(int i=0;i<sizeX;i++)
			{
				for(int j=0;j<dim;j++)
				{
				    values[i][j] = Double.parseDouble(valores.get(i+j).replace(",","."));
				}
			}

			
		}
		catch(Exception ex)
		{
			GWT.log("error: "+ex.toString());
		}
	}
	
	
	public double[][] getValues()
	{
		return this.values;
	}
	
}
