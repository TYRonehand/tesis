package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class GNVG {

	private List<UserFile> FileList;

	private List<MetricResults> ResultList = new ArrayList<MetricResults>();// result are added here

	// load files for metric
	public GNVG(List<UserFile> listPFcalc) {
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
			paretoOptime.create(file.getData(), Integer.parseInt(file.getDimension()));

			// save name pareto aproximation
			mr.setAproximationNameFile(file.getTitle());

			// calculate metric
			mr.addResult(Double.toString(Calculate(paretoOptime.getListPoints())));// calculate file data

			this.ResultList.add(mr);

		}
	}

	// calculate one file at time
	
	private static double Calculate(List<Points> aproximationDataList) {
		
		return aproximationDataList.size();
	}
	
	// get result calculated
	public List<MetricResults> getResults() {
		return this.ResultList;
	}

}

