package com.project.titulo.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.shared.CookieVerify;

public class MenuUser extends Composite {

	// cookies
	private CookieVerify mycookie = new CookieVerify(false);
	// control url
	public GoToUrl url = new GoToUrl();

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
		// del all cookies
		mycookie.delCookiesInfo();
		// load login
		url.GoTo("LOGIN");
	}

	// push files button
	@UiHandler("topicsBTN")
	void onButtonClick(ClickEvent event) {

		// load files
		url.GoTo("TOPICS");
	}

	// push home button
	@UiHandler("usersBTN")
	void onHomeBTNClick(ClickEvent event) {

		// load home
		url.GoTo("ADMIN");
	}

}
