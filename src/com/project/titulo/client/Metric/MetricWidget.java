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
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.client.breadcrumb.BreadWidget;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.UserFile;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class MetricWidget extends Composite {

	/*style*/
	private MyStyle ms = new MyStyle();
	
	/*variables*/
	private String IDUSER=null;
	
	//uifields	
	@UiField Button metric1Btn;
	@UiField Button metric2Btn;
	@UiField Button metric3Btn;
	@UiField Button metric4Btn;
	@UiField Button metric5Btn;

	@UiField Button EntropyBtn;
	@UiField Button SpacingBtn;
	@UiField Button ERBtn;
	@UiField Button GDistanceBtn;
	@UiField Button CoverBtn;
	
	@UiField VerticalPanel panel;
	@UiField VerticalPanel EntropyPanel;
	@UiField VerticalPanel SpacingPanel;
	@UiField VerticalPanel ERPanel;
	@UiField VerticalPanel GDistancePanel;
	@UiField VerticalPanel CoverPanel;
	
	//Create a CellTable.
	private CellTable<UserFile> table = null;
		
	//goto url
	public GoToUrl url = new GoToUrl();
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);

	private static MetricWidgetUiBinder uiBinder = GWT.create(MetricWidgetUiBinder.class);

	interface MetricWidgetUiBinder extends UiBinder<Widget, MetricWidget> {
	}

	public MetricWidget(String iduser) 
	{
		this.IDUSER = iduser;
		initWidget(uiBinder.createAndBindUi(this));
		//set style to buttons from bootstrap
		this.metric1Btn.addStyleName(ms.getButtonStyle());
		this.metric2Btn.addStyleName(ms.getButtonStyle());
		this.metric3Btn.addStyleName(ms.getButtonStyle());
		this.metric4Btn.addStyleName(ms.getButtonStyle());
		this.metric5Btn.addStyleName(ms.getButtonStyle());
		this.SpacingBtn.addStyleName(ms.getOkStyle());
		this.EntropyBtn.addStyleName(ms.getCancelStyle());
		this.ERBtn.addStyleName(ms.getOkStyle());
		this.GDistanceBtn.addStyleName(ms.getOkStyle());
		this.CoverBtn.addStyleName(ms.getCancelStyle());
		//load data table
		LoadFilesData();
	}


	//load data from database
	private void LoadFilesData()
	{
		serverService.getUserFilesMetric(this.IDUSER, new AsyncCallback<List<UserFile>>(){

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(List<UserFile> result) {
				LoadTable(result);
			}});
	}
	
	//Create data table
	private void LoadTable(List<UserFile> DATAINFO)
	{
		panel.clear();
		table = new CellTable<UserFile>();
		
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		table.addStyleName("cellTable");
		table.setSize("100%", "25px");
		table.setPageStart(0);
		table.setPageSize(8);
		table.setEmptyTableWidget(new Label("No data found!"));
	  
		
		/*ADD FILENAME TEXTCELL*/
		TextColumn<UserFile> nameColumn = new TextColumn<UserFile>() {
		     @Override
		     public String getValue(UserFile object) {
		        return object.getTitle();
		     }
		};
		table.addColumn(nameColumn, "FileName");


		/*ADD dimension TEXTCELL*/
		TextColumn<UserFile> dimColumn = new TextColumn<UserFile>() {
		     @Override
		     public String getValue(UserFile object) {
		        return object.getDimension();
		     }
		};
		table.addColumn(dimColumn, "Dimension");
		
		/*ADD CREATION DATE TEXTCELL*/
		TextColumn<UserFile> creationColumn = new TextColumn<UserFile>() {
		     @Override
		     public String getValue(UserFile object) {
		        return object.getCreation();
		     }
		};
		table.addColumn(creationColumn, "Created");
	     
		/*ADD REMOVE BUTTONCELL*/
		ButtonCell buttonCell = new ButtonCell();
		Column<UserFile, String> buttonColumn = new Column<UserFile, String>(buttonCell) {
		  @Override
		  public String getValue(UserFile object) {
			  return "remove";
		  }
		};
		buttonColumn.setFieldUpdater(new FieldUpdater<UserFile, String>() 
		{
			  public void update(int index, UserFile object, String value) 
			  {
				  serverService.removeMetricFile(object.getIddatafile(), new AsyncCallback<Boolean>(){
						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}
						@Override
						public void onSuccess(Boolean result) 
						{
							if(result){
								ErrorVerify.getErrorAlert("fileremove");
								LoadFilesData();
							}else{
								ErrorVerify.getErrorAlert("faildel");
							}
						}}
				  ); 
			  }
		});
		table.addColumn(buttonColumn, "Action");
		
		// Add a selection model to handle user selection.
	    final SingleSelectionModel<UserFile> selectionModel = new SingleSelectionModel<UserFile>();
	    table.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	  UserFile selected = selectionModel.getSelectedObject();
	        if (selected != null) {
	        	
	        }
	      }
	    });
		

		// Push the data into the widget.
		table.setRowCount(DATAINFO.size(), true);
		table.setRowData(0, DATAINFO);
		//simple pager
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(table);
		pager.addStyleName("pagerTable");
		
		panel.setBorderWidth(0);
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		panel.setWidth("350");
		panel.add(new Label("*Press to select Pareto Front True (PFtrue)"));
		panel.add(table);
		panel.add(pager);
		Button reload = new Button("Reload Table");
		reload.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				 LoadFilesData();
			}}
		);
		panel.add(reload);
		
	}
	
	
	/*SELECT METRIC INFO*/
	@UiHandler("metric1Btn")
	void onMetric1BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(true);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(false);
	}
	@UiHandler("metric2Btn")
	void onMetric2BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(true);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(false);
	}
	@UiHandler("metric3Btn")
	void onMetric3BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(true);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(false);
	}
	@UiHandler("metric4Btn")
	void onMetric4BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(true);
		this.CoverPanel.setVisible(false);
	}
	@UiHandler("metric5Btn")
	void onMetric5BtnClick(ClickEvent event) {
		this.EntropyPanel.setVisible(false);
		this.SpacingPanel.setVisible(false);
		this.ERPanel.setVisible(false);
		this.GDistancePanel.setVisible(false);
		this.CoverPanel.setVisible(true);
	}
	
	/*METRIC CALCULATE*/
	@UiHandler("EntropyBtn")
	void onEntropyBtnClick(ClickEvent event) 
	{
		
	}
	@UiHandler("SpacingBtn")
	void onSpacingBtnClick(ClickEvent event) 
	{
		serverService.CalculateSP( this.IDUSER, new AsyncCallback<List<MetricResults>>(){

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(List<MetricResults> result) {
				
				if(result.size()>0)
				{
					RootPanel.get("GWTcontainer").clear();
					//cualquier otro caso sera enviado al login
					RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
					RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER ,result, "Spacing"));
				}
				else{
					String Message ="No data returned";
					Window.alert(Message);
				}
				
			}});
		
	}
	
	@UiHandler("ERBtn")
	void onERBtnClick(ClickEvent event) 
	{
		serverService.CalculateER(this.IDUSER, new AsyncCallback<List<MetricResults>>(){

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(List<MetricResults> result) {
				if(result.size()>0)
				{
					RootPanel.get("GWTcontainer").clear();
					//cualquier otro caso sera enviado al login
					RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
					RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER ,result, "Error-Ratio"));
				}
				else{
					String Message ="No data returned";
					Window.alert(Message);
				}
			}});
	}
	
	@UiHandler("GDistanceBtn")
	void onGDistanceBtnClick(ClickEvent event) 
	{
		serverService.CalculateGD(this.IDUSER, new AsyncCallback<List<MetricResults>>(){

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(List<MetricResults> result) 
			{
				if(result.size()>0)
				{
					RootPanel.get("GWTcontainer").clear();
					//cualquier otro caso sera enviado al login
					RootPanel.get("GWTcontainer").add(new BreadWidget("METRIC"));
					RootPanel.get("GWTcontainer").add(new ResultsWidget(IDUSER ,result, "Generational-Distance"));
				}
				else{
					String Message ="No data returned";
					Window.alert(Message);
				}
				
			}});
			
	}
	
	@UiHandler("CoverBtn")
	void onCoverBtnClick(ClickEvent event) 
	{
		
	}
}
