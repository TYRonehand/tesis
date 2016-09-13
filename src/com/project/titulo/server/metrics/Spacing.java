package com.project.titulo.server.metrics;

import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.UserFile;

	@SuppressWarnings("unused")
public class Spacing {

	private UserFile file1;
	private List<UserFile> file2;
	private double[][] data1;
	private List<double[][]> data2;
	private int dim1,dim2;
	
	
	//load files for metric
	public Spacing(UserFile PFtrue, List<UserFile> listPFcalc){
		this.file1 = PFtrue;
		this.file2 = listPFcalc;
		setDataNumber();
	}
	
	private void setDataNumber()
	{
		//getd number data from pftrue
		TextToDouble aux1 = new TextToDouble(this.file1.getData(),this.dim1);
		this.data1 = aux1.getValues();
		aux1=null;
		//get numer data from list pf calculate
		for(UserFile aux:file2){
			TextToDouble aux2 = new TextToDouble(aux.getData(),this.dim2);
			this.data2.add(aux2.getValues());
			aux2=null;
		}
	}
	
	
	
}
