package com.project.titulo.client.UserProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.DataOptional;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.User;

public class UserProfile extends Composite {

	//principal labels
	@UiField HTML pNameLabel;
	@UiField HTML pMailLabel;
	@UiField HTML pUniversityLabel;
	@UiField HTML pCountryLabel;
	//secondary Labels
	@UiField HTML nameLabel;
	@UiField HTML lastnameLabel;
	@UiField HTML countryLabel;
	@UiField HTML ocupationLabel;
	@UiField HTML webLabel;
	//inputs 
	@UiField TextBox nameInput;
	@UiField Label labelError1;
	@UiField TextBox lastnameInput;
	@UiField Label labelError2;
	@UiField ListBox countryBox;
	@UiField TextBox ocupationInput;
	@UiField TextBox webInput;
	//butons
	@UiField Button submitBTN;
	@UiField Button cancelBTN;
	
	//info from user
	private User UserInfo;
	
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	private static UserProfileUiBinder uiBinder = GWT.create(UserProfileUiBinder.class);

	interface UserProfileUiBinder extends UiBinder<Widget, UserProfile> {
	}

	public UserProfile(String iduser) {
		initWidget(uiBinder.createAndBindUi(this));
		getUser(iduser);
		addCountry();
	}

	//add country to combobox
	private void addCountry(){
		String[] countryList = DataOptional.getCountries(); 
		for(String country : countryList)
		{
			countryBox.addItem(country.substring(3));
		}
	}
	
	//load all data from user
	private void getUser(String user) {
		serverService.getUserInfo(user, new AsyncCallback<User>(){

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(User result) {
				LoadDataView(result);
			}});
	}
	//load data into the view
	private void LoadDataView(User myuser)
	{
		//save user info
		this.UserInfo = myuser;
		
		// primary info label
		if(myuser.getName()!=null && myuser.getLastname()!=null)
			this.pNameLabel.setHTML("<p>"+myuser.getName()+" "+myuser.getLastname()+"</p>");
		if(myuser.getMail()!=null)
			this.pMailLabel.setHTML("<p>"+myuser.getMail()+"</p>");
		if(myuser.getUniversity()!=null)
			this.pUniversityLabel.setHTML("<p>"+myuser.getUniversity()+"</p>");
		if(myuser.getCountry()!=null)
			this.pCountryLabel.setHTML("<p>"+myuser.getCountry()+"</p>");
		
		// secondary info label
		if(myuser.getName()!=null)
			this.nameLabel.setHTML("<div class='bio-row'><p><span>Name </span>: "+myuser.getName()+"</p></div>");
		else
			this.nameLabel.setHTML("<div class='bio-row'><p><span>Name </span>: - </p></div>");
		if(myuser.getLastname()!=null)
			this.lastnameLabel.setHTML("<div class='bio-row'><p><span>Lastname </span>: "+myuser.getLastname()+"</p></div>");
		else	
			this.lastnameLabel.setHTML("<div class='bio-row'><p><span>Lastname </span>: - </p></div>");
		if(myuser.getCountry()!=null)
			this.countryLabel.setHTML("<div class='bio-row'><p><span>Country </span>: "+myuser.getCountry()+"</p></div>");
		else
			this.countryLabel.setHTML("<div class='bio-row'><p><span>Country </span>: - </p></div>");
		if(myuser.getOcupation()!=null)
			this.ocupationLabel.setHTML("<div class='bio-row'><p><span>Ocupation </span>: "+myuser.getOcupation()+"</p></div>");
		else
			this.ocupationLabel.setHTML("<div class='bio-row'><p><span>Ocupation </span>: - </p></div>");
		if(myuser.getWeb()!=null)
			this.webLabel.setHTML("<div class='bio-row'><p><span>Web </span>: <a href='"+myuser.getWeb()+"'>My site</a>");
		else
			this.webLabel.setHTML("<div class='bio-row'><p><span>Web </span>: - </p></div>");
		
		//info inputs
		this.nameInput.setValue(myuser.getName());
		this.lastnameInput.setValue(myuser.getLastname());
		
		
		
		//this.countryInput.setValue(myuser.getCountry());
		int index=0;
		for(int i=0;i<this.countryBox.getItemCount();i++)
		{
			if(this.countryBox.getItemText(i).equals(myuser.getCountry())){
				index=i;
				break;
			}
		}
		this.countryBox.setItemSelected(index, true);
		
		
		this.ocupationInput.setValue(myuser.getOcupation());
		this.webInput.setValue(myuser.getWeb());
	}
	
	private void setUserInfo()
	{
		if(nameInput.getValue().length()>=3 && lastnameInput.getValue().length()>=3)
		{
			User aux = new User( 
					UserInfo.getId() ,
					UserInfo.getMail() , 
					UserInfo.getName() , 
					UserInfo.getLastname() , 
					UserInfo.getCountry() , 
					UserInfo.getOcupation() , 
					UserInfo.getWeb() , 
					UserInfo.getUniversity(),
		 			"",
		 			UserInfo.getCreation(),
		 			UserInfo.getRegistered(),
		 			UserInfo.getBanned(),
		 			""
		 		  );
			final String auxid= aux.getId();
			
			aux.setName(this.nameInput.getValue());
			aux.setLastname(this.lastnameInput.getValue());
			//combo box
			aux.setCountry(countryBox.getItemText(countryBox.getSelectedIndex()));
			aux.setOcupation(this.ocupationInput.getValue());
			aux.setWeb(this.webInput.getValue());
			//connect to servlet and send user
			serverService.setUserInfo(aux, new AsyncCallback<Boolean>(){

				@Override
				public void onFailure(Throwable caught) {
					ErrorVerify.getErrorAlert("offline");
					LoadDataView(UserInfo);
				}

				@Override
				public void onSuccess(Boolean result) {
					if(result)
					{
						ErrorVerify.getErrorAlert("successupdate");
						RootPanel.get("GWTcontainer").clear();
						RootPanel.get("GWTcontainer").add(new UserProfile(auxid));
					}else{
						ErrorVerify.getErrorAlert("offline");
						LoadDataView(UserInfo);
					}
					
				}});
		}
		else{
			Window.alert("Name or Lastname are invalid!");
		}
	}
	
	/*EVENTS and handlers--------------------------------*/
	
	//evento cambio valor  input
	@UiHandler("nameInput")
    void handleNameInputChange(ValueChangeEvent<String> event) 
	{
      if (event.getValue().length() < 3) {
    	  labelError1.setText("minimum lenght 3");
    	  labelError1.setVisible(true);
      } else {
    	  labelError1.setVisible(false);
      }
    }
	
	//evento cambio valor  input
	@UiHandler("lastnameInput")
    void handleLastnameInputChange(ValueChangeEvent<String> event) 
	{
      if (event.getValue().length() < 3) {
    	  labelError2.setText("minimum lenght 3");
    	  labelError2.setVisible(true);
      } else {
    	  labelError2.setVisible(false);
      }
    }
	
	//click registro link
	@UiHandler("submitBTN")
	void onSubmitBTNClick(ClickEvent event) 
	{
		//update data from user
		setUserInfo();
	}
	
	//click registro link
	@UiHandler("cancelBTN")
	void onCancelBTNClick(ClickEvent event) 
	{
		//reload data user
		LoadDataView(this.UserInfo);
		
	}
	
}
