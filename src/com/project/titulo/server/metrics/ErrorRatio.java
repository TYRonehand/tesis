package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class ErrorRatio {
	
	private int axis_size;
	private List<UserFile> aproxFileList;
	
	private List<MetricResults> ResultList = new ArrayList<MetricResults>();//result are added here	
	
	//load files for metric
	public ErrorRatio( List<UserFile> listPFcalc, int axis_size){
		this.aproxFileList = listPFcalc;
		this.axis_size = axis_size;
		
		Start();
	}
	
	//go throw list
	private void Start()
	{
		System.err.print("\nStart");
		List<UserFile> auxFileList = this.aproxFileList;
		System.err.print("\nauxFileSize: "+auxFileList.size());
		//files 
		for(UserFile file : this.aproxFileList)
		{
			//text to double points
			TextToDouble paretoOptime = new TextToDouble();
			paretoOptime.create(file.getData(), this.axis_size);

			System.err.print("\nparetoOptime POINT Size: "+paretoOptime.getListPoints().size());
			
			//set object results
			MetricResults mr = new MetricResults();
			mr.setAproximationNameFile(file.getTitle());//name POknow
			
			//files to compare with
			for(UserFile auxfile : this.aproxFileList)
			{
				mr.addParetoNameFile(auxfile.getTitle());//name PFtrue
				
				//data dimension equals
				if(auxfile.getDimension().equals(file.getDimension()))
				{
					//text to double points
					TextToDouble paretoFrontPoint = new TextToDouble();
					paretoFrontPoint.create(auxfile.getData(), this.axis_size);

					System.err.print("\nparetoFrontPoint POINT Size: "+paretoFrontPoint.getListPoints().size());
					
					//calculate metric
					String value = Calculate( paretoFrontPoint.getListPoints(), paretoOptime.getListPoints()) ;
					System.err.print("\nCalculated VALUE:"+value);
					
					if(!value.isEmpty() && value!=null)
						mr.addResult( value);
					else
						mr.addResult("Error");
				}
				else{
					System.err.println("different dimension");
					//fail dimension
					mr.addResult("Wronge dimension");
				}
			}

			System.err.print("result added!");
			this.ResultList.add(mr);
			
		}
		
	}
	
	//calculate one file at time
	private String Calculate(List<Points> paretoDataList, List<Points> paretoOptime)
	{
		System.err.print("\nevaluating...");
		
		//count equals points
		float errori = 0;
		if(paretoOptime.size()>0)
		{
			//pareto front optime points
			for(Points po: paretoOptime)
			{
				//pareto front true points
				for(Points pf: paretoDataList)
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
			double Nsize = paretoOptime.size();
			double ER = (1-(errori/Nsize));
			
			System.err.print("\nready!");
			return String.format( "%.6f", ER);
			
		}
		return null;
	}
	
	
	public List<MetricResults> getResults(){
		return this.ResultList;
	}
	
}
