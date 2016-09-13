package com.project.titulo.client;

import java.util.Collection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.project.titulo.shared.CookieVerify;

//Entry Point
public class Proyectotitulo implements EntryPoint 
{
	//control url
	public GoToUrl url = new GoToUrl();

	public void onModuleLoad() 
	{	
		//pide lista de cookies en el dominio actual
		Collection<String> cok = Cookies.getCookieNames();
		//si existen cookies
		if(cok.size()>=4)
		{
			//cookie
			CookieVerify mycookie = new CookieVerify(false);
			//load menu
			if(!mycookie.getCookieIdurl().equals("ADMIN"))
				url.GoTo("MENU");	
			else
				url.GoTo("MENU2");	
			//last  Widget called
			url.GoTo(mycookie.getCookieIdurl());
		}
		else//if cookies dont exist send to login
		{
			url.GoTo("LOGIN");
		}
	}
	
}
