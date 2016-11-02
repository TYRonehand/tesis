package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Topic implements IsSerializable {

	private String idtopic;
	private String title;
	private String description;
	private String creation;
	private String edition;
	private String banned;
	private String iduser;
	private String numcomments;

	@SuppressWarnings("unused")
	private Topic() {

	}

	// add topic
	public Topic(String Title, String Resume, String Iduser) {
		this.setTitle(Title);
		this.setDescription(Resume);
		this.setIduser(Iduser);
	}

	// update topic
	public Topic(String idtopic, String Title, String Resume, String date) {
		this.setIdtopic(idtopic);
		this.setTitle(Title);
		this.setDescription(Resume);
		this.setEdition(date);
	}

	// get info
	public Topic(String idtopic, String Title, String Description, String Iduser,
			String Creation, String Banned, String edited, String Numcomment) {
		this.setIdtopic(idtopic);
		this.setTitle(Title);
		this.setDescription(Description);
		this.setIduser(Iduser);
		this.setCreation(Creation);
		this.setBanned(Banned);
		this.setEdition(edited);
		this.setNumcomments(Numcomment);
	}

	public String getIdtopic() {
		return idtopic;
	}

	public void setIdtopic(String idtopic) {
		this.idtopic = idtopic;
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

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getbanned() {
		return banned;
	}

	public void setBanned(String banned) {
		this.banned = banned;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public String getNumcomments() {
		return numcomments;
	}

	public void setNumcomments(String numcomments) {
		this.numcomments = numcomments;
	}

}
