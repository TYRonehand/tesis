package com.project.titulo.shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MetricResults implements Serializable  
{
	private String Message;
	private String results;
	private String aproximationNameFile;
	private String paretoNameFile;
	
	public MetricResults(){
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getAproximationNameFile() {
		return aproximationNameFile;
	}

	public void setAproximationNameFile(String aproximationNameFile) {
		this.aproximationNameFile = aproximationNameFile;
	}

	public String getParetoNameFile() {
		return paretoNameFile;
	}

	public void setParetoNameFile(String paretoNameFile) {
		this.paretoNameFile = paretoNameFile;
	}

}
