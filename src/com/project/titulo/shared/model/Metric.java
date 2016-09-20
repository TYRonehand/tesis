package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Metric implements IsSerializable{

	private String idmetric;
	private String title;
	private String description;

	@SuppressWarnings("unused")
	private Metric(){
		
	}
	
	public Metric(String Title, String Description)
	{
		this.setTitle(Title);
		this.setDescription(Description);
	}
	
	public Metric(String Idmetric, String Title, String Description)
	{
		this.setIdmetric(Idmetric);
		this.setTitle(Title);
		this.setDescription(Description);
	}

	public String getIdmetric() {
		return idmetric;
	}

	public void setIdmetric(String idmetric) {
		this.idmetric = idmetric;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
