package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class ErrorRatio {

	private String Message;
	
	private int axis_size;
	
	private UserFile PFtrue_File;
	private List<UserFile> FileList;
	
	private List<Points> PFtrue_data;//pf true points doubles
	
	private List<String> ResultList = new ArrayList<String>();//result are added here	
	
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
		System.err.println("NEW DATA");
		//count equals points
		float errori = 0;
		if(paretoOp.size()>0)
		{
			System.err.println("contains elements");
			//pareto front optime points
			for(Points po: paretoOp)
			{
				//pareto front true points
				for(Points pf: this.PFtrue_data)
				{
					//same dimension for axies
					if(po.getDimension()==pf.getDimension())
					{
						int conterr=0;
						//elements from axies
						for(int i = 0; i < po.getDimension(); i++)
						{
							//if x!=X y!=Y ...z!=Z
							if(po.getAxieIndex(i)!=pf.getAxieIndex(i))
							{
								conterr++;//all axies aren't equal
							}
						}
						
						//if axies arent equal +1 error
						if(conterr<po.getDimension()){
							errori++;
							
						}
					}
					else//diferent dimension error to calculate
					{
						this.setMessage("Dimension Pareto Front True: "+pf.getDimension()+" differs from Pareto Optime: "+po.getDimension());
						
					}
				}
			}

			//formula
			float Nsize = paretoOp.size();
			System.err.println("ei = "+errori );
			
			System.err.println("N = "+Nsize);

			System.err.println("ER = "+(1-(errori/Nsize)));
			float ER=(1-(errori/Nsize));
			this.ResultList.add( ER+"");
			
		}
		else//diferent dimension error to calculate
		{
			this.setMessage( "Dimension from file is zero");
		}
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
