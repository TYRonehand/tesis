package com.project.titulo.client.login;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface LoginResource extends ClientBundle {
	   /**
	   * Sample CssResource.
	   */
	   public interface MyCss extends CssResource {
	      
	      String logologin();
	      String loginscreen();
	      String login();
	      String loginlink();
	      String loginform();
	      
	      String input();
	      String btn();
	      String select();
	      String body();
	      
	      String controlgroup();
	      String apptitle();
	      String menutop();
	      
	   }

	   @Source("login_style.css")
	   MyCss style();
	}