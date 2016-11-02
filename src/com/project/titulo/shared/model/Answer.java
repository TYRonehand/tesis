package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Answer implements IsSerializable {
	private String idcomentary;
	private String idtopic;
	private String description;
	private String creation;
	private String edition;
	private String iduser;
	private String numsubcomment;

	@SuppressWarnings("unused")
	private Answer() {

	}

	public Answer(String Description, String Idtopic, String Iduser) {
		this.setDescription(Description);
		this.setIdtopic(Idtopic);
		this.setIduser(Iduser);
	}

	public Answer(String idcomentary, String idtopic, String Description,
			String creation, String edition, String Numsubcomment, String iduser) {
		this.setIdcomentary(idcomentary);
		this.setIdtopic(idtopic);
		this.setDescription(Description);
		this.setCreation(creation);
		this.setEdition(edition);
		this.setNumsubcomment(Numsubcomment);
		this.setIduser(iduser);
	}

	public String getIdcomentary() {
		return idcomentary;
	}

	public void setIdcomentary(String idcomentary) {
		this.idcomentary = idcomentary;
	}

	public String getIdtopic() {
		return idtopic;
	}

	public void setIdtopic(String idtopic) {
		this.idtopic = idtopic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String comentary) {
		this.description = comentary;
	}

	public String getCreation() {
		return creation;
	}

	public void setCreation(String creation) {
		this.creation = creation;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public String getNumsubcomment() {
		return numsubcomment;
	}

	public void setNumsubcomment(String numsubcomment) {
		this.numsubcomment = numsubcomment;
	}

}
