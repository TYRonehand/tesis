package com.project.titulo.shared;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.project.titulo.shared.model.MetricResults;

public class ExportDataResult {

	private final char DEFAULT_SEPARATOR = ';';
	private String PATH = null;

	private List<MetricResults> dataResultsList = new ArrayList<MetricResults>();
	private String IDUSER = null;

	public ExportDataResult(ServletContext context, String iduser,
			List<MetricResults> Results) {
		this.dataResultsList = Results;
		this.IDUSER = iduser;
		this.PATH = context.getRealPath("download") + "/";
	}

	public Boolean WriteFile() {

		try (PrintStream writer = new PrintStream(new FileOutputStream(PATH
				+ this.IDUSER + ".csv"))) {
			// cadena titulos
			List<String> titlesHorizontal = this.dataResultsList.get(0)
					.getParetoNameFile();
			String textline = ";";
			for (int i = 0; i < titlesHorizontal.size(); i++) {
				textline += titlesHorizontal.get(i);
				if (i < titlesHorizontal.size() - 1)
					textline += this.DEFAULT_SEPARATOR;
			}

			// write in textfile
			writer.println(textline);

			// create textlines from results
			for (MetricResults result : this.dataResultsList) {
				textline = result.getAproximationNameFile()
						+ this.DEFAULT_SEPARATOR;

				for (int j = 0; j < result.getResultsList().size(); j++) {
					textline += result.getResultsList().get(j);
					if (j < titlesHorizontal.size() - 1)
						textline += this.DEFAULT_SEPARATOR;
				}
				// write in textfile
				writer.println(textline);
			}
			// close textfile
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Export fail!");
			EmailAlert.WarningEmail("ExportDataResult.java - WriteFile", "Message: "+e.toString());
			return false;
		}
		System.err.println("Export Ok!");
		return true;

	}

}
