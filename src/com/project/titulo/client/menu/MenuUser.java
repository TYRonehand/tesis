package com.project.titulo.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.shared.CookieVerify;

public class MenuUser extends Composite {

	// cookies
	private CookieVerify mycookie = new CookieVerify(false);

	private static MenuUserUiBinder uiBinder = GWT
			.create(MenuUserUiBinder.class);

	interface MenuUserUiBinder extends UiBinder<Widget, MenuUser> {
	}

	public MenuUser() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	/* EVENT HANDLERS */

	// click salir
	@UiHandler("exitLink")
	void onExitLinkClick(ClickEvent event) {
		mycookie.delCookiesInfo();
		// load profile
		Window.Location.reload();
	}


	// push home button
	@UiHandler("usersBTN")
	void onHomeBTNClick(ClickEvent event) {
		History.newItem("admin");
	}

}
