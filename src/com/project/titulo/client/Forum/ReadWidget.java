package com.project.titulo.client.Forum;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.client.UserProfile.UserProfile;
import com.project.titulo.client.UserProfile.ViewProfile;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.Answer;
import com.project.titulo.shared.model.Topic;
import com.project.titulo.shared.model.User;

public class ReadWidget extends Composite {
	private String IDTOPIC = null;//selected topic
	private String IDUSER = null;//current user
	
	//-----------------------------
	@UiField VerticalPanel topicpanel;
	@UiField VerticalPanel commentpanel;
	
	//goto url
	public GoToUrl url = new GoToUrl();

	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	//-----------------------------
	private static ReadWidgetUiBinder uiBinder = GWT
			.create(ReadWidgetUiBinder.class);

	interface ReadWidgetUiBinder extends UiBinder<Widget, ReadWidget> {
	}
	//-----------------------------
	public ReadWidget(String selectedtopic, String currentuser) {
		//save id topic
		this.IDTOPIC = selectedtopic;
		//save current user
		this.IDUSER = currentuser;
		//init
		initWidget(uiBinder.createAndBindUi(this));
		//create topic panel
		DrawTopic(this.IDTOPIC);
		//create comments panels
		DrawComments(this.IDTOPIC);
		
	}
	//-----------------------------
	
	//draw topic!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private void DrawTopic(String idtopic)
	{
		serverService.getTopic(idtopic, new AsyncCallback<Topic>(){

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}
			//find topic
			@Override
			public void onSuccess(Topic mytopic) 
			{
				final Topic auxtopic = mytopic;
				
				serverService.getUserInfo(auxtopic.getIduser(), new AsyncCallback<User>(){

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}
					//find user topic
					@Override
					public void onSuccess(User myuser) 
					{
						final String localiduser = myuser.getId();
						
						//title topic --------------------------
						VerticalPanel hpan = new VerticalPanel();
						hpan.setWidth("100%");
						hpan.setHeight("12px");
						hpan.addStyleName("panel panel-success");
						
						Label titletopic = new Label("TITLE: "+auxtopic.getTitle());
						titletopic.addStyleName("panel-heading");
						hpan.add(titletopic);
						
						//info------------------------------
						FlowPanel hpan1 = new FlowPanel();
						hpan1.setWidth("100%");
						hpan1.addStyleName("infoPanel");
						
						Hyperlink username = new Hyperlink();
						username.setText(myuser.getName());
						username.setTargetHistoryToken("");
						//clic user
						username.addHandler(new ClickHandler(){
					        @Override
					        public void onClick(ClickEvent event) {
					        	RootPanel.get("GWTcontainer").clear();
					        	if(IDUSER.equals(localiduser)){//if is me
					        		RootPanel.get("GWTcontainer").add(new UserProfile(localiduser));
					        	}else{//if is other
					        		RootPanel.get("GWTcontainer").add(new ViewProfile(localiduser));
					        	}
					        }
					    }, ClickEvent.getType());
						
						InlineLabel ocupation = new InlineLabel(myuser.getOcupation());
						InlineLabel creation = new InlineLabel(auxtopic.getCreation());

						hpan1.add(username);
						hpan1.add(ocupation);
						hpan1.add(creation);
						
						//message------------------------------
						Label description = new Label(auxtopic.getDescription());
						description.setHorizontalAlignment(Label.ALIGN_JUSTIFY);
						
						HorizontalPanel hpan2 = new HorizontalPanel();
						hpan2.setWidth("100%");
						hpan2.setHeight("60px");
						hpan2.setHorizontalAlignment(VerticalPanel.ALIGN_JUSTIFY);
						hpan2.addStyleName("messagePanel");
						
						hpan2.add(description);
						
						//options--------------------------------
						HorizontalPanel hpan3 = new HorizontalPanel();
						hpan3.addStyleName("optionPanel");
						
						//if topic is from user this is add
						if(IDUSER.equals(myuser.getId()))
						{
							Button editBtn = new Button("Edit");
							editBtn.addStyleName("commentOption");
							editBtn.addClickHandler(new ClickHandler(){

								@Override
								public void onClick(ClickEvent event) {
									RootPanel.get().add(new EditTopicModal(auxtopic));
								}});
							hpan3.add(editBtn);
							
							/*
							Button deleteBtn = new Button("Delete");
							deleteBtn.addStyleName("commentOption");
							deleteBtn.addClickHandler(new ClickHandler(){

								@Override
								public void onClick(ClickEvent event) {
									Window.alert("User delete");
								}});
							
							hpan3.add(deleteBtn);
							*/		
						}
						Button responseBtn = new Button("Response");
						responseBtn.addStyleName("commentOption");
						responseBtn.addClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent event) {
								RootPanel.get().add(new AddCommentModal(auxtopic.getIdtopic(),IDUSER));
							}});
						hpan3.add(responseBtn);
						
						
						//create topicpanel-------------------------
						VerticalPanel panel = new VerticalPanel();
						panel.setWidth("100%");
						panel.addStyleName("controlPanel");
						
						panel.add(hpan);//title
						panel.add(hpan1);//info
						panel.add(hpan2);//message
						panel.add(hpan3);//options

						//add to forumpanel
						topicpanel.add(panel);
						
					}});
			}});
		
	}
	
	//draw comments!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private void DrawComments(String idtopic)
	{
		serverService.getTopic(idtopic, new AsyncCallback<Topic>(){
			@Override
			public void onFailure(Throwable caught) 
			{
				ErrorVerify.getErrorAlert("offline");
			}
			//find topic
			@Override
			public void onSuccess(Topic mytopic) 
			{
				final Topic auxtopic = mytopic;
				//find comments in topic
				serverService.getComments(mytopic.getIdtopic(), new AsyncCallback<List<Answer>>(){
					@Override
					public void onFailure(Throwable caught) 
					{
						ErrorVerify.getErrorAlert("offline");
					}
					//find Answer
					@Override
					public void onSuccess(List<Answer> aresult) 
					{
						if(aresult.size()>0)
						{
							//clean panel
							commentpanel.clear();

							//go through each comment
							Iterator<Answer> iter = aresult.iterator();
							while (iter.hasNext())
							{
								final Answer auxcomment=iter.next();
								
								//find user from comment
								serverService.getUserInfo(auxcomment.getIduser(), new AsyncCallback<User>(){
									@Override
									public void onFailure(Throwable caught) {
	
										ErrorVerify.getErrorAlert("offline");
									}
	
									@Override
									public void onSuccess(User myuser) 
									{

										final String localiduser = myuser.getId();
										
										//comment panel properties--------------------
										commentpanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
										
										//title topic --------------------------
										VerticalPanel hpan = new VerticalPanel();
										hpan.setWidth("100%");
										hpan.addStyleName("panel panel-info");
										
										Label titletopic = new Label("RE: "+auxtopic.getTitle());
										titletopic.addStyleName("panel-heading");
										hpan.add(titletopic);
										
										//info------------------------------
										FlowPanel hpan1 = new FlowPanel();
										hpan1.setWidth("100%");
										hpan1.addStyleName("infoPanel");
										
										Hyperlink username = new Hyperlink();
										username.setText(myuser.getName());
										username.setTargetHistoryToken("");
										//clic user
										username.addHandler(new ClickHandler(){
									        @Override
									        public void onClick(ClickEvent event) {
									        	RootPanel.get("GWTcontainer").clear();
									        	if(IDUSER.equals(localiduser)){//if is me
									        		RootPanel.get("GWTcontainer").add(new UserProfile(localiduser));
									        	}else{//if is other
									        		RootPanel.get("GWTcontainer").add(new ViewProfile(localiduser));
									        	}
									        }
									    }, ClickEvent.getType());
										
										InlineLabel ocupation = new InlineLabel(myuser.getOcupation());
										InlineLabel creation = new InlineLabel(auxcomment.getCreation());
	
										hpan1.add(username);
										hpan1.add(ocupation);
										hpan1.add(creation);
										
										//message------------------------------------
										HorizontalPanel hpan2 = new HorizontalPanel();
										hpan2.setWidth("100%");
										hpan2.setHeight("60px");
										hpan2.addStyleName("messagePanel");
										
										Label description = new Label(auxcomment.getComentary());
										description.setHorizontalAlignment(Label.ALIGN_JUSTIFY);
										hpan2.add(description);
										
										//options--------------------------------
										VerticalPanel aux = new VerticalPanel();
										aux.setWidth("100%");
										aux.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
										HorizontalPanel hpan3 = new HorizontalPanel();
										hpan3.addStyleName("optionPanel");
	
										//if comment is from user this is add
										if(IDUSER.equals(myuser.getId()))
										{
											Button editBtn = new Button("Edit");
											editBtn.addStyleName("commentOption");
											//clic user
											editBtn.addClickHandler(new ClickHandler(){

												@Override
												public void onClick(ClickEvent event) {
													RootPanel.get().add(new EditResponseModal(auxcomment));
												}});
											hpan3.add(editBtn);
											
											/*
											Button deleteBtn = new Button("Delete");
											deleteBtn.addStyleName("commentOption");
											
											hpan3.add(deleteBtn);
											*/	
										}
										Button responseBtn = new Button("Response");
										responseBtn.addStyleName("commentOption");
										responseBtn.addClickHandler(new ClickHandler(){

											@Override
											public void onClick(ClickEvent event) {
												RootPanel.get().add(new AddCommentModal(auxcomment.getIdtopic(),IDUSER));
											}});
										hpan3.add(responseBtn);
										aux.add(hpan3);
										
										//create topicpanel-------------------------
										VerticalPanel panel = new VerticalPanel();
										panel.setWidth("96%");
										panel.addStyleName("controlPanel");
										
										panel.add(hpan);//title
										panel.add(hpan1);//info
										panel.add(hpan2);//message
										panel.add(aux);//options
	
										//add to forumpanel
										commentpanel.add(panel);
										commentpanel.addStyleName("commentpanel");
	
									}});
							}//end for
						}
						else{
							commentpanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
							commentpanel.add(new Label("No Comments yet"));
						}
					}});
			}});
	}
	
}
