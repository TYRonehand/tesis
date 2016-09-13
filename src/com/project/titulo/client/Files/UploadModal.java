package com.project.titulo.client.Files;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.FieldVerifier;
import com.project.titulo.shared.model.UserFile;

public class UploadModal  extends DialogBox  {
	
	private static UploadModalUiBinder uiBinder = GWT.create(UploadModalUiBinder.class);
	
	private String IDUSER=null;
	
	@UiField DialogBox dialogBox; 
	@UiField VerticalPanel dialogVPanel; 
	@UiField VerticalPanel panel1; 
	@UiField VerticalPanel panel2; 
	@UiField VerticalPanel buttonPanel; 
	@UiField TextBox titleInput; 
	@UiField RichTextArea descriptionInput;
	@UiField RichTextArea dataInput;
	@UiField Button nextBtn;
	@UiField Button createBtn;  
	@UiField Button cancelBtn;  
	
	@UiField ListBox dimensionList; 
	@UiField TextBox labelxInput; 
	@UiField TextBox labelyInput; 
	@UiField TextBox labelzInput; 
	//control url
	public GoToUrl url = new GoToUrl();
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);

	interface UploadModalUiBinder extends UiBinder<Widget, UploadModal> {
	}

	public UploadModal(String iduser) {
		this.IDUSER = iduser;
		setWidget(uiBinder.createAndBindUi(this));
		//load properties
		LoadModal();
	}

	private void LoadModal(){

		//list box
		dimensionList.addItem("2D");
		dimensionList.addItem("3D");
		//dialogbox
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
	    dialogBox.setGlassEnabled(true);
		dialogBox.center();
		dialogBox.setText("Create New Data File");
		dialogBox.addStyleName("panel-body");
		
		//VerticalPanel 
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		//style
		buttonPanel.addStyleName("btn-group btn-group-justified");
		
		//set style to buttons from bootstrap
		nextBtn.addStyleName("btn btn-primary");
		createBtn.addStyleName("btn btn-success");
		cancelBtn.addStyleName("btn btn-danger");
		
	}
	
	//show data input
	@UiHandler("nextBtn")
	void onNextBtnClick(ClickEvent event) 
	{
		if(this.titleInput.getText().isEmpty() && this.descriptionInput.getText().isEmpty())
			ErrorVerify.getErrorAlert("empty");
		else{
			panel1.setVisible(false);
			panel2.setVisible(true);
		}
	}
	
	//handler push upload
	@UiHandler("createBtn")
	void onCreateBtnClick(ClickEvent event) 
	{
		if(this.dataInput.getText().isEmpty())
			ErrorVerify.getErrorAlert("empty");
		else{
			//verify format
			if(FieldVerifier.checkDataFormat(this.dataInput.getText()))
			{
				//upload data
				UserFile userfile = new UserFile(	this.titleInput.getText(),
													this.dimensionList.getItemText(this.dimensionList.getSelectedIndex()),
													this.labelxInput.getText(),
													this.labelyInput.getText(),
													this.labelzInput.getText(), 
													this.descriptionInput.getText(), 
													this.IDUSER, 
													this.dataInput.getText());
				
				
				serverService.addNewFile(userfile, new AsyncCallback<Boolean>(){

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result)
						{
							//file added
							dialogBox.hide();
							url.GoTo("FILES");
							ErrorVerify.getErrorAlert("successadd");
						}
						else
						{
							ErrorVerify.getErrorAlert("failadd");
						}
					}});
			}
			else{
				ErrorVerify.getErrorAlert("noletters");
			}
			
			
		}
	}
	
	
	//evento cambio valor  input
	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) 
	{
		dialogBox.hide();
    }
	
	
}