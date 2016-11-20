package com.project.titulo.client.Metric;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.client.breadcrumb.BreadWidget;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.UserFile;
import com.google.gwt.uibinder.client.UiHandler;

public class MetricWidget extends Composite {

	/* style */
	private MyStyle ms = new MyStyle();

	/* variables */
	private String IDUSER = null;

	// uifields
	@UiField
	Button metric1Btn;
	@UiField
	Button metric2Btn;
	@UiField
	Button metric3Btn;
	@UiField
	Button metric4Btn;
	@UiField
	Button metric5Btn;
	@UiField
	Button metric6Btn;

	@UiField
	Button EntropyBtn;
	@UiField
	Button SpacingBtn;
	@UiField
	Button ERBtn;
	@UiField
	Button GDistanceBtn;
	@UiField
	Button CoverBtn;
	@UiField
	Button gnvgBtn;

	@UiField
	VerticalPanel panel;
	@UiField
	VerticalPanel EntropyPanel;
	@UiField
	VerticalPanel SpacingPanel;
	@UiField
	VerticalPanel ERPanel;
	@UiField
	VerticalPanel GDistancePanel;
	@UiField
	VerticalPanel CoverPanel;
	@UiField
	VerticalPanel gnvgPanel;

	// Create a CellTable.
	private CellTable<UserFile> table = null;

	// RPC
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);

	private static MetricWidgetUiBinder uiBinder = GWT
			.create(MetricWidgetUiBinder.class);

	interface MetricWidgetUiBinder extends UiBinder<Widget, MetricWidget> {
	}

	public MetricWidget(String iduser) {
		this.IDUSER = iduser;
		initWidget(uiBinder.createAndBindUi(this));
		// set style to buttons from bootstrap
		this.metric1Btn.addStyleName(ms.getButtonStyle(0));
		this.metric2Btn.addStyleName(ms.getButtonStyle(0));
		this.metric3Btn.addStyleName(ms.getButtonStyle(0));
		this.metric4Btn.addStyleName(ms.getButtonStyle(0));
		this.metric5Btn.addStyleName(ms.getButtonStyle(0));
		this.metric6Btn.addStyleName(ms.getButtonStyle(0));
		/*
		this.SpacingBtn.addStyleName(ms.getOkStyle());
		this.EntropyBtn.addStyleName(ms.getCancelStyle());
		this.ERBtn.addStyleName(ms.getOkStyle());
		this.GDistanceBtn.addStyleName(ms.getOkStyle());
		this.CoverBtn.addStyleName(ms.getOkStyle());
		this.gnvgBtn.addStyleName(ms.getOkStyle());
		*/
		
		// load data table
		LoadFilesData();
	}

	// load data from database
	private void LoadFilesData() {
		serverService.getUserFilesMetric(this.IDUSER,
				new AsyncCallback<List<UserFile>>() {

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<UserFile> result) {
						LoadTable(result);
					}
				});
	}

	// Create data table
	private void LoadTable(List<UserFile> DATAINFO) {
		panel.clear();
		table = new CellTable<UserFile>();

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		table.addStyleName("cellTable");
		table.setSize("100%", "25px");
		table.setPageStart(0);
		table.setPageSize(8);
		table.setEmptyTableWidget(new Label("No data found!"));

		/* ADD FILENAME TEXTCELL */
		TextColumn<UserFile> nameColumn = new TextColumn<UserFile>() {
			@Override
			public String getValue(UserFile object) {
				return object.getTitle();
			}
		};
		table.addColumn(nameColumn, "FileName");

		/* ADD dimension TEXTCELL */
		TextColumn<UserFile> dimColumn = new TextColumn<UserFile>() {
			@Override
			public String getValue(UserFile object) {
				return object.getDimension();
			}
		};
		table.addColumn(dimColumn, "Dimension");

		/* ADD CREATION DATE TEXTCELL */
		TextColumn<UserFile> creationColumn = new TextColumn<UserFile>() {
			@Override
			public String getValue(UserFile object) {
				return object.getCreation();
			}
		};
		table.addColumn(creationColumn, "Created");

		/* ADD REMOVE BUTTONCELL */
		ButtonCell buttonCell = new ButtonCell();
		Column<UserFile, String> buttonColumn = new Column<UserFile, String>(
				buttonCell) {
			@Override
			public String getValue(UserFile object) {
				return "remove";
			}
		};
		buttonColumn.setFieldUpdater(new FieldUpdater<UserFile, String>() {
			public void update(int index, UserFile object, String value) {
				serverService.removeMetricFile(object.getIdfile(),
						new AsyncCallback<Boolean>() {
							@Override
							public void onFailure(Throwable caught) {
								ErrorVerify.getErrorAlert("offline");
							}

							@Override
							public void onSuccess(Boolean result) {
								if (result) {
									ErrorVerify.getErrorAlert("fileremove");
									LoadFilesData();
								} else {
									ErrorVerify.getErrorAlert("faildel");
								}
							}
						});
			}
		});
		table.addColumn(buttonColumn, "Action");

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
		panel.setWidth("350");
		panel.add(table);
		panel.add(pager);
		Button reload = new Button("Reload Table");
		reload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LoadFilesData();
			}
		});
		panel.add(reload);

	}

	private void Spacing(String iduser) {
		serverService.CalculateSP(iduser,
				new AsyncCallback<List<MetricResults>>() {

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<MetricResults> result) {

						if (result.size() > 0) {
							RootPanel.get("GWTcontainer").clear();
							// cualquier otro caso sera enviado al login
							RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
							RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER, result,"Spacing"));
						} else {
							ErrorVerify.getErrorAlert("baddimension");
						}
					}
				});
	}

	private void CMetric(String iduser) {
		serverService.CalculateC(iduser,
				new AsyncCallback<List<MetricResults>>() {

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<MetricResults> result) {
						if (result.size() > 0) {
							RootPanel.get("GWTcontainer").clear();
							// cualquier otro caso sera enviado al login
							RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
							RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER, result,"Coverage"));
						} else {
							ErrorVerify.getErrorAlert("baddimension");
						}

					}
				});
	}

	private void ERatio(String iduser) {
		serverService.CalculateER(iduser,
				new AsyncCallback<List<MetricResults>>() {

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<MetricResults> result) {
						if (result.size() > 0) {
							RootPanel.get("GWTcontainer").clear();
							// cualquier otro caso sera enviado al login
							RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
							RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER, result,"Error-Ratio"));
						} else {
							ErrorVerify.getErrorAlert("baddimension");
						}
					}
				});
	}

	private void GDistance(String iduser) {
		serverService.CalculateGD(this.IDUSER,
				new AsyncCallback<List<MetricResults>>() {

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<MetricResults> result) {
						if (result.size() > 0) {
							RootPanel.get("GWTcontainer").clear();
							// cualquier otro caso sera enviado al login
							RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
							RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER, result,"Generational-Distance"));
						} else {
							ErrorVerify.getErrorAlert("baddimension");
						}

					}
				});
	}

	private void SMetric(String iduser) {
		Window.alert("Metric not available");
	}

	private void gnvgMetric(String iduser) {
		serverService.CalculateGNVG(iduser,
				new AsyncCallback<List<MetricResults>>() {

					@Override
					public void onFailure(Throwable caught) {
						ErrorVerify.getErrorAlert("offline");
					}

					@Override
					public void onSuccess(List<MetricResults> result) {

						if (result.size() > 0) {
							RootPanel.get("GWTcontainer").clear();
							// cualquier otro caso sera enviado al login
							RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
							RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER, result,"Generational Nondominated Vector Generation"));
						} else {
							ErrorVerify.getErrorAlert("baddimension");
						}
					}
				});
	}
	
	
	/* SELECT METRIC INFO */
	@UiHandler("metric1Btn")
	void onMetric1BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(true);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(false);
		this.gnvgPanel.setVisible(false);
	}

	@UiHandler("metric2Btn")
	void onMetric2BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(true);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(false);
		this.gnvgPanel.setVisible(false);
	}

	@UiHandler("metric3Btn")
	void onMetric3BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(true);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(false);
		this.gnvgPanel.setVisible(false);
	}

	@UiHandler("metric4Btn")
	void onMetric4BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(true);
		this.CoverPanel.setVisible(false);
		this.gnvgPanel.setVisible(false);
	}

	@UiHandler("metric5Btn")
	void onMetric5BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(true);
		this.gnvgPanel.setVisible(false);
	}
	
	@UiHandler("metric6Btn")
	void onMetric6BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(false);
		this.gnvgPanel.setVisible(true);
	}

	/* METRIC CALCULATE */
	@UiHandler("EntropyBtn")
	void onEntropyBtnClick(ClickEvent event) {
		if(table.getRowCount()>0)
			SMetric(this.IDUSER);
		else
			ErrorVerify.getErrorAlert("nofiles");
	}

	@UiHandler("SpacingBtn")
	void onSpacingBtnClick(ClickEvent event) {
		if(table.getRowCount()>0)
			Spacing(this.IDUSER);
		else
			ErrorVerify.getErrorAlert("nofiles");
	}

	@UiHandler("ERBtn")
	void onERBtnClick(ClickEvent event) {
		if(table.getRowCount()>0)
			ERatio(this.IDUSER);
		else
			ErrorVerify.getErrorAlert("nofiles");
	}

	@UiHandler("GDistanceBtn")
	void onGDistanceBtnClick(ClickEvent event) {
		if(table.getRowCount()>0)
			GDistance(this.IDUSER);
		else
			ErrorVerify.getErrorAlert("nofiles");
	}

	@UiHandler("CoverBtn")
	void onCoverBtnClick(ClickEvent event) {
		if(table.getRowCount()>0)
			CMetric(this.IDUSER);
		else
			ErrorVerify.getErrorAlert("nofiles");
	}
	
	@UiHandler("gnvgBtn")
	void onGnvgBtnClick(ClickEvent event) {
		if(table.getRowCount()>0)
			gnvgMetric(this.IDUSER);
		else
			ErrorVerify.getErrorAlert("nofiles");
	}
}
