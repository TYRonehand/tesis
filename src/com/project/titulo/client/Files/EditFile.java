package com.project.titulo.client.Files;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.FieldVerifier;
import com.project.titulo.shared.GoToUrl;
import com.project.titulo.shared.MyStyle;
import com.project.titulo.shared.model.UserFile;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class EditFile extends Composite {

	@UiField
	VerticalPanel labelxPanel;
	@UiField
	VerticalPanel labelyPanel;
	@UiField
	VerticalPanel labelzPanel;
	@UiField
	TextBox titleBox;
	@UiField
	ListBox dimensionList;
	@UiField
	TextBox labelxBox;
	@UiField
	TextBox labelyBox;
	@UiField
	TextBox labelzBox;
	@UiField
	TextArea descriptionBox;
	@UiField
	TextArea paretoSetBox;
	@UiField
	Button saveBtn;
	@UiField
	Button cancelBtn;
	/* style */
	private MyStyle ms = new MyStyle();
	//url control
	private GoToUrl url = new GoToUrl();

	// id for data file
	private String IDDATAFILE;
	private UserFile USERFILE;
	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	// variables
	private static EditFileUiBinder uiBinder = GWT
			.create(EditFileUiBinder.class);

	interface EditFileUiBinder extends UiBinder<Widget, EditFile> {
	}

	public EditFile(String iddatafile) {
		// id from datafile
		this.IDDATAFILE = iddatafile;

		// properties
		initWidget(uiBinder.createAndBindUi(this));

		// bootstrap style
		saveBtn.setStyleName(ms.getButtonStyle(0));
		cancelBtn.setStyleName(ms.getButtonStyle(0));

		// load data from file into inputs
		getFileData();
	}

	private void getFileData() {
		serverService.getDataFile(this.IDDATAFILE,
				new AsyncCallback<UserFile>() {

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(UserFile result) {
						if (result != null) {
							USERFILE = result;
							LoadDataInput(result);
						} else {
							ErrorVerify.getErrorAlert("fatal");
						}
					}
				});
	}

	private void LoadDataInput(UserFile result) {
		titleBox.setText(result.getTitle());
		descriptionBox.setText(result.getDescription());
		paretoSetBox.setText(result.getData());
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
		dimensionList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (dimensionList.getValue(dimensionList.getSelectedIndex())
						.toString().equals("1")) {
					labelxPanel.setVisible(true);
					labelyPanel.setVisible(false);
					labelzPanel.setVisible(false);
					labelyBox.setText("");
					labelzBox.setText("");

				} else if (dimensionList
						.getValue(dimensionList.getSelectedIndex()).toString()
						.equals("2")) {
					labelxPanel.setVisible(true);
					labelyPanel.setVisible(true);
					labelzPanel.setVisible(false);
					labelzBox.setText("");

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
					labelxBox.setText("");
					labelyBox.setText("");
					labelzBox.setText("");
				}
			}
		});
		// combobox
		for (int i = 0; i < dimensionList.getItemCount(); i++) {
			if (dimensionList.getItemText(i).equals(result.getDimension())) {
				dimensionList.setItemSelected(i, true);
				break;
			}
		}

		// labels
		if (dimensionList.getValue(dimensionList.getSelectedIndex()).toString()
				.equals("1")) {
			labelxPanel.setVisible(true);
			labelyPanel.setVisible(false);
			labelzPanel.setVisible(false);
			labelxBox.setText(result.getLabelx());

		} else if (dimensionList.getValue(dimensionList.getSelectedIndex())
				.toString().equals("2")) {
			labelxPanel.setVisible(true);
			labelyPanel.setVisible(true);
			labelzPanel.setVisible(false);
			labelxBox.setText(result.getLabelx());
			labelyBox.setText(result.getLabely());

		} else if (dimensionList.getValue(dimensionList.getSelectedIndex())
				.toString().equals("3")) {
			labelxPanel.setVisible(true);
			labelyPanel.setVisible(true);
			labelzPanel.setVisible(true);
			labelxBox.setText(result.getLabelx());
			labelyBox.setText(result.getLabely());
			labelzBox.setText(result.getLabelz());

		} else {
			labelxPanel.setVisible(false);
			labelyPanel.setVisible(false);
			labelzPanel.setVisible(false);
			labelxBox.setText("");
			labelyBox.setText("");
			labelzBox.setText("");
		}
	}

	private void EditDataFile(UserFile file) 
	{
		int dimension = Integer.parseInt(this.dimensionList.getItemText(this.dimensionList.getSelectedIndex()));
		
		//validation
		if (this.paretoSetBox.getText().isEmpty()){
			ErrorVerify.getErrorAlert("empty");
		}else if(FieldVerifier.checkDataDimension(dimension, this.paretoSetBox.getText())){
			ErrorVerify.getErrorAlert("baddimension");
		}else {
			// verify format
			if (FieldVerifier.isString(this.paretoSetBox.getText())) {
		
				UserFile edited = file;
				edited.setTitle(this.titleBox.getText());
				edited.setDimension(this.dimensionList.getItemText(
						this.dimensionList.getSelectedIndex()).toString());
				edited.setLabelx(this.labelxBox.getText());
				edited.setLabely(this.labelyBox.getText());
				edited.setLabelz(this.labelzBox.getText());
				edited.setDescription(this.descriptionBox.getText());
				edited.setData(this.paretoSetBox.getText());
				
				// update request
				serverService.setFile(edited, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}
		
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							ErrorVerify.getErrorAlert("successupdate");
							url.GoTo("files", USERFILE.getIduser(), null);
						} else {
							ErrorVerify.getErrorAlert("failupdate");
						}
					}
				});
			} else {
				ErrorVerify.getErrorAlert("noletters");
			}

		}
	}

	// cancel button event
	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) {
		url.GoTo("files", USERFILE.getIduser(), null);
	}

	// save button event
	@UiHandler("saveBtn")
	void onSaveBtnClick(ClickEvent event) {
		EditDataFile(this.USERFILE);
	}
}
