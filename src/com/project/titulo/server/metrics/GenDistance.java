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

	private List<MetricResults> ResultList = new ArrayList<MetricResults>();// result
																			// are
																			// added
																			// here

	// load files for metric
	public GenDistance(List<UserFile> listPFcalc, int axis_size) {
		this.aproxFileList = listPFcalc;
		this.axis_size = axis_size;

		Start();
	}

	// go throw list
	private void Start() {
		System.err.print("\nStart");
		List<UserFile> auxFileList = this.aproxFileList;
		System.err.print("\nauxFileSize: " + auxFileList.size());
		// files
		for (UserFile file : this.aproxFileList) {
			// text to double points
			TextToDouble paretoOptime = new TextToDouble();
			paretoOptime.create(file.getData(), this.axis_size);

			System.err.print("\nparetoOptime POINT Size: "
					+ paretoOptime.getListPoints().size());

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

					System.err.print("\nparetoFrontPoint POINT Size: "
							+ paretoFrontPoint.getListPoints().size());

					// calculate metric
					String value = Calculate(paretoFrontPoint.getListPoints(),
							paretoOptime.getListPoints());
					System.err.print("\nCalculated VALUE:" + value);

					if (!value.isEmpty() && value != null)
						mr.addResult(value);
					else
						mr.addResult("Error");
				} else {
					System.err.println("different dimension");
					// fail dimension
					mr.addResult("Wronge dimension");
				}
			}

			System.err.print("result added!");
			this.ResultList.add(mr);

		}

	}

	// calculate one file at time
	private String Calculate(List<Points> paretoDataList,
			List<Points> aproximationDataList) {
		System.err.print("\nevaluating...");

		double D, F, G, flag;
		D = 0.00000;
		G = 0.00000;
		F = -1.00000;
		flag = 0.00000;
		for (int i = 0; i < aproximationDataList.size(); i++) {
			for (int j = 0; j < paretoDataList.size(); j++) {
				Points pareto = paretoDataList.get(j);
				Points optimo = aproximationDataList.get(i);

				for (int k = 0; k < optimo.getDimension(); k++) {
					D = (Math.pow(
							optimo.getAxieIndex(k) - pareto.getAxieIndex(k), 2));
					// System.err.print(String.format("\nD POW + = %.6f",D));
				}

				D = Math.sqrt(D);
				// System.err.print(String.format("\nD SQRT + = %.6f",D));
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
		System.err.print(String.format("\nG ante POW + = %.6f", G));

		G = (Math.pow(G, 0.5));

		System.err.print(String.format("\nG POW + = %.6f", G));

		G = (G / aproximationDataList.size());

		System.err.print(String.format("\nG DIVIDE + = %.6f", G));

		// G = Math.rint(G * 100) / 100;

		System.err.print(String.format("\nG FINAL + = %.6f", G));

		System.err.print("\nready!");
		return String.format("%.6f", G);
	}

	// get result calculated
	public List<MetricResults> getResults() {
		return this.ResultList;
	}
}
