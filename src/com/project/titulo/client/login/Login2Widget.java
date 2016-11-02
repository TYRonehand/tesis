package com.project.titulo.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.CookieVerify;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.FieldVerifier;
import com.project.titulo.shared.model.User;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;

public class Login2Widget extends Composite {

	/* style */
	private MyStyle ms = new MyStyle();

	/* variables */
	// elementos uibinder
	@UiField
	TextBox mailInput;
	@UiField
	PasswordTextBox passInput;
	@UiField
	Button submitBTN;

	// cookie
	private CookieVerify mycookie = new CookieVerify(false);
	// url
	private GoToUrl url = new GoToUrl();
	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	// crear widget
	private static Login2WidgetUiBinder uiBinder = GWT
			.create(Login2WidgetUiBinder.class);

	interface Login2WidgetUiBinder extends UiBinder<Widget, Login2Widget> {
	}

	public Login2Widget() {
		initWidget(uiBinder.createAndBindUi(this));

		// set style to buttons from bootstrap
		submitBTN.addStyleName(ms.getButtonStyle(0));
	}

	// limpiar inputs
	private void clearInputs() {
		mailInput.setText("");
		passInput.setText("");
	}

	private void LoginUser() {
		// no existen errores
		if (passInput.getText().length() >= 6
				&& mailInput.getText().length() >= 6) {
			if (FieldVerifier.isValidMail(mailInput.getText())) {
				// admin test
				if (mailInput.getText().equals("Administr@tor")
						&& passInput.getText().equals("741admin963")) {
					// guardamos las cookies con info
					mycookie.setCookieMail("cagutierrez@ing.ucsc.cl");
					mycookie.setCookieUser("Admin");

					// go to home first time
					url.GoTo("MENU2");
					url.GoTo("ADMIN");
				} else {
					// consulta datos usuario normal
					serverService.authenticateUser(mailInput.getText(),passInput.getText(), new AsyncCallback<User>() 
					{
						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
							// limpiar input
							clearInputs();
						}
						@Override
						public void onSuccess(User result) {
							if (result == null) 
							{
								ErrorVerify.getErrorAlert("wronguser");
							} 
							else if (!result.getId().isEmpty()) 
							{
								// guardamos las cookies con info
								mycookie.setCookieMail(result.getMail());
								mycookie.setCookieUser(result.getId());

								// go to home first time
								url.GoTo("MENU");
								url.GoTo("HOME");
							} else {
								clearInputs();
							}
						}
					});
				}
			} else {
				ErrorVerify.getErrorAlert("invalidmail");
			}
		} else {
			ErrorVerify.getErrorAlert("tooshort");
		}

	}

	/* Evento click SUBMIT */
	@UiHandler("submitBTN")
	void onSubmitBTNClick(ClickEvent event) {
		LoginUser();
	}

	@UiHandler("passInput")
	void onPassInputKeyDown(KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
			LoginUser();
	}
}
