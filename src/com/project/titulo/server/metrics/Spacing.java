package com.project.titulo.server.metrics;

import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

	@SuppressWarnings("unused")
public class Spacing {

	private UserFile file1;
	private List<UserFile> file2;
	private List<Points> data1;
	private List<Points> data2;
	private int dim1,dim2;
	
	
	//load files for metric
	public Spacing(UserFile PFtrue, List<UserFile> listPFcalc){
		this.file1 = PFtrue;
		this.file2 = listPFcalc;
		setDataNumber();
	}
	
	private void setDataNumber()
	{
		
	}
	
	
	
}
