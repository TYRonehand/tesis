package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class Spacing {

	private List<UserFile> FileList;

	private List<MetricResults> ResultList = new ArrayList<MetricResults>();// result
																			// are
																			// added
																			// here

	// load files for metric
	public Spacing(List<UserFile> listPFcalc) {
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
			mr.addResult(Calculate(paretoOptime.getListPoints()));// calculate
																	// file data

			this.ResultList.add(mr);

		}
	}

	// calculate one file at time
	private String Calculate(List<Points> aproximationDataList) {

		System.err.print("\nevaluating...");
		// auxiliar list
		List<Points> auxDataList = aproximationDataList;

		double flag = 0.00000, F = 0.00000, D = 0.00000, pro = 0.00000, dif = 0.00000;
		double d[] = new double[aproximationDataList.size()];

		for (int i = 0; i < aproximationDataList.size(); i++) {
			for (int j = 0; j < auxDataList.size(); j++) {
				if (i != j) {
					Points aproximationDataPoint = aproximationDataList.get(i);
					Points auxDataPoint = auxDataList.get(j);

					for (int k = 0; k < aproximationDataPoint.getDimension(); k++) {
						D += Math
								.pow((auxDataPoint.getAxieIndex(k) - aproximationDataPoint
										.getAxieIndex(k)), 2);
					}
					D = Math.sqrt(D);
				}
				if (flag == 0) {
					F = D;
					flag = 1;
				}
				if (F > D) {
					F = D;
				}
			}
			d[i] = F;
			pro = pro + F;
			flag = 0;
		}

		pro = pro / (aproximationDataList.size());

		for (int i = 0; i < aproximationDataList.size(); i++) {
			dif = dif + Math.pow((pro - d[i]), 2);
		}
		dif = dif / (aproximationDataList.size() - 1);
		dif = Math.sqrt(dif);
		// dif = Math.rint(dif * 100) / 100;

		System.err.print("\nready!");
		return String.format("%.6f", dif);

	}

	// get result calculated
	public List<MetricResults> getResults() {
		return this.ResultList;
	}

}
