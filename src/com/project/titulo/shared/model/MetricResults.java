package com.project.titulo.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MetricResults implements Serializable  {
	private String Message;
	private List<String> results;
	
	public MetricResults(){
		setResults(new ArrayList<String>());
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

}
