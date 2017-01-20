package com.project.titulo.client.AdminProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.CookieVerify;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.MyStyle;
import com.project.titulo.shared.model.User;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;

public class AdminLogin extends Composite {
	@UiField Button submitBtn;
	@UiField TextBox userBox;
	@UiField PasswordTextBox passBox;
	
	/* style */
	private MyStyle ms = new MyStyle();	
	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	private static AdminLoginUiBinder uiBinder = GWT
			.create(AdminLoginUiBinder.class);

	interface AdminLoginUiBinder extends UiBinder<Widget, AdminLogin> {
	}

	public AdminLogin() {
		initWidget(uiBinder.createAndBindUi(this));
		// set style to buttons from bootstrap
		submitBtn.addStyleName(ms.getButtonStyle(0));
	}

	private void adminLogin(String mail,String password){
		serverService.authenticateAdmin(mail, password, new AsyncCallback<User>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(User result) {
				if(result!=null)
				{
					CookieVerify cok = new CookieVerify(false);
					cok.setCookieIdurl("admindashboard");
					cok.setCookieUser(result.getId());
					History.newItem("admindashboard");
				}else{
					ErrorVerify.getErrorAlert("fatal");
					}
			}});
		
	}
	
	@UiHandler("submitBtn")
	void onSubmitBtnClick(ClickEvent event) {
		if(!this.userBox.getText().isEmpty() && !this.passBox.getText().isEmpty())
			adminLogin(this.userBox.getText(),this.passBox.getText());
		else
			ErrorVerify.getErrorAlert("empty");
	}
	
	@UiHandler("passBox")
	void onPassInputKeyDown(KeyDownEvent event) {

		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			if(!this.userBox.getText().isEmpty() && !this.passBox.getText().isEmpty())
				adminLogin(this.userBox.getText(),this.passBox.getText());
			else
				ErrorVerify.getErrorAlert("empty");
		}
	}
	
}
