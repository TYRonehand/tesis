package com.project.titulo.client.Forum;

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
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.GoToUrl;
import com.project.titulo.shared.model.Answer;
import com.project.titulo.shared.model.SubAnswer;
import com.project.titulo.shared.model.Topic;
import com.project.titulo.shared.model.User;

public class ReadWidget extends Composite {

	private String IDTOPIC = null;// selected topic
	private String IDUSER = null;// current user

	private Topic TOPICINFO;
	// -----------------------------
	@UiField
	VerticalPanel topicpanel;
	@UiField
	VerticalPanel commentpanel;

	// goto url
	public GoToUrl url = new GoToUrl();

	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);
	// -----------------------------
	private static ReadWidgetUiBinder uiBinder = GWT
			.create(ReadWidgetUiBinder.class);

	interface ReadWidgetUiBinder extends UiBinder<Widget, ReadWidget> {
	}

	// -----------------------------
	public ReadWidget(String selectedtopic, String currentuser) {
		// save id topic
		this.IDTOPIC = selectedtopic;
		// save current user
		this.IDUSER = currentuser;
		// init
		initWidget(uiBinder.createAndBindUi(this));
		// create topic panel
		DrawTopic(this.IDTOPIC);

	}

	// draw topic!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private void DrawTopic(String idtopic) 
	{
		serverService.getTopic(idtopic, new AsyncCallback<Topic>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}
			
			@Override
			public void onSuccess(Topic mytopic) 
			{
				
				if(mytopic!=null)
				{
					TOPICINFO = new Topic(mytopic.getIdtopic(), mytopic.getTitle(), mytopic.getDescription(), mytopic.getIduser(),
							mytopic.getCreation(), mytopic.getbanned(), mytopic.getEdition(), mytopic.getNumcomments());

					// create comments panels
					DrawComments(TOPICINFO);
					
					serverService.getUserInfo(TOPICINFO.getIduser(), new AsyncCallback<User>() 
					{
						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}
	
						// find user topic
						@Override
						public void onSuccess(User myuser) {
							final String localiduser = myuser.getId();
	
							// title topic --------------------------
							VerticalPanel hpan = new VerticalPanel();
							hpan.setWidth("100%");
							hpan.setHeight("12px");
							hpan.addStyleName("panel panel-success");
							Label titletopic = new Label("TITLE: " + TOPICINFO.getTitle());
							titletopic.addStyleName("panel-heading");
							hpan.add(titletopic);
	
							// info------------------------------
							FlowPanel hpan1 = new FlowPanel();
							hpan1.setWidth("100%");
							hpan1.addStyleName("infoPanel");
	
							Hyperlink username = new Hyperlink();
							username.setText(myuser.getName()+" "+myuser.getLastname());
							// clic user
							if (IDUSER.equals(localiduser)) {
								// if  is  me
								username.setTargetHistoryToken("profile");
							} else {
								// if is other
								username.setTargetHistoryToken("uid="+localiduser);
							}
	
							InlineLabel ocupation = new InlineLabel(myuser.getOcupation());
							InlineLabel creation = new InlineLabel(TOPICINFO.getCreation());
	
							hpan1.add(username);
							hpan1.add(ocupation);
							hpan1.add(creation);
	
							// message------------------------------
							Label description = new Label(TOPICINFO.getDescription());
							description.setHorizontalAlignment(Label.ALIGN_JUSTIFY);
	
							HorizontalPanel hpan2 = new HorizontalPanel();
							hpan2.setWidth("100%");
							hpan2.setHeight("60px");
							hpan2.setHorizontalAlignment(VerticalPanel.ALIGN_JUSTIFY);
							hpan2.addStyleName("messagePanel");
	
							hpan2.add(description);
	
							// options--------------------------------
							HorizontalPanel hpan3 = new HorizontalPanel();
							hpan3.addStyleName("optionPanel");
	
							// if topic is from user this is add
							if (IDUSER.equals(myuser.getId())) 
							{
								if(Integer.parseInt(TOPICINFO.getNumcomments())==0)
								{
									Button editBtn = new Button("Edit");
									editBtn.addStyleName("commentOption");
									editBtn.addClickHandler(new ClickHandler() {
	
										@Override
										public void onClick(ClickEvent event) {
											RootPanel.get().add(new EditTopicModal(TOPICINFO));
										}
									});
									hpan3.add(editBtn);
								}
							}
							//NEW COMMENT
							Button responseBtn = new Button("New Comment");
							responseBtn.addStyleName("commentOption");
							responseBtn.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									RootPanel.get().add(
											new AddCommentModal(TOPICINFO.getIdtopic(), IDUSER));
								}
							});
							hpan3.add(responseBtn);
	
							// create topic panel-------------------------
							VerticalPanel panel = new VerticalPanel();
							panel.setWidth("100%");
							panel.addStyleName("controlPanel");
	
							panel.add(hpan);// title
							panel.add(hpan1);// info
							panel.add(hpan2);// message
							panel.add(hpan3);// options
							
							// add to forumpanel
							topicpanel.add(panel);
						}
					});
				}
			}
		});
	}

	// draw comments!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private void DrawComments(final Topic topicInfo) 
	{
		//external style
		commentpanel.addStyleName("commentpanel");

		// find comments in topic
		serverService.getComments(topicInfo.getIdtopic(), new AsyncCallback<List<Answer>>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			// find Answer
			@Override
			public void onSuccess(List<Answer> answerListResult) 
			{
				// clean panel
				commentpanel.clear();
				// comment panel properties/////////////////////
				commentpanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
				
				if (answerListResult.size() > 0) 
				{
					for(final Answer AuxComment: answerListResult)
					{
						final VerticalPanel FullComment = new VerticalPanel();
						FullComment.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
						FullComment.setWidth("100%");
						// DRAW A USER COMMENT
						serverService.getUserInfo(AuxComment.getIduser(),new AsyncCallback<User>() 
						{
							@Override
							public void onFailure(Throwable caught) {
								ErrorVerify.getErrorAlert("offline");
							}
							@Override
							public void onSuccess(User commentOwner) 
							{
								//DRAW A COMMENT
								FullComment.add(createPanelComment(topicInfo.getTitle(), commentOwner, AuxComment, AuxComment.getIdcomentary()));

							}
						});
						//DRAW SUBCOMMENTS FROM COMMENT
						serverService.getSubComments(AuxComment.getIdcomentary(), new AsyncCallback<List<SubAnswer>>() 
						{
							@Override
							public void onFailure(Throwable caught) {
								ErrorVerify.getErrorAlert("offline");
							}
							// find Answer
							@Override
							public void onSuccess(List<SubAnswer> subcommentListResult) 
							{
								if (subcommentListResult.size() > 0) 
								{
									// go through each comment
									for(final SubAnswer AuxSubComment: subcommentListResult)
									{
										// find user from comment
										serverService.getUserInfo(AuxSubComment.getIduser(),new AsyncCallback<User>() 
										{
											@Override
											public void onFailure(Throwable caught) {

												ErrorVerify.getErrorAlert("offline");
											}

											@Override
											public void onSuccess(User subcommentOwner) {
												// add to forumpanel
												FullComment.add(createPanelSubComment(TOPICINFO.getTitle(), subcommentOwner, AuxSubComment, AuxComment.getIdcomentary()));
											}
										});
									}// end while
								}
							}
							
						});
						
						commentpanel.add( FullComment);
					}// end for
				} else {
					commentpanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
					commentpanel.add(new Label("No Comments yet"));
				}
			}
		});
	
	}

	private VerticalPanel createPanelComment(String Title, final User owner, final Answer comment, final String idcomment)
	{

		// title topic/////////////////////////////////
		VerticalPanel hpan = new VerticalPanel();
		hpan.setWidth("100%");
		hpan.addStyleName("panel panel-info");

		Label titletopic = new Label("RE: "+ Title);
		titletopic.addStyleName("panel-heading");
		hpan.add(titletopic);

		// info///////////////////////////////////////////
		FlowPanel hpan1 = new FlowPanel();
		hpan1.setWidth("100%");
		hpan1.addStyleName("infoPanel");

		Hyperlink username = new Hyperlink();
		username.setText(owner.getName()+" "+owner.getLastname());
		// clic user NAME
		if (IDUSER.equals(owner.getId())) {
			// if is me
			username.setTargetHistoryToken("profile");
		} else {
			// if  is other
			username.setTargetHistoryToken("uid="+owner.getId());
		}

		InlineLabel ocupation = new InlineLabel(owner.getOcupation());
		InlineLabel creation = new InlineLabel(comment.getCreation());

		hpan1.add(username);
		hpan1.add(ocupation);
		hpan1.add(creation);

		// message//////////////////////////////////////
		HorizontalPanel hpan2 = new HorizontalPanel();
		hpan2.setWidth("100%");
		hpan2.setHeight("60px");
		hpan2.addStyleName("messagePanel");

		Label description = new Label(comment.getDescription());
		description.setHorizontalAlignment(Label.ALIGN_JUSTIFY);
		hpan2.add(description);

		// options//////////////////////////////////////
		VerticalPanel aux = new VerticalPanel();
		aux.setWidth("100%");
		aux.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		HorizontalPanel hpan3 = new HorizontalPanel();
		hpan3.addStyleName("optionPanel");

		// if comment is from user and dont  have subcomments this is add
		if (IDUSER.equals(owner.getId())) 
		{
			// clic user
			if(Integer.parseInt(comment.getNumsubcomment())==0)
			{
				Button editBtn = new Button("Edit");
				editBtn.addStyleName("commentOption");
				editBtn.addClickHandler(new ClickHandler() {
	
					@Override
					public void onClick(
							ClickEvent event) {
						RootPanel.get().add(new EditCommentModal(comment));
					}
				});
				hpan3.add(editBtn);
			}
		}
		
		//button for response
		Button responseBtn = new Button("Response");
		responseBtn.addStyleName("commentOption");
		responseBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(
						ClickEvent event) {
					RootPanel.get().add(new AddSubcommentModal(idcomment,IDUSER));
				}
			});
		hpan3.add(responseBtn);
		aux.add(hpan3);

		// create
		// topicpanel-------------------------
		final VerticalPanel panel = new VerticalPanel();
		panel.setWidth("96%");
		panel.addStyleName("controlPanel");

		panel.add(hpan);// title
		panel.add(hpan1);// info
		panel.add(hpan2);// message
		panel.add(aux);// options
		
		return panel;
	}
	
	
	private VerticalPanel createPanelSubComment(String Title, final User owner, final SubAnswer subcomment, final String idcomment)
	{
		final String localiduser = owner.getId();

		// title topic --------------------------
		VerticalPanel hpan = new VerticalPanel();
		hpan.setWidth("100%");
		hpan.addStyleName("panel panel-warning");

		Label titletopic = new Label(" RE: " +Title);
		titletopic.addStyleName("panel-heading");
		hpan.add(titletopic);

		// info------------------------------
		FlowPanel hpan1 = new FlowPanel();
		hpan1.setWidth("100%");
		hpan1.addStyleName("infoPanel");

		Hyperlink username = new Hyperlink();
		username.setText(owner.getName()+" "+owner.getLastname());
		// clic user
		if (IDUSER.equals(localiduser)) {
			// if is me
			username.setTargetHistoryToken("profile");
		} else {
			// if is other
			username.setTargetHistoryToken("uid="+localiduser);
		}

		InlineLabel ocupation = new InlineLabel(owner.getOcupation());
		InlineLabel creation = new InlineLabel(subcomment.getCreation());

		hpan1.add(username);
		hpan1.add(ocupation);
		hpan1.add(creation);

		// message------------------------------------
		HorizontalPanel hpan2 = new HorizontalPanel();
		hpan2.setWidth("100%");
		hpan2.setHeight("60px");
		hpan2.addStyleName("messagePanel");

		Label description = new Label(subcomment.getDescription());
		description.setHorizontalAlignment(Label.ALIGN_JUSTIFY);
		hpan2.add(description);

		// options--------------------------------
		VerticalPanel aux = new VerticalPanel();
		aux.setWidth("100%");
		aux.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		HorizontalPanel hpan3 = new HorizontalPanel();
		hpan3.addStyleName("optionPanel");

		
		Button responseBtn = new Button("Response");
		responseBtn.addStyleName("commentOption");
		responseBtn.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(
							ClickEvent event) {
						RootPanel.get().add(new AddSubcommentModal(idcomment, IDUSER));
					}
				});
		
		hpan3.add(responseBtn);
		aux.add(hpan3);

		// create
		// topicpanel-------------------------
		final VerticalPanel panel = new VerticalPanel();
		panel.setWidth("92%");
		//add to panel
		panel.add(hpan);// title
		panel.add(hpan1);// info
		panel.add(hpan2);// message
		panel.add(aux);// options
		
		return panel;
	}
	
	
}
