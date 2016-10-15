package com.project.titulo.shared;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.project.titulo.shared.model.Points;

public class TextToDouble implements IsSerializable {

	private List<Points> listvalues = new ArrayList<>();
	
	public TextToDouble(){}
	
	//data text to double
	public void create(String datastring,int dimension)
	{
		//insert here all delimitators
		String delim = "[\n ;]"; 
		String[] values = datastring.split(delim);
		//doubles coordenates
		for(int i=0;i<values.length;i=i+dimension) //jump every dimension size
		{
			//create point
			Points axis = new Points();
			//all points from size dimension are add to one point
			for(int j=i;j<i+dimension;j++)
			{
				axis.add( Double.valueOf( values[j].replace(",",".") ) );//add axis to point
			}
			this.listvalues.add(axis);//add point to list of points
		}
	}
	
	
	public List<Points> getListPoints()
	{
		return this.listvalues;
	}
	
}
