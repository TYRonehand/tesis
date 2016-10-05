package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class Spacing {
	private String Message;
	
	private int axis_size;
	
	private UserFile PFtrue_File;
	private List<UserFile> FileList;
	
	private List<Points> PFtrue_data;//pf true points doubles
	
	private List<String> ResultList = new ArrayList<String>();//result are added here	
	
	//load files for metric
	public Spacing(UserFile PFtrue, List<UserFile> listPFcalc,int axis_size){
		this.PFtrue_File = PFtrue;
		this.FileList = listPFcalc;
		this.axis_size = axis_size;
		
		
		Start();
	}
	
	//go throw list
	private void Start()
	{
		//pftrue to double
		TextToDouble pftrue = new TextToDouble();
		pftrue.create(PFtrue_File.getData(), this.axis_size);
		this.PFtrue_data = pftrue.getListPoints();
		
		//run data list if exist pftrue
		if(this.PFtrue_data.size()>0)
		{
			for(UserFile file : this.FileList)
			{
				if(this.PFtrue_File.getDimension().equals(file.getDimension()))
				{
					TextToDouble paretoOp = new TextToDouble();
					paretoOp.create(file.getData(), this.axis_size);
					Calculate(paretoOp.getListPoints());//calculate file data
				}
				else{
					this.ResultList.add("different dimensions");
				}
			}
		}
		else{
			this.setMessage("No data inf Pareto Front");
		}
	}
	
	//calculate one file at time
	private void Calculate(List<Points> paretoOp)
	{
		
	}
	
	//get result calculated
	public List<String> getResults()
	{
		return this.ResultList;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
	
}
