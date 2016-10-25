package com.project.titulo.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.shared.CookieVerify;

public class MenuDropdown extends Composite {


	//cookies
	private CookieVerify mycookie = new CookieVerify(false);
	//control url
	public GoToUrl url = new GoToUrl();
	
	//uibinder
	@UiField Button exitLink;
	@UiField Button profileLink;
	@UiField MenuItem homeBTN;
	@UiField MenuItem filesBTN;
	@UiField MenuItem plotBTN;
	@UiField MenuItem metricBTN;
	@UiField MenuItem communityBTN;
	@UiField MenuItem faqBTN;
	
	
	
	private static MenuDropdownUiBinder uiBinder = GWT
			.create(MenuDropdownUiBinder.class);

	interface MenuDropdownUiBinder extends UiBinder<Widget, MenuDropdown> {
	}

	public MenuDropdown() {
		initWidget(uiBinder.createAndBindUi(this));
		profileLink.setText("User Profile");
		LoadMenuItem();
	}
	
	@SuppressWarnings("deprecation")
	private void LoadMenuItem(){
		homeBTN.setCommand(commHome);
		filesBTN.setCommand(commFile);
		plotBTN.setCommand(commPlot);
		metricBTN.setCommand(commMetric);
		communityBTN.setCommand(commForum);
		faqBTN.setCommand(commFaq);
	}
	
	
	/*command event*/
	Command commHome = new Command() {
		@Override
		public void execute() {

			//load home
			url.GoTo("HOME"); 
		}
	};
	
	Command commFile = new Command() {
		@Override
		public void execute() {
			//load home
			url.GoTo("FILES"); 
		}
	};

	Command commPlot = new Command() {
		@Override
		public void execute() {
			//load home
			url.GoTo("PLOT"); 
		}
	};

	Command commMetric = new Command() {
		@Override
		public void execute() {
			//load home
			url.GoTo("METRIC"); 
		}
	};

	Command commForum = new Command() {
		@Override
		public void execute() {
			//load home
			url.GoTo("FORUM"); 
		}
	};

	Command commFaq = new Command() {
		@Override
		public void execute() {
			//load home
			url.GoTo("FAQ"); 
		}
	};


	
	//click profile
	@UiHandler("profileLink")
	void onProfileLinkClick(ClickEvent event) {
		
		//load profile
		url.GoTo("PROFILE"); 
	}
	//click profile
	@UiHandler("exitLink")
	void onCloseLinkClick(ClickEvent event) {
		
		mycookie.delCookiesInfo();
		//load profile
		url.GoTo("LOGIN"); 
	}

}
