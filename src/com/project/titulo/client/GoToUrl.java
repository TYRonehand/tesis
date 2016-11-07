package com.project.titulo.client;

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
import com.project.titulo.client.UserProfile.ViewProfile;
import com.project.titulo.client.breadcrumb.BreadWidget;
import com.project.titulo.client.faq.FAQWidget;
import com.project.titulo.client.home.HomeWidget;
import com.project.titulo.client.login.Login2Widget;
import com.project.titulo.client.menu.MenuDropdown;
import com.project.titulo.client.menu.MenuUser;
import com.project.titulo.client.notfound.NotfoundWidget;
import com.project.titulo.client.recovery.RecoveryWidget;
import com.project.titulo.client.register.SignupWidget;

public class GoToUrl {

	public GoToUrl() {
	}

	public void GoTo(String option, String IDUSER, String IDTOPIC) {

		//Window.alert("Option: "+option);
		// opciones url
		switch (option.toUpperCase()) 
		{
			case "LOGIN":
				// widget close session
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTmenu").add(new Login2Widget());
				RootPanel.get("GWTcontainer").add(new SignupWidget());
				break;
	
			case "MENU":
				// widget close session
				RootPanel.get("GWTmenu").clear();
				// menu widget
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				break;
	
			case "MENU2":
				// widget close session
				RootPanel.get("GWTmenu").clear();
				// menu widget
				RootPanel.get("GWTmenu").add(new MenuUser());
				break;
	
			case "RECOVERY":
				// widget close session
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTmenu").add(new RecoveryWidget());
				break;
	
			case "USER":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// goto profile
				RootPanel.get("GWTcontainer").clear();
				RootPanel.get("GWTcontainer").add(new BreadWidget("PROFILE"));
				RootPanel.get("GWTcontainer").add(new ViewProfile(IDUSER));
				break;
				
			case "PROFILE":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// goto profile
				RootPanel.get("GWTcontainer").clear();
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new UserProfile(IDUSER));
				break;
	
			case "HOME":
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				RootPanel.get("GWTmenu").clear();
				// menu widget
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new HomeWidget());
				break;
	
			case "ADMIN":
				// widget close session
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuUser());
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new UsersList());
				break;
	
			case "FILES":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new FileWidget(IDUSER));
				break;
	
			case "PLOT":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new PlotWidget(IDUSER));
				break;
	
			case "METRIC":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new MetricWidget(IDUSER));
				break;
	
			case "FORUM":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new ForumWidget(IDUSER));
				break;
	
			case "FAQ":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new FAQWidget());
				break;
	
			case "TOPIC":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTmenu").add(new MenuDropdown());
				// widget close session
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new BreadWidget(option));
				RootPanel.get("GWTcontainer").add(new ReadWidget(IDTOPIC, IDUSER));
				break;
				
			case "404":
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new NotfoundWidget());
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
				// menu user
				RootPanel.get("GWTmenu").clear();
				RootPanel.get("GWTcontainer").clear();
				// cualquier otro caso sera enviado al login
				RootPanel.get("GWTcontainer").add(new NotfoundWidget());
				break;
		}
	}
}
