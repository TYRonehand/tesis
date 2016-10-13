package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class ErrorRatio {
	
	private int axis_size;
	
	private UserFile paretoFile;
	private List<UserFile> aproxFileList;
	
	private List<Points> paretoDataList;//pf true points doubles
	
	private List<MetricResults> ResultList = new ArrayList<MetricResults>();//result are added here	
	
	//load files for metric
	public ErrorRatio(UserFile PFtrue, List<UserFile> listPFcalc,int axis_size){
		this.paretoFile = PFtrue;
		this.aproxFileList = listPFcalc;
		this.axis_size = axis_size;
		
		Start();
	}
	
	//go throw list
	private void Start()
	{
		
		//pftrue to double
		TextToDouble pftrue = new TextToDouble();
		pftrue.create(paretoFile.getData(), this.axis_size);
		this.paretoDataList = pftrue.getListPoints();
		
		//run data list if exist pftrue
		if(this.paretoDataList.size()>0)
		{
			
			for(UserFile file : this.aproxFileList)
			{
				//metric data
				MetricResults mr  = new MetricResults();
				//save name pareto front
				mr.setParetoNameFile(paretoFile.getTitle());
				//save name pareto aproximation
				mr.setAproximationNameFile(file.getTitle());
				
				//data dimension equals
				if(this.paretoFile.getDimension().equals(file.getDimension()))
				{
					
					TextToDouble paretoOp = new TextToDouble();
					paretoOp.create(file.getData(), this.axis_size);
					
					//calculate metric
					String Value = Calculate(paretoOp.getListPoints());
					//value return
					if(!Value.isEmpty() && Value != null)
					{
						//save data
						mr.setResults(Value);
						mr.setMessage("Ok");
					}else{

						//fail calculate
						mr.setResults("-");
						mr.setMessage("Error");
					}
				}
				else{
					System.err.println("different dimension");
					//fail dimension
					mr.setMessage("Wronge dimension");
					mr.setResults("-");
				}
				this.ResultList.add(mr);
			}
		}
		else{
			System.err.println("pareto front empty");
		}
	}
	
	//calculate one file at time
	private String Calculate(List<Points> paretoOp)
	{

		System.err.print("\nevaluating...");
		
		//count equals points
		float errori = 0;
		if(paretoOp.size()>0)
		{
			//pareto front optime points
			for(Points po: paretoOp)
			{
				//pareto front true points
				for(Points pf: this.paretoDataList)
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
						System.err.println("calculate dimension error");
					}
				}
			}

			//formula
			float Nsize = paretoOp.size();
			float ER=(1-(errori/Nsize));
			
			System.err.print("\nready!");
			return ER+"";
			
		}
		return null;
	}
	
	
	public List<MetricResults> getResults(){
		return this.ResultList;
	}
	
}
