package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Topic  implements IsSerializable{
	
	private String idtopic;
	private String title;
	private String description;
	private String creation;
	private String edition;
	private String enabled;
	private String iduser;
	
	@SuppressWarnings("unused")
	private Topic(){
		
	}
	
	//add topic
	public Topic(String Title, String Resume, String Iduser){
		this.setTitle(Title);
		this.setDescription(Resume);
		this.setIduser(Iduser);
	}
	
	//update topic
	public Topic(String idtopic,String Title, String Resume,String date){
		this.setIdtopic(idtopic);
		this.setTitle(Title);
		this.setDescription(Resume);
		this.setEdition(date);
	}
	
	//get info
	public Topic(String idtopic, String Title, String Resume, String Iduser,String Creation, String Enabled, String edited){
		this.setIdtopic(idtopic);
		this.setTitle(Title);
		this.setDescription(Resume);
		this.setIduser(Iduser);
		this.setCreation(Creation);
		this.setEnabled(Enabled);
		this.setEdition(edited);
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

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}
	
}
