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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.Answer;

public class AddCommentModal  extends DialogBox {

	//control url
	public GoToUrl url = new GoToUrl();
	
	//elementos uibinder
	@UiField RichTextArea descriptionInput; 
	@UiField DialogBox dialogBox; 
	@UiField VerticalPanel dialogVPanel; 
	@UiField VerticalPanel buttonPanel; 
	@UiField Button createBtn;
	@UiField Button cancelBtn;

	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	//topic full
	private String idtopic;
	private String iduser;
	
	//widget
	private static AddCommentModalUiBinder uiBinder = GWT
			.create(AddCommentModalUiBinder.class);

	interface AddCommentModalUiBinder extends UiBinder<Widget, AddCommentModal> {
	}

	public AddCommentModal(String idtopic, String iduser) {
		this.idtopic=idtopic;
		this.iduser=iduser;
		setWidget(uiBinder.createAndBindUi(this));
		LoadModal();
	}



	//properties from modal
	public void LoadModal(){
		//dialogbox
		dialogBox.setText("Create a Response");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
		dialogBox.addStyleName("panel-body");

		//set style to buttons from bootstrap
		createBtn.addStyleName("btn btn-success");
		cancelBtn.addStyleName("btn btn-danger");
		
		//VerticalPanel 
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		buttonPanel.addStyleName("btn-group btn-group-justified");
		
		//clear fields
		descriptionInput.setText("");
		descriptionInput.setFocus(true);
		dialogBox.center();
	}
	
	//click update
	@UiHandler("createBtn")
	void onCreateBtnClick(ClickEvent event) 
	{
		//validar algo escrito
		if(descriptionInput.getText().length()>20)
		{
			Answer myanswer = new Answer(descriptionInput.getText(), this.idtopic, this.iduser);
			//call service update
			serverService.addNewComment(myanswer, new AsyncCallback<Boolean>(){

				@Override
				public void onFailure(Throwable caught) {
					ErrorVerify.getErrorAlert("offline");
				}

				@Override
				public void onSuccess(Boolean result) {
					if(result){
						ErrorVerify.getErrorAlert("successadd");
						url.GoTo("TOPIC");
						dialogBox.hide();
					}else{
						ErrorVerify.getErrorAlert("failadd");
					}
				}});
		
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
