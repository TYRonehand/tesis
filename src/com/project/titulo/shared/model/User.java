package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
public class User implements IsSerializable {

	private String id;
	private String mail;
	private String name;
	private String lastname;
	private String country;
	private String ocupation;
	private String web;
	private String university;
	private String password;
	private String creation;
	private String registered;
	private String banned;
	private String pin;
	
	@SuppressWarnings("unused")
	private User() {
		//just here because GWT wants it.
	}
	public User(String id, String mail, String name, String lastname, String country, String ocupation, String web, 
				String university, String password, String creation, String registered, String banned,String pin) 
	{
		this.setId(id);
		this.setMail(mail);
		this.setName(name);
		this.setLastname(lastname);
		this.setCountry(country);
		this.setOcupation(ocupation);
		this.setWeb(web);
		this.setUniversity(university);
		this.setPassword(password);
		this.setCreation(creation);
		this.setRegistered(registered);
		this.setBanned(banned);
		this.setPin(pin);
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOcupation() {
		return ocupation;
	}
	public void setOcupation(String ocupation) {
		this.ocupation = ocupation;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreation() {
		return creation;
	}
	public void setCreation(String creation) {
		this.creation = creation;
	}
	public String getRegistered() {
		return registered;
	}
	public void setRegistered(String registered) {
		this.registered = registered;
	}
	public String getBanned() {
		return banned;
	}
	public void setBanned(String banned) {
		this.banned = banned;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}

	
}

