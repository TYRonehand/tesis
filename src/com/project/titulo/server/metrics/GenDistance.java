package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class GenDistance 
{
	private int axis_size;
	
	private UserFile paretoFile;
	private List<UserFile> aproxFileList;
	
	private List<Points> paretoDataList;//pf true points doubles
	private List<MetricResults> ResultList = new ArrayList<MetricResults>();//result are added here	
	
	//load files for metric
	public GenDistance(UserFile PFtrue, List<UserFile> listPFcalc,int axis_size)
	{
		this.paretoFile = PFtrue;
		this.aproxFileList = listPFcalc;
		this.axis_size = axis_size;
		
		Start();
	}
	
	//go throw list
	private void Start()
	{
		//pftrue to double
		TextToDouble pftrueaux = new TextToDouble();
		pftrueaux.create(paretoFile.getData(), this.axis_size);
		this.paretoDataList = pftrueaux.getListPoints();
		
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
				
				if(this.paretoFile.getDimension().equals(file.getDimension()))
				{
					TextToDouble paretoAux = new TextToDouble();
					paretoAux.create(file.getData(), this.axis_size);
					
					//calculate metric
					String Value = Calculate(paretoAux.getListPoints());//calculate file data
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
	private String Calculate(List<Points> aproximationDataList)
	{

		System.err.print("\nevaluating...");
		
	    double D, F, G, flag;
	    D = 0;
        G = 0;
        F = -1;
        flag = 0;
        for (int i = 0; i < aproximationDataList.size(); i++) 
        {
            for (int j = 0; j < paretoDataList.size(); j++) 
            {
            	Points pareto = paretoDataList.get(j);
            	Points optimo = aproximationDataList.get(i);
            	
            	for(int k =0; k <optimo.getDimension();k++)
            	{
            		D = (Math.pow(optimo.getAxieIndex(k) - pareto.getAxieIndex(k), 2));
            	}
            	
                D = Math.sqrt(D);
                if (flag == 0) {
                    F = D;
                    flag = 1;
                }
                if (F > D) {
                    F = D;
                }
            }
            G = (G + Math.pow(F, 2));
            flag = 0;
        }
        G = (Math.pow(G, 0.5));
        G = (G / aproximationDataList.size());
        G = Math.rint(G * 100) / 100;

		System.err.print("\nready!");
		return G+"";
	}
	
	//get result calculated
	public List<MetricResults> getResults()
	{
		return this.ResultList;
	}
}
