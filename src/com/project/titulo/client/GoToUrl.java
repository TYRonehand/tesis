package com.project.titulo.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.project.titulo.client.AdminProfile.UsersList;
import com.project.titulo.client.Files.FileWidget;
import com.project.titulo.client.Files.HelpModal;
import com.project.titulo.client.Files.UploadModal;
import com.project.titulo.client.Forum.ForumWidget;
import com.project.titulo.client.Forum.NewTopicModal;
import com.project.titulo.client.Forum.ReadWidget;
import com.project.titulo.client.Metric.MetricWidget;
import com.project.titulo.client.Plot.PlotWidget;
import com.project.titulo.client.UserProfile.UserProfile;
import com.project.titulo.client.breadcrumb.BreadWidget;
import com.project.titulo.client.faq.FAQWidget;
import com.project.titulo.client.home.HomeWidget;
import com.project.titulo.client.login.Login2Widget;
import com.project.titulo.client.menu.MenuDropdown;
import com.project.titulo.client.menu.MenuUser;
import com.project.titulo.client.recovery.RecoveryWidget;
import com.project.titulo.client.register.SignupWidget;
import com.project.titulo.shared.CookieVerify;

public class GoToUrl {
	//cookies
	private CookieVerify mycookie = new CookieVerify(false);
	
	public GoToUrl(){}
	
	public void GoTo(String option)
	{
		String IDUSER=null;
		String IDTOPIC=null;
		
		if(Cookies.getCookieNames().size()>2)
		{
			IDUSER= mycookie.getCookieUser();
			if(option=="TOPIC")
				IDTOPIC=mycookie.getCookieTopic();
		}
		else
		{
			if(option=="REGISTER")  
			{
				option="REGISTER";
			}	
			else if(option=="RECOVERY")
			{
				option="RECOVERY";
			}	
			else
			{
				option="LOGIN";
			}	
		}
		//opciones url
		switch(option){

			 case "LOGIN":
				//set cookie from
				mycookie.setCookieIdurl("LOGIN");
				// widget close session	
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTmenu").add(new Login2Widget());
				RootPanel.get("GWTcontainer").add(new SignupWidget());
				break;
				
			case "MENU":
				// widget close session	
				RootPanel.get("GWTmenu").clear();
				//menu widget
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				break;

			case "MENU2":
				// widget close session	
				RootPanel.get("GWTmenu").clear();
				//menu widget
				RootPanel.get("GWTmenu").add(new MenuUser());
				break;
				
			case "RECOVERY":
				//set cookie from
				mycookie.setCookieIdurl("RECOVERY");
				// widget close session	
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTmenu").add(new RecoveryWidget());
				break;
				
			case "PROFILE":
				//set cookie from
				mycookie.setCookieIdurl("PROFILE");
				//goto profile
				RootPanel.get("GWTcontainer").clear();
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new UserProfile(IDUSER));
				break;

			case "HOME":
				//set cookie from
				mycookie.setCookieIdurl("HOME");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new HomeWidget());
				break;
				
			case "ADMIN":
				//set cookie from
				mycookie.setCookieIdurl("ADMIN");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new UsersList());
				break;		
				
			case "FILES":
				//set cookie from
				mycookie.setCookieIdurl("FILES");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new FileWidget(IDUSER));
				break;
				
			case "PLOT":
				//set cookie from
				mycookie.setCookieIdurl("PLOT");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new PlotWidget(IDUSER));
				break;
				
			case "METRIC":
				//set cookie from
				mycookie.setCookieIdurl("METRIC");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new MetricWidget(IDUSER));
				break;
				
			case "FORUM":
				//set cookie from
				mycookie.setCookieIdurl("FORUM");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new ForumWidget(IDUSER));
				break;
				
			case "FAQ":
				mycookie.setCookieIdurl("FAQ");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new FAQWidget());
				break;
				
			case "TOPIC":
				mycookie.setCookieIdurl("TOPIC");
				// widget close session	
				RootPanel.get("GWTcontainer").clear();	
				//cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new ReadWidget(IDTOPIC,IDUSER));
				break;
				
			case "MODALNEWTOPIC":
				RootPanel.get().add(new NewTopicModal(IDUSER));
				break;
					
			case "MODALUPLOAD":
				RootPanel.get().add(new UploadModal(IDUSER));
				break;
				
			case "MODALHELP":
				RootPanel.get().add(new HelpModal());
				break;
				
			default:
				Window.alert("NO REDIRECTION");
				break;
		}
	}
}
