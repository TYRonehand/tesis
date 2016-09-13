package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Answer  implements IsSerializable{
	private String idcomentary;
	private String idtopic;
	private String comentary;
	private String creation;
	private String modify;
	private String enabled;
	private String iduser;
	
	@SuppressWarnings("unused")
	private Answer(){
		
	}
	
	public Answer(String Comentary, String Idtopic, String Iduser)
	{
		this.setComentary(Comentary);
		this.setIdtopic(Idtopic);
		this.setIduser(Iduser);
	}
	
	public Answer(String idcomentary,String idtopic, String Comentary, String creation, String modify, String enabled, String iduser)
	{
		this.setIdcomentary(idcomentary);
		this.setIdtopic(idtopic);
		this.setComentary(Comentary);
		this.setCreation(creation);
		this.setModify(modify);
		this.setEnabled(enabled);
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

	public String getComentary() {
		return comentary;
	}

	public void setComentary(String comentary) {
		this.comentary = comentary;
	}

	public String getCreation() {
		return creation;
	}

	public void setCreation(String creation) {
		this.creation = creation;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
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
