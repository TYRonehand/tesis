package com.project.titulo.client;

import java.util.Collection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.project.titulo.shared.CookieVerify;

//Entry Point
public class Proyectotitulo implements EntryPoint {
	// control url
	public GoToUrl url = new GoToUrl();
	
	public void onModuleLoad() 
	{
		if(History.getToken().isEmpty() || History.getToken()==null)
			History.newItem("LOGIN");
		
		History.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if(!History.getToken().isEmpty())
					url.GoTo(History.getToken());
			}});
		
		// search cookies in the domain
		Collection<String> cok = Cookies.getCookieNames();
		// si existen cookies
		if (cok.size() >= 3) 
		{
			// cookie
			CookieVerify mycookie = new CookieVerify(false);
			// load menu
			if (mycookie.getCookieIdurl().equals("ADMIN"))
				url.GoTo("MENU2");
			else
				url.GoTo("MENU");
			
			// last Widget called
			url.GoTo(mycookie.getCookieIdurl());
		} else// if cookies dont exist send to login
		{
			History.back();
		}
	}

}
