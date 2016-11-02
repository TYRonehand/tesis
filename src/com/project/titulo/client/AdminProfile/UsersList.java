package com.project.titulo.client.AdminProfile;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.User;

public class UsersList extends Composite {

	// uifields
	@UiField
	VerticalPanel panel;
	@UiField
	TextBox searchInput;
	@UiField
	Button modalSearchTopic;
	@UiField
	Button modalNewestTopic;
	@UiField
	Button modalOldestTopic;

	// Create a CellTable.
	private CellTable<User> table = null;
	// goto url
	public GoToUrl url = new GoToUrl();
	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	// ------------------------------------
	// creation binder
	private static UsersListUiBinder uiBinder = GWT
			.create(UsersListUiBinder.class);

	// binder widget
	interface UsersListUiBinder extends UiBinder<Widget, UsersList> {
	}

	public UsersList() {
		initWidget(uiBinder.createAndBindUi(this));
		// set style to buttons from bootstrap
		modalSearchTopic.addStyleName("btn btn-success");
		modalNewestTopic.addStyleName("btn btn-success");
		modalOldestTopic.addStyleName("btn btn-success");
		// load data and table (ASC, USER, COMENTED)
		LoadData("all");
	}

	// ------------------------------------
	// load data from database
	private void LoadData(String opcion) {
		serverService.getUserList(opcion, new AsyncCallback<List<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");

			}

			@Override
			public void onSuccess(List<User> result) {
				LoadTable(result);

			}
		});
	}

	// ------------------------------------
	// Create data table
	private void LoadTable(List<User> DATAINFO) {
		panel.clear();
		table = new CellTable<User>();

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		table.addStyleName("cellTable");
		table.setSize("100%", "25px");
		table.setPageStart(0);
		table.setPageSize(25);
		table.setEmptyTableWidget(new Label("No data found!"));

		// Add a text column to show the name.
		TextColumn<User> nameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getName();
			}
		};
		table.addColumn(nameColumn, "Name");

		// Add a text column to show the lastname.
		TextColumn<User> lastnameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getLastname();
			}
		};
		table.addColumn(lastnameColumn, "Lastname");

		// Add a text column to show the email.
		TextColumn<User> emailColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getMail();
			}
		};
		table.addColumn(emailColumn, "Email");

		// Add a text column to show the registered.
		TextColumn<User> totalColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getCreation();
			}
		};
		table.addColumn(totalColumn, "Join");

		// Add a text column to show the pin.
		TextColumn<User> pinColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getPin();
			}
		};
		table.addColumn(pinColumn, "Pin");

		// Add a text column to show the password.
		TextColumn<User> passColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getPassword();
			}
		};
		table.addColumn(passColumn, "Pass");

		// Add a selection model to handle user selection.
		final SingleSelectionModel<User> selectionModel = new SingleSelectionModel<User>();
		table.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						User selected = selectionModel.getSelectedObject();
						if (selected != null) {
							// redirect

						}
					}
				});

		// Push the data into the widget.
		table.setRowCount(DATAINFO.size(), true);
		table.setRowData(0, DATAINFO);

		// simple pager
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pager.setDisplay(table);
		pager.addStyleName("pagerTable");
		panel.setBorderWidth(0);
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		panel.add(table);
		panel.add(pager);

		// reload data
		Button reloadBtn = new Button("Reload");
		reloadBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LoadData("all");
			}
		});

		panel.add(reloadBtn);
	}

	// ------------------------------------

	@UiHandler("modalNewestTopic")
	void onModalNewestTopicClick(ClickEvent event) {
		LoadData("all");
	}

	@UiHandler("modalOldestTopic")
	void onModalOldestTopicClick(ClickEvent event) {
		LoadData("old");
	}

	@UiHandler("modalSearchTopic")
	void onModalSearchTopicClick(ClickEvent event) {
		if (this.searchInput.getText().length() > 0)
			LoadData(this.searchInput.getText());
		else
			ErrorVerify.getErrorAlert("empty");
	}
}
