package com.project.titulo.server.metrics;

import java.util.ArrayList;
import java.util.List;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.Points;
import com.project.titulo.shared.model.UserFile;

public class Coverage {
	private int axis_size;
	private List<UserFile> aproxFileList;

	private List<MetricResults> ResultList = new ArrayList<MetricResults>();// result
																			// are
																			// added
																			// here

	// load files for metric
	public Coverage(List<UserFile> listPFcalc, int axis_size) {
		this.aproxFileList = listPFcalc;
		this.axis_size = axis_size;

		Start();
	}

	// go throw list
	private void Start() {
		System.err.print("\nStart");

		// files
		for (UserFile file : this.aproxFileList) {
			// text to double points
			TextToDouble paretoOptime = new TextToDouble();
			paretoOptime.create(file.getData(), this.axis_size);

			// set object results
			MetricResults mr = new MetricResults();
			mr.setAproximationNameFile(file.getTitle());// name POknow

			// files to compare with
			for (UserFile auxfile : this.aproxFileList) {
				mr.addParetoNameFile(auxfile.getTitle());// name PFtrue

				// data dimension equals
				if (auxfile.getDimension().equals(file.getDimension())) {
					// text to double points
					TextToDouble paretoFrontPoint = new TextToDouble();
					paretoFrontPoint.create(auxfile.getData(), this.axis_size);

					// calculate metric
					double value = Calculate(paretoFrontPoint.getListPoints(),
							paretoOptime.getListPoints());
					mr.addResult(value);
				} else {
					System.err.println("different dimension");
					// fail dimension
					mr.addResult(null);
					mr.setMessage("Wronge Dimension");
				}
			}

			System.err.print("result added!");
			this.ResultList.add(mr);

		}

	}

	// calculate one file at time
	
	private double Calculate(List<Points> paretoDataList, List<Points> aproximationDataList) {
		int dominated = 0;
		for (Points po : aproximationDataList) {
			for (Points pf : paretoDataList) {
				int domcount = 0;
				for (int i = 0; i < po.getDimension(); i++) {
					if (po.getAxieIndex(i) < pf.getAxieIndex(i)) {
						domcount++;
					}
				}
				if (domcount > 0) {
					dominated++;
				}
			}
		}
		double Nsize = aproximationDataList.size();
		double CV = ((dominated / Nsize));
		return CV;
	}

	// get result calculated
	public List<MetricResults> getResults() {
		return this.ResultList;
	}
}
