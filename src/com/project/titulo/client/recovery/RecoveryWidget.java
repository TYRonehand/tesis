package com.project.titulo.client.recovery;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.FieldVerifier;
import com.project.titulo.shared.MyStyle;

public class RecoveryWidget extends Composite {

	@UiField
	Hyperlink backLink;
	@UiField
	TextBox mailInput;
	@UiField
	Label labelError1;
	@UiField
	TextBox codeInput;
	@UiField
	Label labelError2;
	@UiField
	PasswordTextBox password1Input;
	@UiField
	Label labelError3;
	@UiField
	PasswordTextBox password2Input;
	@UiField
	Label labelError4;
	@UiField
	VerticalPanel panelRecovery;
	@UiField
	VerticalPanel panelNewCode;
	@UiField
	VerticalPanel panelNewPassword;
	@UiField
	Button submitBTN;
	@UiField
	Button passwordBTN;
	@UiField
	Button codeBTN;

	/* style */
	private MyStyle ms = new MyStyle();
	// RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);

	private static recoveryWidgetUiBinder uiBinder = GWT.create(recoveryWidgetUiBinder.class);

	interface recoveryWidgetUiBinder extends UiBinder<Widget, RecoveryWidget> {
	}

	public RecoveryWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		backLink.setTargetHistoryToken("login");
		// set style to buttons from bootstrap
		submitBTN.addStyleName(ms.getButtonStyle(0));
		passwordBTN.addStyleName(ms.getButtonStyle(0));
		codeBTN.addStyleName(ms.getButtonStyle(0));
	}

	// sen email with code
	private void SendCode() {
		serverService.sendEmailVerify(this.mailInput.getText(), new AsyncCallback<String>() 
		{

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(String result) {
				if (!result.isEmpty())
					ErrorVerify.getErrorAlert(result);

				if (result.equals("goodsendmail")) {
					panelRecovery.setVisible(false);
					panelNewCode.setVisible(true);
				}

			}
		});
	}

	// submit code
	private void ReceiveCode() {
		serverService.userRecovery(this.mailInput.getText(), this.codeInput.getText(), new AsyncCallback<Boolean>() 
		{

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					panelNewCode.setVisible(false);
					panelNewPassword.setVisible(true);
				} else {
					ErrorVerify.getErrorAlert("badcode");
				}
			}
		});
	}

	// change password
	private void ChangePass() {
		serverService.changeUserPassword(this.mailInput.getText(), this.password1Input.getText(), new AsyncCallback<Boolean>() 
		{

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					ErrorVerify.getErrorAlert("goodnewpass");
					History.back();
				} else {
					ErrorVerify.getErrorAlert("fatal");
				}

			}
		});
	}

	/* EVENTS */
	// evento cambio valor input mail
	@UiHandler("mailInput")
	void handleMailInputChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() < 6) {
			labelError1.setText("minimum lenght 6");
			labelError1.setVisible(true);
		} else {
			labelError1.setText("");
			labelError1.setVisible(false);
		}
	}

	// evento cambio valor input code
	@UiHandler("codeInput")
	void handleCodeInputChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() != 6) {
			labelError2.setText("Enter 6 char code!");
			labelError2.setVisible(true);
		} else {
			labelError2.setText("");
			labelError2.setVisible(false);
		}
	}

	// evento cambio valor input new password
	@UiHandler("password1Input")
	void handlePass1InputChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() < 6) {
			labelError3.setText("minimum lenght 6");
			labelError3.setVisible(true);
		} else {
			labelError3.setText("");
			labelError3.setVisible(false);
		}
	}

	// evento cambio valor inputpassword repeat
	@UiHandler("password2Input")
	void handlePass2InputChange(ValueChangeEvent<String> event) {
		if (event.getValue().length() < 6) {
			labelError4.setText("minimum lenght 6");
			labelError4.setVisible(true);
		} else {
			labelError4.setText("");
			labelError4.setVisible(false);
		}
	}

	/* Evento click SEND CODE */
	@UiHandler("submitBTN")
	void onSendCodeBTNClick(ClickEvent event) {
		if (mailInput.getText().length() < 6) {

			labelError1.setText("minimum lenght 6");
			labelError1.setVisible(true);
		} else if (FieldVerifier.isValidMail(mailInput.getText())) {
			this.SendCode();

		} else {
			ErrorVerify.getErrorAlert("invalidmail");
		}

	}

	/* Evento click SUBMIT */
	@UiHandler("codeBTN")
	void onEnterCodeBTNClick(ClickEvent event) {
		if (codeInput.getText().length() != 6) {
			labelError2.setText("Enter 6 character code.");
			labelError2.setVisible(false);

		} else if (FieldVerifier.isValidMail(mailInput.getText())) {
			labelError2.setVisible(false);
			// verify code belongs to the user mail
			this.ReceiveCode();

		} else {
			ErrorVerify.getErrorAlert("invalidmail");
		}

	}

	/* Evento click SUBMIT */
	@UiHandler("passwordBTN")
	void onEnterPasswordBTNClick(ClickEvent event) {
		if (password1Input.getText().length() < 6) {
			Window.alert("pass1 size");
			labelError3.setText("minimum lenght 6");
			labelError3.setVisible(true);
		} else if (password2Input.getText().length() < 6) {
			Window.alert("pass2 size");
			labelError4.setText("minimum lenght 6");
			labelError4.setVisible(true);
		} else if (!password1Input.getText().equals(password2Input.getText())) {
			Window.alert("Passwords are not equals");
			labelError4.setText("Passwords are not equals");
			labelError4.setVisible(true);
		} else if (password1Input.getText().equals(password2Input.getText())) {
			this.ChangePass();
		} else {
			ErrorVerify.getErrorAlert("invalidmail");
		}

	}

}
