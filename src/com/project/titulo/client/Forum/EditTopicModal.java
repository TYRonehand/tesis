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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.Topic;

public class EditTopicModal extends DialogBox {

	// control url
	public GoToUrl url = new GoToUrl();

	// elementos uibinder
	@UiField
	TextBox titleInput;
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
	private Topic updatedTopic;

	// widget
	private static EditTopicModalUiBinder uiBinder = GWT
			.create(EditTopicModalUiBinder.class);

	interface EditTopicModalUiBinder extends UiBinder<Widget, EditTopicModal> {
	}

	public EditTopicModal(Topic mytopic) {
		// save topic to edit
		this.updatedTopic = mytopic;
		// init properties
		setWidget(uiBinder.createAndBindUi(this));
		this.center();
		LoadModal();
	}

	// properties from modal
	public void LoadModal() {
		// dialogbox
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
		dialogBox.setGlassEnabled(true);
		dialogBox.center();
		dialogBox.setText("Edit My Topic");
		dialogBox.addStyleName("panel-body");

		// set style to buttons from bootstrap
		createBtn.addStyleName("btn btn-success");
		cancelBtn.addStyleName("btn btn-danger");

		// VerticalPanel
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		buttonPanel.addStyleName("btn-group btn-group-justified");

		// clear fields
		titleInput.setText(this.updatedTopic.getTitle());
		descriptionInput.setText(this.updatedTopic.getDescription());

		// set focus
		titleInput.setFocus(true);
	}

	private void EditTopic() {
		// validar algo escrito
		if (titleInput.getText().length() > 3 && descriptionInput.getText().length() > 3) {
			// modification date
			// Edit object
			updatedTopic.setTitle(titleInput.getText());
			updatedTopic.setDescription(descriptionInput.getText());
			updatedTopic.setEdition("");

			// call service update
			serverService.setTopic(updatedTopic, new AsyncCallback<Boolean>() {

				@Override
				public void onFailure(Throwable caught) {
					ErrorVerify.getErrorAlert("offline");
				}

				@Override
				public void onSuccess(Boolean result) {
					if (result) {
						ErrorVerify.getErrorAlert("successupdate");
						url.GoTo("TOPIC");
						dialogBox.hide();
					} else {
						ErrorVerify.getErrorAlert("fatal");
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
		EditTopic();
	}

	// evento cambio valor input
	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) {
		dialogBox.hide();
	}

}
