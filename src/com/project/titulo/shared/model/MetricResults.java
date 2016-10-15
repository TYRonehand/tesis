package com.project.titulo.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MetricResults implements Serializable  
{
	private String Message;
	private String aproximationNameFile;
	private List<String> resultList = new ArrayList<String>();
	private List<String> paretoNameFileList = new ArrayList<String>();;
	
	public MetricResults(){
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public List<String> getResultsList() {
		return resultList;
	}

	public void addResult(String result) {
		this.resultList.add(result);
	}

	public String getAproximationNameFile() {
		return aproximationNameFile;
	}

	public void setAproximationNameFile(String aproximationNameFile) {
		this.aproximationNameFile = aproximationNameFile;
	}

	public List<String> getParetoNameFile() {
		return paretoNameFileList;
	}

	public void addParetoNameFile(String paretoNameFile) {
		this.paretoNameFileList.add(paretoNameFile);
	}

}
