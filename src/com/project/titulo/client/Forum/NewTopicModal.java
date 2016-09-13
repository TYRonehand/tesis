package com.project.titulo.client.Forum;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.Topic;

public class NewTopicModal  extends DialogBox {

	private String IDUSER=null;
	
	//goto url
	public GoToUrl url = new GoToUrl();
	
	
	//elementos uibinder
	@UiField TextBox titleInput; 
	@UiField RichTextArea descriptionInput; 
	@UiField DialogBox dialogBox; 
	@UiField VerticalPanel dialogVPanel; 
	@UiField VerticalPanel buttonPanel; 
	@UiField Button createBtn;
	@UiField Button cancelBtn;
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	private static NewTopicModalUiBinder uiBinder = GWT
			.create(NewTopicModalUiBinder.class);

	interface NewTopicModalUiBinder extends UiBinder<Widget, NewTopicModal> {
	}

	public NewTopicModal(String iduser) {
		//save id user
		this.IDUSER = iduser;
		
		setWidget(uiBinder.createAndBindUi(this));
		
		//load properties
		LoadModal();
	}

	public void LoadModal(){

		//dialogbox
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
	    dialogBox.setGlassEnabled(true);
		dialogBox.center();
		dialogBox.setText("Create new topic");
		dialogBox.addStyleName("panel-body");

		//set style to buttons from bootstrap
		createBtn.addStyleName("btn btn-success");
		cancelBtn.addStyleName("btn btn-danger");
		
		//VerticalPanel 
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		buttonPanel.addStyleName("btn-group btn-group-justified");
		
		//clear fields
		titleInput.setText("");
		descriptionInput.setText("");
		
		//set focus
		titleInput.setFocus(true);
	}
	
	//evento cambio valor  input
	@UiHandler("createBtn")
	void onCreateBtnClick(ClickEvent event) 
	{
		//validar algo escrito
		if(titleInput.getText().length()>3 && descriptionInput.getText().length()>20)
		{
			
				//create object
				Topic myTopic = new Topic(titleInput.getText(), descriptionInput.getText(), this.IDUSER);
				//call service
				serverService.addNewTopic(myTopic, new AsyncCallback<Boolean>(){
		
					@Override
					public void onFailure(Throwable caught) 
					{
						ErrorVerify.getErrorAlert("offline");
					}
					
					@Override
					public void onSuccess(Boolean result) 
					{
						if(result){

							ErrorVerify.getErrorAlert("successadd");
							dialogBox.setVisible(false);
						}else{
							ErrorVerify.getErrorAlert("failadd");
						}
					}
				});
			
		}
		else
		{
			ErrorVerify.getErrorAlert("empty");
		}
		
    }
	
	//evento cambio valor  input
	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) 
	{
		dialogBox.hide();
    }
	
}
