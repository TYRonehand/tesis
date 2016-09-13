package com.project.titulo.client.Files;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HelpModal  extends DialogBox  {

	@UiField DialogBox dialogBox; 
	@UiField VerticalPanel dialogVPanel; 

	private static HelpModalUiBinder uiBinder = GWT.create(HelpModalUiBinder.class);

	interface HelpModalUiBinder extends UiBinder<Widget, HelpModal> {
	}

	public HelpModal() {
		setWidget(uiBinder.createAndBindUi(this));
		LoadModal();
	}

	public void LoadModal(){
		
		//dialogbox
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
	    dialogBox.setGlassEnabled(true);
		dialogBox.center();
		dialogBox.setText("File Format");
		
		
		//VerticalPanel 
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
	}
}
