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

public class EditResponseModal extends DialogBox {

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
	private Answer updatedAnswer;
	
	//widget
	private static EditResponseModalUiBinder uiBinder = GWT
			.create(EditResponseModalUiBinder.class);

	interface EditResponseModalUiBinder extends
			UiBinder<Widget, EditResponseModal> {
	}

	public EditResponseModal(Answer mycomment) 
	{
		//save comment to edit
		this.updatedAnswer=mycomment;
		//init properties
		setWidget(uiBinder.createAndBindUi(this));
		LoadModal();
	}


	//properties from modal
	public void LoadModal(){
		//dialogbox
		dialogBox.setText("Edit My Response");
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
		descriptionInput.setText(this.updatedAnswer.getComentary());
		descriptionInput.setFocus(true);
		dialogBox.center();
	}
	
	private void EditComment()
	{
		//validar algo escrito
		if(descriptionInput.getText().length()>20)
		{
			//Edit object
			updatedAnswer.setComentary(descriptionInput.getText());
			
			//call service update
			serverService.setComment(updatedAnswer, new AsyncCallback<Boolean>(){

				@Override
				public void onFailure(Throwable caught) 
				{
					ErrorVerify.getErrorAlert("offline");
				}
				
				@Override
				public void onSuccess(Boolean result) 
				{
					if(result){
						ErrorVerify.getErrorAlert("successupdate");
						url.GoTo("TOPIC");
						dialogBox.hide();
					}else{
						ErrorVerify.getErrorAlert("fatal");
					}
				}
			});
		
		}
		else
		{
			ErrorVerify.getErrorAlert("empty");
		}
	}
	
	//click update
	@UiHandler("createBtn")
	void onCreateBtnClick(ClickEvent event) 
	{
		EditComment();
    }
	
	//evento cambio valor  input
	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) 
	{
		dialogBox.hide();
    }
}
