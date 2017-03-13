package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class GenDistance {
	private int axis_size;
	private List<UserFile> aproxFileList;
	private List<MetricResults> ResultList = new ArrayList<MetricResults>();
	
	// load files for metric
	public GenDistance(List<UserFile> listPFcalc, int axis_size) {
		this.aproxFileList = listPFcalc;
		this.axis_size = axis_size;
		Start();
	}

	private void Start() {
		for (UserFile file : this.aproxFileList) {
			// text to double points
			TextToDouble paretoOptime = new TextToDouble();
			paretoOptime.create(file.getData(), this.axis_size);
			// set object results
			MetricResults mr = new MetricResults();
			mr.setAproximationNameFile(file.getTitle());// name PFknow
			// files to compare with
			for (UserFile auxfile : this.aproxFileList) {
				mr.addParetoNameFile(auxfile.getTitle());// name PFtrue

				// data dimension equals
				if (auxfile.getDimension().equals(file.getDimension())) {
					// text to double points
					TextToDouble paretoFrontPoint = new TextToDouble();
					paretoFrontPoint.create(auxfile.getData(), this.axis_size);

					// calculate metric
					double value = Calculate(paretoFrontPoint.getListPoints(), paretoOptime.getListPoints());
					mr.addResult(value);
				} else {
					// fail dimension
					mr.addResult(null);
					mr.setMessage("Wronge Dimension");
				}
			}
			this.ResultList.add(mr);
		}
	}

	// calculate one file at time
	private static double Calculate(List<Points> paretoDataList, List<Points> aproximationDataList) {
		double D, F, G, flag;
		D = 0.0;
		G = 0.0;
		F = -1.0;
		flag = 0.00000;
		for (int i = 0; i < aproximationDataList.size(); i++) {
			for (int j = 0; j < paretoDataList.size(); j++) {
				Points pareto = paretoDataList.get(j);
				Points optimo = aproximationDataList.get(i);
				for (int k = 0; k < optimo.getDimension(); k++) {
					D = (Math.pow(optimo.getAxieIndex(k) - pareto.getAxieIndex(k), 2));
				}
				D = Math.sqrt(D);
				if (flag == 0) {
					F = D;
					flag = 1;
				}
				if (F > D) {F = D;}
			}
			G = (G + Math.pow(F, 2));
			flag = 0;
		}
		G = (Math.pow(G, 0.5));
		G = (G / aproximationDataList.size());
		return  G;
	}

	// get result calculated
	public List<MetricResults> getResults() {
		return this.ResultList;
	}
}
