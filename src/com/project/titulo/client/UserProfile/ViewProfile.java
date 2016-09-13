package com.project.titulo.client.UserProfile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.model.User;

public class ViewProfile extends Composite {

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
	
	//info from user
	@SuppressWarnings("unused")
	private User UserInfo;
	
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);

	private static ViewProfileUiBinder uiBinder = GWT
			.create(ViewProfileUiBinder.class);

	interface ViewProfileUiBinder extends UiBinder<Widget, ViewProfile> {
	}

	public ViewProfile(String iduser) {
		initWidget(uiBinder.createAndBindUi(this));
		getUser(iduser);
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
		
	}
	
}
