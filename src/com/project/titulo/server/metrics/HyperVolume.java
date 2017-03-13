package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class HyperVolume {

	private List<UserFile> FileList;

	// result are added here
	private List<MetricResults> ResultList = new ArrayList<MetricResults>();

	// load files for metric
	public HyperVolume(List<UserFile> listPFcalc) {
		this.FileList = listPFcalc;
		Start();
	}

	// go throw list
	private void Start() {

		for (UserFile file : this.FileList) {
			// metric data
			MetricResults mr = new MetricResults();
			// save name pareto front
			mr.addParetoNameFile("Results");
			// text to double points
			TextToDouble paretoOptime = new TextToDouble();
			paretoOptime.create(file.getData(),
					Integer.parseInt(file.getDimension()));

			// save name pareto aproximation
			mr.setAproximationNameFile(file.getTitle());

			// calculate metric
			mr.addResult(Calculate(paretoOptime.getListPoints()));// calculate file data

			this.ResultList.add(mr);

		}
	}

	// calculate one file at time
	
	private Double Calculate(List<Points> aproximationDataSet) 
	{
		int Error=0;
		
		double HV = 0.0;
		Points lastpoint = new Points();
		lastpoint.add(0);//x0-x
		lastpoint.add(0);//x1-y
		lastpoint.add(0);//x2-z
		//PARETO SET
		for(Points point : aproximationDataSet){
			//control dimension
			if(point.getDimension()!=2)
			{
				Error++;
				break;
			}
				
			double width = lastpoint.getAxieIndex(0) - point.getAxieIndex(0);
			double height = point.getAxieIndex(1);
			double deep = point.getAxieIndex(2);
			
			double volumeAcumulated = Math.abs(width*height*deep);
				
			HV += volumeAcumulated;

			lastpoint = point;
		}
		
		if(Error==0){
			return  HV;
		}else{
			return null;
		}
	}
	
	// get result calculated
	public List<MetricResults> getResults() {
		return this.ResultList;
	}

}
