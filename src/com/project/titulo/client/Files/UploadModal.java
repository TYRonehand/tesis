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
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.FieldVerifier;
import com.project.titulo.shared.GoToUrl;
import com.project.titulo.shared.MyStyle;
import com.project.titulo.shared.model.UserFile;

public class UploadModal extends DialogBox {

	private static UploadModalUiBinder uiBinder = GWT
			.create(UploadModalUiBinder.class);

	private String IDUSER = null;

	@UiField
	DialogBox dialogBox;
	@UiField
	VerticalPanel dialogVPanel;
	@UiField
	VerticalPanel panel1;
	@UiField
	VerticalPanel panel2;
	@UiField
	VerticalPanel buttonPanel;
	@UiField
	VerticalPanel labelxPanel;
	@UiField
	VerticalPanel labelyPanel;
	@UiField
	VerticalPanel labelzPanel;
	@UiField
	TextBox titleInput;
	@UiField
	RichTextArea descriptionInput;
	@UiField
	RichTextArea dataInput;
	@UiField
	Button nextBtn;
	@UiField
	Button createBtn;
	@UiField
	Button cancelBtn;
	@UiField
	ListBox dimensionList;
	@UiField
	TextBox labelxInput;
	@UiField
	TextBox labelyInput;
	@UiField
	TextBox labelzInput;
	/* style */
	private MyStyle ms = new MyStyle();
	// control url
	public GoToUrl url = new GoToUrl();
	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	interface UploadModalUiBinder extends UiBinder<Widget, UploadModal> {
	}

	public UploadModal(String iduser) {
		setWidget(uiBinder.createAndBindUi(this));
		this.IDUSER = iduser;
		this.center();
		// load properties
		LoadModal();
	}

	private void LoadModal() {

		// list box
		dimensionList.addItem("1");
		dimensionList.addItem("2");
		dimensionList.addItem("3");
		dimensionList.addItem("4");
		dimensionList.addItem("5");
		dimensionList.addItem("6");
		dimensionList.addItem("7");
		dimensionList.addItem("8");
		dimensionList.addItem("9");
		dimensionList.addItem("10");
		dimensionList.setItemSelected(2, true);
		dimensionList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (dimensionList.getValue(dimensionList.getSelectedIndex())
						.toString().equals("1")) {
					labelxPanel.setVisible(true);
					labelyPanel.setVisible(false);
					labelzPanel.setVisible(false);

				} else if (dimensionList
						.getValue(dimensionList.getSelectedIndex()).toString()
						.equals("2")) {
					labelxPanel.setVisible(true);
					labelyPanel.setVisible(true);
					labelzPanel.setVisible(false);

				} else if (dimensionList
						.getValue(dimensionList.getSelectedIndex()).toString()
						.equals("3")) {
					labelxPanel.setVisible(true);
					labelyPanel.setVisible(true);
					labelzPanel.setVisible(true);

				} else {
					labelxPanel.setVisible(false);
					labelyPanel.setVisible(false);
					labelzPanel.setVisible(false);
				}

			}
		});
		// dialogbox
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
		dialogBox.setGlassEnabled(true);
		dialogBox.center();
		dialogBox.setText("Create New Data File");
		dialogBox.addStyleName("panel-body");

		// VerticalPanel
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		
		// style
		buttonPanel.addStyleName("btn-group btn-group-justified");

		// set style to buttons from bootstrap
		nextBtn.addStyleName(ms.getButtonStyle(0));
		createBtn.addStyleName(ms.getButtonStyle(0));
		cancelBtn.addStyleName(ms.getButtonStyle(0));

	}

	// create file and upload
	private void CreateDataFile() {
		int dimension = Integer.parseInt(this.dimensionList.getItemText(this.dimensionList.getSelectedIndex()));
		
		if (this.dataInput.getText().isEmpty()){
			ErrorVerify.getErrorAlert("empty");
		}else if(FieldVerifier.checkDataDimension(dimension, this.dataInput.getText())){
			ErrorVerify.getErrorAlert("baddimension");
		}else {
			// verify format
			if (FieldVerifier.isString(this.dataInput.getText())) {
				// verify user has less than 8 files
				serverService.getCountUserFiles(this.IDUSER, new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
								ErrorVerify.getErrorAlert("offline");
							}
							@Override
							public void onSuccess(Integer result) {
								if (result != null) {
									// user has less than 8 files
									if (result >= 0 && result < 8) {
										// upload data
										UserFile userfile = new UserFile(titleInput.getText(), 
																dimensionList.getItemText(dimensionList.getSelectedIndex()),
																labelxInput.getText(),
																labelyInput.getText(),
																labelzInput.getText(),
																descriptionInput.getText(),
																IDUSER, 
																dataInput.getText());
										//service
										serverService.addNewFile(userfile, new AsyncCallback<Boolean>() 
										{
											@Override
											public void onFailure(Throwable caught) {
												ErrorVerify.getErrorAlert("offline");
											}
											@Override
											public void onSuccess(Boolean result) {
												if (result) {
													// file added
													dialogBox.hide();
													url.GoTo("FILES",IDUSER,null);
													ErrorVerify.getErrorAlert("successadd");
												} else {
													ErrorVerify.getErrorAlert("failadd");
													dialogBox.hide();
												}
											}
										});
										
									} else if (result < 0) {
										ErrorVerify.getErrorAlert("fatal");
										dialogBox.hide();
									} 
									else// user has 8 or more files
									{
										ErrorVerify.getErrorAlert("limitfiles");
										dialogBox.hide();
									}
								}
							}
						});

			} else {
				ErrorVerify.getErrorAlert("noletters");
			}

		}
	}

	// show data input
	@UiHandler("nextBtn")
	void onNextBtnClick(ClickEvent event) {
		if (this.titleInput.getText().isEmpty()
				&& this.descriptionInput.getText().isEmpty())
			ErrorVerify.getErrorAlert("empty");
		else {
			panel1.setVisible(false);
			panel2.setVisible(true);
		}
	}

	// handler push upload
	@UiHandler("createBtn")
	void onCreateBtnClick(ClickEvent event) {
		CreateDataFile();
	}

	// evento cambio valor input
	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) {
		dialogBox.hide();
	}

}
