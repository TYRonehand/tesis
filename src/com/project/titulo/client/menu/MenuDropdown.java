package com.project.titulo.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class MenuDropdown extends Composite {


	// uibinder
	@UiField
	Button exitLink;
	@UiField
	Button profileLink;
	@UiField
	MenuItem homeBTN;
	@UiField
	MenuItem filesBTN;
	@UiField
	MenuItem plotBTN;
	@UiField
	MenuItem metricBTN;
	@UiField
	MenuItem communityBTN;
	@UiField
	MenuItem faqBTN;

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
	private void LoadMenuItem() {
		homeBTN.setCommand(commHome);
		filesBTN.setCommand(commFile);
		plotBTN.setCommand(commPlot);
		metricBTN.setCommand(commMetric);
		communityBTN.setCommand(commForum);
		faqBTN.setCommand(commFaq);
	}

	/* command event */
	Command commHome = new Command() {
		@Override
		public void execute() {

			// load home
			History.newItem("home");
		}
	};

	Command commFile = new Command() {
		@Override
		public void execute() {
			// load files
			History.newItem("files");
		}
	};

	Command commPlot = new Command() {
		@Override
		public void execute() {
			// load plot
			History.newItem("plot");
		}
	};

	Command commMetric = new Command() {
		@Override
		public void execute() {
			// load metric
			History.newItem("metric");
		}
	};

	Command commForum = new Command() {
		@Override
		public void execute() {
			// load forum
			History.newItem("forum");
		}
	};

	Command commFaq = new Command() {
		@Override
		public void execute() {
			// load faq
			History.newItem("faq");
		}
	};

	// click profile
	@UiHandler("profileLink")
	void onProfileLinkClick(ClickEvent event) {

		// load profile
		History.newItem("profile");
	}

	// click profile
	@UiHandler("exitLink")
	void onCloseLinkClick(ClickEvent event) {
		// load profile
		Window.Location.reload();
	}

}
