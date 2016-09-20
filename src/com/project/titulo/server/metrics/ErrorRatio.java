package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class ErrorRatio {

	private int axis_size;
	
	private UserFile PFtrue_File;
	private List<UserFile> FileList;
	
	private List<Points> PFtrue_data;//pf true points doubles
	
	private List<Float> ResultList = new ArrayList<Float>();//result are added here	
	
	//load files for metric
	public ErrorRatio(UserFile PFtrue, List<UserFile> listPFcalc,int axis_size){
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
				TextToDouble paretoOp = new TextToDouble();
				paretoOp.create(file.getData(), this.axis_size);
				Calculate(paretoOp.getListPoints());//calculate file data
			}
		}
	}
	
	//calculate one file at time
	private String Calculate(List<Points> paretoOp)
	{
		//count equals points
		float errori = 0;
		if(paretoOp.size()>0)
		{
			//pareto front optime points
			for(Points po: paretoOp)
			{
				//pareto front true points
				for(Points pf: this.PFtrue_data)
				{
					//same dimension for axies
					if(po.getDimension()==pf.getDimension())
					{
						boolean axies_equal = true;
						//elements from axies
						for(int i = 0; i < po.getDimension(); i++)
						{
							//if x!=X y!=Y ...z!=Z
							if(po.getAxieIndex(i)!=pf.getAxieIndex(i)){
								axies_equal = false;//all axies aren't equal
							}
						}
						//if axies arent equal +1 error
						if(!axies_equal){
							errori++;
						}
					}
					else//diferent dimension error to calculate
					{
						return "Dimension Pareto Front True: "+pf.getDimension()+" differs from Pareto Optime: "+po.getDimension();
					}
				}
			}

			//formula
			float Nsize = paretoOp.size();
			this.ResultList.add( (errori/Nsize));
			
		}
		else//diferent dimension error to calculate
		{
			return "Dimension from file is zero";
		}
		return null;
	}
	
	//get result calculated
	public List<Float> getResults()
	{
		return this.ResultList;
	}
	
}