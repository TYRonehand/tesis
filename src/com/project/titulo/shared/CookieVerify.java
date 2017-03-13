package com.project.titulo.shared;

import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class CookieVerify {
	// variable names
	private Date time = new Date(new Date().getTime() + (1000 * 60 * 60 * (1)));//milisecond*second*minutes*hour
	private String iduser = "iux";
	private String idurl = "url";
	private String idtopic = "itx";

	public CookieVerify(Boolean init) {
		// inicializa cookie
		if (init) {
			this.setCookieUser("");
			this.setCookieIdurl("login");
			this.setCookieITopic("");
		}

	}

	// getters internal
	private Date getTime() {
		return this.time;
	}

	private String getIduser() {
		return this.iduser;
	}

	private String getIdurl() {
		return idurl;
	}

	private String getIdtopic() {
		return idtopic;
	}

	// cookie id
	public void setCookieUser(String value) {
		Cookies.setCookie(getIduser(), value, getTime());
	}

	// cookieurl
	public void setCookieIdurl(String value) {
		Cookies.setCookie(getIdurl(), value, getTime());
	}

	// cookie topic
	public void setCookieITopic(String value) {
		Cookies.setCookie(getIdtopic(), value, getTime());
	}

	// cookie id
	public String getCookieUser() {
		return Cookies.getCookie(getIduser());
	}

	// cookie topic
	public String getCookieTopic() {
		return Cookies.getCookie(getIdtopic());
	}

	// cookieurl
	public String getCookieIdurl() {
		return Cookies.getCookie(getIdurl());
	}

	// exist cookie created
	public Boolean CookieExist() {
		// if all cookies are not empty -> true
		if (!Cookies.getCookie(getIduser()).isEmpty()) {
			return true;
		} else// if there is any one empty -> false
		{
			delCookiesInfo();
			return false;
		}

	}

	// delete cookie
	public void delCookiesInfo() {

		this.setCookieUser("");
		this.setCookieIdurl("");
		this.setCookieITopic("");
		Cookies.removeCookie(this.getIdurl());
		Cookies.removeCookie(this.getIdtopic());
		Cookies.removeCookie(this.getIduser());
		Window.Location.reload();
	}

}
