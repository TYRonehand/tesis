package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SubAnswer implements IsSerializable {
	private String idsubcomment;
	private String description;
	private String creation;
	private String idcomment;
	private String iduser;

	@SuppressWarnings("unused")
	private SubAnswer() {

	}

	public SubAnswer(String Description, String Idcomment, String Iduser) {
		this.setDescription(Description);
		this.setIdcomment(Idcomment);
		this.setIduser(Iduser);
	}

	public SubAnswer(String idsubcomment, String Idcomment, String Description, String Creation, String Iduser) {
		this.setIdsubcomment(idsubcomment);
		this.setDescription(Description);
		this.setIdcomment(Idcomment);
		this.setIduser(Iduser);
		this.setCreation(Creation);
		
	}

	public String getIdsubcomment() {
		return idsubcomment;
	}

	public void setIdsubcomment(String idsubcomment) {
		this.idsubcomment = idsubcomment;
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

	public String getIdcomment() {
		return idcomment;
	}

	public void setIdcomment(String idcomment) {
		this.idcomment = idcomment;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}


}
