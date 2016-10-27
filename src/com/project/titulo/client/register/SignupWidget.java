package com.project.titulo.client.register;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.DataOptional;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.FieldVerifier;
import com.project.titulo.shared.model.User;
import com.google.gwt.event.dom.client.KeyDownEvent;

public class SignupWidget extends Composite {

	/*style*/
	private MyStyle ms = new MyStyle();
	
	/*variables*/
	//goto url
	public GoToUrl url = new GoToUrl();
		
	@UiField  Label labelError1;
	@UiField  Label labelError2;
	@UiField  Label labelError3;
	@UiField  Label labelError4;
	@UiField  Label labelError5;
	@UiField Hyperlink recoveryLink;
	@UiField  Button registerBtn;
	@UiField  TextBox mailInput;
	@UiField  TextBox nameInput;
	@UiField  TextBox lastnameInput;
	@UiField  TextBox passInput;
	@UiField  TextBox pass2Input;
	@UiField ListBox  countryBox;
	
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	//widget
	private static SignupWidgetUiBinder uiBinder = GWT
			.create(SignupWidgetUiBinder.class);

	interface SignupWidgetUiBinder extends UiBinder<Widget, SignupWidget> {
	}

	public SignupWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//set style to buttons from bootstrap
		registerBtn.addStyleName(ms.getButtonStyle(0));
		//set styles for css
		
		//load country to combobox
		addCountry();
	}

	//add country to combobox
	private void addCountry(){
		String[] countryList = DataOptional.getCountries(); 
		for(String country : countryList){			
			countryBox.addItem(country.substring(3));
		}
		countryBox.setSelectedIndex(42);
	}

	//register user
	private void registerUser(User newUser)
	{
		//send object
		serverService.addUserInfo(newUser, new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					ErrorVerify.getErrorAlert("successadd");
					url.GoTo("LOGIN");
				}else{
					ErrorVerify.getErrorAlert("failadd");
				}
			}
		});//end inner service
	}
	
	//send user info to server
	private void SignUp(){
		if(passInput.getText().equals(pass2Input.getText()) && FieldVerifier.isValidMail(mailInput.getText()) && FieldVerifier.isValidName(nameInput.getText()) && FieldVerifier.isValidName(lastnameInput.getText()))
		{
			serverService.userExist(mailInput.getText(), new AsyncCallback<Boolean>(){
				@Override
				public void onFailure(Throwable caught) {
					ErrorVerify.getErrorAlert("mailexist");
				}
				@Override
				public void onSuccess(Boolean result){
					//if mail dont exist
					if(!result)	{
						//save data in object
						User newUser = new User( 
				 			"",
				 			mailInput.getText(), 
				 			nameInput.getText(), 
				 			lastnameInput.getText(), 
				 			countryBox.getItemText(countryBox.getSelectedIndex()), 
				 			"", 
				 			"", 
				 			"",
				 			pass2Input.getText(),
				 			null,
				 			"",
				 			null,
				 			""
						);
						//call reguster user
						registerUser(newUser);
					}else{
						ErrorVerify.getErrorAlert("userexist");
					}
				}
			});//end service
		}else{
			Window.alert("Please check fields before submit");
		}
	}
	
	//evento size name
	@UiHandler("nameInput")
    void handleNameChange(ValueChangeEvent<String> event){
		if(FieldVerifier.isValidName(event.getValue()) && event.getValue().length() >= 3){
	          labelError1.setText("");
	    	  labelError1.setVisible(false);
		}else{	
			if (event.getValue().length() < 3){
	    	  labelError1.setText("Minimum lenght 3");
	    	  labelError1.setVisible(true);
			} 
			if(!FieldVerifier.isValidName(event.getValue())){
				labelError1.setText("Use only letters");
				labelError1.setVisible(true);
			}
		}
   }
	//evento size lastname
	@UiHandler("lastnameInput")
    void handleLastnameChange(ValueChangeEvent<String> event){
		if(FieldVerifier.isValidName(event.getValue()) && event.getValue().length() >= 3){
			labelError2.setText("");
			labelError2.setVisible(false);
		}else{	
			if (event.getValue().length() < 3){
				labelError2.setText("Minimum lenght 3");
				labelError2.setVisible(true);
			} 
			if(!FieldVerifier.isValidName(event.getValue())){
				labelError2.setText("Use only letters");
				labelError2.setVisible(true);
			}
		}
	}
	//evento size lastname
	@UiHandler("mailInput")
    void handleMailChange(ValueChangeEvent<String> event) 
	{
		if(FieldVerifier.isValidMail(event.getValue()) && event.getValue().length() >= 6)
		{
	        labelError3.setText("");
	    	labelError3.setVisible(false);
		}else{
			if (event.getValue().length() < 6) 
			{
		    	labelError3.setText("Minimum lenght 6");
		    	labelError3.setVisible(true);
		    } 
			if(!FieldVerifier.isValidMail(event.getValue()))
			{
				labelError3.setText("Invalid email. example: name@company.com");
				labelError3.setVisible(true);
			}
			
		}
    }

	//evento size lastname
	@UiHandler("passInput")
    void handlePassChange(ValueChangeEvent<String> event)
	{
		if(FieldVerifier.isValidPass(event.getValue()) && event.getValue().length() >= 6)
		{
	        labelError4.setText("");
	    	labelError4.setVisible(false);
			pass2Input.setEnabled(true);
		}else{
			if (event.getValue().length() < 6) 
			{
		    	labelError4.setText("Minimum lenght 6");
		    	labelError4.setVisible(true);
				pass2Input.setText("");
				pass2Input.setEnabled(false);
		    }
			if(!FieldVerifier.isValidPass(event.getValue()))
			{
				labelError4.setText("Use letters and numbers");
				labelError4.setVisible(true);
				pass2Input.setText("");
				pass2Input.setEnabled(false);
			}
			
		}
    }
	
	//evento size lastname
	@UiHandler("pass2Input")
    void handlePass2Change(ValueChangeEvent<String> event)
	{
		if(!passInput.getText().equals(event.getValue()))
		{
			labelError5.setText("Passwords must be equals!");
			labelError5.setVisible(true);
		}else{
	        labelError5.setText("");
	    	labelError5.setVisible(false);
	    }
    }
	
	//submit register
	@UiHandler("registerBtn")
	void onRegisterBtnClick(ClickEvent event){
		SignUp();
	}
		
	//click recuperar link
	@UiHandler("recoveryLink")
	void onRecoveryLinkClick(ClickEvent event){
		url.GoTo("RECOVERY");
	}
	
	@UiHandler("pass2Input")
	void onPass2InputKeyDown(KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
			SignUp();
	}
}
