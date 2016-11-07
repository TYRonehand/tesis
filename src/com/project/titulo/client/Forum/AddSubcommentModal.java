package com.project.titulo.client.Forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.SubAnswer;

public class AddSubcommentModal extends DialogBox {

	// control url
	public GoToUrl url = new GoToUrl();

	// elementos uibinder
	@UiField
	TextArea descriptionInput;
	@UiField
	DialogBox dialogBox;
	@UiField
	VerticalPanel dialogVPanel;
	@UiField
	VerticalPanel buttonPanel;
	@UiField
	Button createBtn;
	@UiField
	Button cancelBtn;

	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	// topic full
	private String IDCOMMENT;
	private String IDUSER;

	// widget
	private static AddCommentModalUiBinder uiBinder = GWT
			.create(AddCommentModalUiBinder.class);

	interface AddCommentModalUiBinder extends UiBinder<Widget, AddSubcommentModal> {
	}

	public AddSubcommentModal(String idcomment, String iduser) {
		this.IDCOMMENT = idcomment;
		this.IDUSER = iduser;

		setWidget(uiBinder.createAndBindUi(this));
		this.center();

		LoadModal();
	}

	// properties from modal
	public void LoadModal() {
		// dialogbox
		dialogBox.setText("Response to Comment");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
		dialogBox.addStyleName("panel-body");

		// set style to buttons from bootstrap
		createBtn.addStyleName("btn btn-success");
		cancelBtn.addStyleName("btn btn-danger");

		// VerticalPanel
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		buttonPanel.addStyleName("btn-group btn-group-justified");

		// clear fields
		descriptionInput.setText("");
		descriptionInput.setFocus(true);
		dialogBox.center();
	}

	private void CreateComment() {
		// validar algo escrito
		if (descriptionInput.getText().length() > 3) {
			SubAnswer myanswer = new SubAnswer(descriptionInput.getText(),this.IDCOMMENT, this.IDUSER);
			// call service update
			serverService.addNewSubComment(myanswer, new AsyncCallback<Boolean>() {

				@Override
				public void onFailure(Throwable caught) {
					ErrorVerify.getErrorAlert("offline");
				}

				@Override
				public void onSuccess(Boolean result) {
					if (result) {
						ErrorVerify.getErrorAlert("successadd");
						url.GoTo("TOPIC",IDUSER,null);
						dialogBox.hide();
					} else {
						ErrorVerify.getErrorAlert("failadd");
					}
				}
			});

		} else {
			ErrorVerify.getErrorAlert("empty");
		}

	}

	// click update
	@UiHandler("createBtn")
	void onCreateBtnClick(ClickEvent event) {
		CreateComment();
	}

	// evento cambio valor input
	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) {
		dialogBox.hide();
	}
}
