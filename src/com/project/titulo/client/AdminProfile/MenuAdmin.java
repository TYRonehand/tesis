package com.project.titulo.client.AdminProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.shared.CookieVerify;

public class MenuAdmin extends Composite {

	@UiField
	MenuItem homeBTN;
	@UiField
	MenuItem usersBTN;
	
	// cookies
	private CookieVerify mycookie = new CookieVerify(false);

	private static MenuAdminUiBinder uiBinder = GWT
			.create(MenuAdminUiBinder.class);

	interface MenuAdminUiBinder extends UiBinder<Widget, MenuAdmin> {
	}

	@SuppressWarnings("deprecation")
	public MenuAdmin() {
		initWidget(uiBinder.createAndBindUi(this));
		homeBTN.setCommand(commHome);
		usersBTN.setCommand(commUsers);
	}

	/* EVENT HANDLERS */
	
	/* command event */
	Command commHome = new Command() {
		@Override
		public void execute() {

			// load home
			History.newItem("admindashboard");
		}
	};
	/* command event */
	Command commUsers = new Command() {
		@Override
		public void execute() {

			History.newItem("users");
		}
	};
	
	// click salir
	@UiHandler("exitLink")
	void onExitLinkClick(ClickEvent event) {
		mycookie.delCookiesInfo();
		// load profile
		Window.Location.reload();
	}

}
