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

	private List<MetricResults> ResultList = new ArrayList<MetricResults>();// result
																			// are
																			// added
																			// here

	// load files for metric
	public ErrorRatio(List<UserFile> listPFcalc, int axis_size) {
		this.aproxFileList = listPFcalc;
		this.axis_size = axis_size;

		Start();
	}

	// go throw list
	private void Start() {
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
					String value = Double.toString(Calculate(paretoFrontPoint.getListPoints(), paretoOptime.getListPoints()));

					if (!value.isEmpty() && value != null)
						mr.addResult(value);
					else
						mr.addResult("Error");
				} else {
					// fail dimension
					mr.addResult("Wronge dimension");
				}
			}

			this.ResultList.add(mr);

		}

	}

	// calculate one file at time
	
	private static double Calculate(List<Points> paretoDataList, List<Points> paretoOptime) {
		float errori = 0;
			for (Points po : paretoOptime) {
				for (Points pf : paretoDataList) {
						int conterr = 0;
						for (int i = 0; i < po.getDimension(); i++) {
							if (po.getAxieIndex(i) != pf.getAxieIndex(i)) {
								conterr++;
							}
						}
						if (conterr < po.getDimension()) 
							errori++;
				}
			}
			double Nsize = paretoOptime.size();
			double ER = (1 - (errori / Nsize));
			return ER;
	}

	
	public List<MetricResults> getResults() {
		return this.ResultList;
	}

}
