package com.project.titulo.client.Forum;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.History;
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
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.CookieVerify;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.ResumeTopic;

public class ForumWidget extends Composite {

	/* style */
	private MyStyle ms = new MyStyle();

	/* variables */
	private String IDUSER = null;

	// uifields
	@UiField
	VerticalPanel panel;
	@UiField
	TextBox searchInput;
	@UiField
	Button modalNewTopic;
	@UiField
	Button modalSearchTopic;
	@UiField
	Button modalMyTopic;
	@UiField
	Button modalNewestTopic;
	@UiField
	Button modalOldestTopic;

	// Create a CellTable.
	private CellTable<ResumeTopic> table = null;
	// goto url
	public GoToUrl url = new GoToUrl();
	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	// ------------------------------------
	// creation binder
	private static ForumWidgetUiBinder uiBinder = GWT
			.create(ForumWidgetUiBinder.class);

	// binder widget
	interface ForumWidgetUiBinder extends UiBinder<Widget, ForumWidget> {
	}

	// ------------------------------------

	// initializator
	public ForumWidget(String iduser) {
		// get id user
		this.IDUSER = iduser;
		initWidget(uiBinder.createAndBindUi(this));
		// set style to buttons from bootstrap
		modalNewTopic.addStyleName(ms.getButtonStyle(0));
		modalSearchTopic.addStyleName(ms.getButtonStyle(0));
		modalMyTopic.addStyleName(ms.getButtonStyle(0));
		modalNewestTopic.addStyleName(ms.getButtonStyle(0));
		modalOldestTopic.addStyleName(ms.getButtonStyle(0));
		// load data and table (ASC, USER, COMENTED)
		LoadNewestTopic();
	}

	// ------------------------------------
	// load data from database
	private void LoadNewestTopic() {
		serverService.NewestResumeTopic(new AsyncCallback<List<ResumeTopic>>() {
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(List<ResumeTopic> result) {
				LoadTable(result);
			}
		});
	}

	private void LoadOldestTopic() {
		serverService.OldestResumeTopic(new AsyncCallback<List<ResumeTopic>>() {
			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(List<ResumeTopic> result) {
				LoadTable(result);
			}
		});
	}

	private void LoadMyTopic(String iduser) {
		serverService.MyResumeTopic(iduser,
				new AsyncCallback<List<ResumeTopic>>() {
					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<ResumeTopic> result) {
						LoadTable(result);
					}
				});
	}

	private void LoadSearchTopic(String search) {
		serverService.SearchResumeTopic(search,
				new AsyncCallback<List<ResumeTopic>>() {
					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<ResumeTopic> result) {
						LoadTable(result);
					}
				});
	}

	// ------------------------------------
	// Create data table
	private void LoadTable(List<ResumeTopic> DATAINFO) {
		panel.clear();
		table = new CellTable<ResumeTopic>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		table.addStyleName("cellTable");
		table.setSize("100%", "25px");
		table.setPageStart(0);
		table.setPageSize(8);
		table.setEmptyTableWidget(new Label("No data found!"));

		// Add a text column to show the name.
		TextColumn<ResumeTopic> nameColumn = new TextColumn<ResumeTopic>() {
			@Override
			public String getValue(ResumeTopic object) {
				return object.getTitle();
			}
		};
		table.addColumn(nameColumn, "Title");

		// Add a text column to show the name.
		TextColumn<ResumeTopic> dateColumn = new TextColumn<ResumeTopic>() {
			@Override
			public String getValue(ResumeTopic object) {
				return object.getCreated();
			}
		};
		table.addColumn(dateColumn, "Creation");

		// Add a text column to show the address.
		TextColumn<ResumeTopic> addressColumn = new TextColumn<ResumeTopic>() {
			@Override
			public String getValue(ResumeTopic object) {
				return object.getUser();
			}
		};
		table.addColumn(addressColumn, "User");

		// Add a text column to show the address.
		TextColumn<ResumeTopic> totalColumn = new TextColumn<ResumeTopic>() {
			@Override
			public String getValue(ResumeTopic object) {
				return object.getTotal();
			}
		};
		table.addColumn(totalColumn, "Responses");

		// Add a selection model to handle user selection.
		final SingleSelectionModel<ResumeTopic> selectionModel = new SingleSelectionModel<ResumeTopic>();
		table.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						ResumeTopic selected = selectionModel
								.getSelectedObject();
						if (selected != null) {
							// cookies
							CookieVerify mycookie = new CookieVerify(false);
							mycookie.setCookieITopic(selected.getIdtopic());
							// redirect
							History.newItem("topic");
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
		panel.add(new Label("*press topic to Read"));
		panel.add(table);
		panel.add(pager);

		Button reloadBtn = new Button("Reload");
		reloadBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LoadNewestTopic();
			}
		});

		panel.add(reloadBtn);
	}

	// ------------------------------------

	// evento cambio valor input
	@UiHandler("modalNewTopic")
	void onModalCallClick(ClickEvent event) {
		// cualquier otro caso sera enviado al login
		url.GoTo("MODALNEWTOPIC",IDUSER,null);
	}

	// load table and topics
	@UiHandler("modalMyTopic")
	void onModalMyTopicClick(ClickEvent event) {
		LoadMyTopic(this.IDUSER);
	}

	@UiHandler("modalNewestTopic")
	void onModalNewestTopicClick(ClickEvent event) {
		LoadNewestTopic();
	}

	@UiHandler("modalOldestTopic")
	void onModalOldestTopicClick(ClickEvent event) {
		LoadOldestTopic();
	}

	@UiHandler("modalSearchTopic")
	void onModalSearchTopicClick(ClickEvent event) {
		if (this.searchInput.getText().length() > 0)
			LoadSearchTopic(this.searchInput.getText());
		else
			ErrorVerify.getErrorAlert("empty");
	}

	@UiHandler("searchInput")
	void onPassInputKeyDown(KeyDownEvent event) {

		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			LoadSearchTopic(this.searchInput.getText());
		}
	}
}
