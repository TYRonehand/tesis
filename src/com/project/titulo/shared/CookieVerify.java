package com.project.titulo.shared;

import java.util.Date;
import com.google.gwt.user.client.Cookies;

public class CookieVerify {
	//variable names
	private  Date time = new Date(new Date().getTime()+(1000*60*60*2));
	private  String iduser="MOPuser";
	private  String idmail="MOPmail";
	private  String idname="MOPname";
	private  String idban="MOPban";
	private  String idurl="MOPurl";
	private  String idtopic="MOPtopic";
	
	public CookieVerify(Boolean init){
		//inicializa cookie
		if(init){
			this.setCookieUser("");
			this.setCookieBanned("");
			this.setCookieIdurl("LOGIN");
			this.setCookieMail("");
			this.setCookieName("");
			this.setCookieITopic("");
		}
		
	}
	
	//getters internal
	private  Date getTime() {return this.time;}
	private  String getIduser() {return this.iduser;}
	private  String getIdmail() {return this.idmail;}
	private  String getIdname() {return this.idname;}
	private  String getIdban() {return this.idban;}
	private String getIdurl() {return idurl;}
	private String getIdtopic() {return idtopic;}
	
	//cookie id
	public  void setCookieUser(String value)
	{
		Cookies.setCookie( getIduser(), value, getTime());
	}
	
	//cookie mail
	public  void setCookieMail(String value)
	{
		Cookies.setCookie(getIdmail(), value, getTime());
	}
	
	//cookie fullname
	public  void setCookieName(String value)
	{
		Cookies.setCookie(getIdname(), value, getTime());
	}
	
	//cookie state ban
	public  void setCookieBanned(String value)
	{
		Cookies.setCookie(getIdban(), value, getTime());
	}

	//cookieurl
	public void setCookieIdurl(String value) {
		Cookies.setCookie(getIdurl(), value, getTime());
	}

	//cookie topic
	public void setCookieITopic(String value) {
		Cookies.setCookie(getIdtopic(), value, getTime());
	}	
	
	//cookie id
	public  String getCookieUser()
	{
		return Cookies.getCookie( getIduser());
	}
	
	//cookie mail
	public  String getCookieMail()
	{
		return Cookies.getCookie(getIdmail());
	}
	
	//cookie fullname
	public  String getCookieName()
	{
		return Cookies.getCookie(getIdname());
	}
	
	//cookie state ban
	public  String getCookieBanned()
	{
		return Cookies.getCookie(getIdban());
	}

	//cookie topic
	public  String getCookieTopic()
	{
		return Cookies.getCookie(getIdtopic());
	}
	
	//cookieurl
	public String getCookieIdurl() {
		return Cookies.getCookie(getIdurl());
	}
	
	
	//exist cookie created
	public Boolean CookieExist()
	{
		//if all cookies are not empty -> true
		if(!Cookies.getCookie(getIduser()).isEmpty() && !Cookies.getCookie(getIdmail()).isEmpty() && !Cookies.getCookie(getIdname()).isEmpty() && !Cookies.getCookie(getIdban()).isEmpty())
		{
			return true;
		}
		else//if there is any one empty -> false
		{
			delCookiesInfo();
			return false;
		}
			
	}
	
	//delete cookie
	public  void delCookiesInfo()
	{
		this.setCookieUser("");
		this.setCookieBanned("");
		this.setCookieIdurl("LOGIN");
		this.setCookieMail("");
		this.setCookieName("");
	}

	

}
