package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserFile implements IsSerializable {
	private String idfile;
	private String title;
	private String description;
	private String creation;
	private String dimension;
	private String labelx;
	private String labely;
	private String labelz;
	private String plot;
	private String metric;
	private String data;
	private String iduser;

	@SuppressWarnings("unused")
	private UserFile() {

	}

	// add file
	public UserFile(String Title, String Dimension, String Labelx,
			String Labely, String Labelz, String Description, String Iduser,
			String Data) {
		this.setIduser(Iduser);
		this.setTitle(Title);
		this.setDimension(Dimension);
		this.setLabelx(Labelx);
		this.setLabely(Labely);
		this.setLabelz(Labelz);
		this.setDescription(Description);
		this.setData(Data);
	}

	// get file
	public UserFile(String Idfile, String Title, String Dimension,
			String Labelx, String Labely, String Labelz, String Description,
			String Iduser, String Creation, String Data, String Plot,
			String Metric) {
		this.setIdfile(Idfile);
		this.setIduser(Iduser);
		this.setTitle(Title);
		this.setDimension(Dimension);
		this.setLabelx(Labelx);
		this.setLabely(Labely);
		this.setLabelz(Labelz);
		this.setDescription(Description);
		this.setCreation(Creation);
		this.setData(Data);
		this.setPlot(Plot);
		this.setMetric(Metric);

	}

	public String getIdfile() {
		return idfile;
	}

	public void setIdfile(String iddatafile) {
		this.idfile = iddatafile;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
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

	public String getCreation() {
		return creation;
	}

	public void setCreation(String creation) {
		this.creation = creation;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getLabelx() {
		return labelx;
	}

	public void setLabelx(String labelx) {
		this.labelx = labelx;
	}

	public String getLabely() {
		return labely;
	}

	public void setLabely(String labely) {
		this.labely = labely;
	}

	public String getLabelz() {
		return labelz;
	}

	public void setLabelz(String labelz) {
		this.labelz = labelz;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}
}
