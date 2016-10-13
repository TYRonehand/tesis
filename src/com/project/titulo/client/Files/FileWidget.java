package com.project.titulo.client.Files;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.UserFile;

public class FileWidget extends Composite {

	private String IDUSER=null;
	
	//uifields	
	@UiField Button uploadBtn;
	@UiField Button helpBtn;
	@UiField VerticalPanel panel;
	
	//------------------------------------
	
	
	//Create a CellTable.
	private CellTable<UserFile> table = null;
	//goto url
	public GoToUrl url = new GoToUrl();
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	//CREATION
	private static FileWidgetUiBinder uiBinder = GWT.create(FileWidgetUiBinder.class);

	interface FileWidgetUiBinder extends
			UiBinder<Widget, FileWidget> {
	}
	public FileWidget(String iduser) {
	
		//get id user
		this.IDUSER=iduser;
		//properties
		initWidget(uiBinder.createAndBindUi(this));

		//set style to buttons from bootstrap
		uploadBtn.addStyleName("btn btn-primary");
		helpBtn.addStyleName("btn btn-primary");
		//table
		LoadFilesData();
	}
	
	//load data from database
	private void LoadFilesData()
	{
		serverService.getUserFiles(this.IDUSER, new AsyncCallback<List<UserFile>>(){

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
		//cleaning
		panel.clear();
		table = new CellTable<UserFile>();
		
		//properties
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
	      
		
		/*ADD TO PLOT BUTTONCELL*/
		ButtonCell buttonPlot = new ButtonCell();
		Column<UserFile, String> buttonPlotColumn = new Column<UserFile, String>(buttonPlot) {
		  @Override
		  public String getValue(UserFile object) {
			  if(object.getPlot().equals("1"))
				  return "Added";
			  else
				  return "Add";
		  }
		};
		buttonPlotColumn.setFieldUpdater(new FieldUpdater<UserFile, String>() 
		{
			  public void update(int index, UserFile object, String value) 
			  {
				  serverService.addPlotFile(object.getIddatafile(), new AsyncCallback<Boolean>(){

						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}
						@Override
						public void onSuccess(Boolean result) 
						{
							if(result){
								ErrorVerify.getErrorAlert("plotfileadd");
								LoadFilesData();
							}else{
								ErrorVerify.getErrorAlert("failadd");
							}
						}}); 
			  }
		});
		table.addColumn(buttonPlotColumn, "Plot");
		
		/*ADD TO METRIC BUTTONCELL*/
		ButtonCell buttonMetric = new ButtonCell();
		Column<UserFile, String> buttonMetricColumn = new Column<UserFile, String>(buttonMetric) {
		  @Override
		  public String getValue(UserFile object) {
			  if(object.getMetric().equals("1"))
				  return "Added";
			  else
				  return "Add";
		  }
		};
		buttonMetricColumn.setFieldUpdater(new FieldUpdater<UserFile, String>() 
		{
			  public void update(int index, UserFile object, String value) 
			  {
				  serverService.addMetricFile(object.getIddatafile(), new AsyncCallback<Boolean>(){

						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}
						@Override
						public void onSuccess(Boolean result) 
						{
							if(result){
								ErrorVerify.getErrorAlert("metricfileadd");
								LoadFilesData();
							}else{
								ErrorVerify.getErrorAlert("failadd");
							}
						}}); 
			  }
		});
		table.addColumn(buttonMetricColumn, "Metric");
		
		/*DELETE BOUTTON CELL*/
		ButtonCell buttonDelete = new ButtonCell();
		Column<UserFile, String> buttonDeleteColumn = new Column<UserFile, String>(buttonDelete) {
		  @Override
		  public String getValue(UserFile object) {
			  
			  return "Delete";
		  }
		};
		buttonDeleteColumn.setFieldUpdater(new FieldUpdater<UserFile, String>() 
		{
			  public void update(int index, UserFile object, String value) 
			  {
				  serverService.deleteFile(object.getIddatafile(), new AsyncCallback<Boolean>(){

						@Override
						public void onFailure(Throwable caught) {
							ErrorVerify.getErrorAlert("offline");
						}
						@Override
						public void onSuccess(Boolean result) 
						{
							if(result){
								LoadFilesData();
							}else{
								ErrorVerify.getErrorAlert("faildel");
							}
						}}); 
			  }
		});
		table.addColumn(buttonDeleteColumn, "Action");
		
		
		// draw table
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
		panel.add(new Label("*select action"));
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
	
	//click upload
	@UiHandler("uploadBtn")
	void onRegisteLinkClick(ClickEvent event) 
	{
		url.GoTo("MODALUPLOAD");
	}
	
	//click Help
	@UiHandler("helpBtn")
	void onHelpBtnClick(ClickEvent event) 
	{
		url.GoTo("MODALHELP");
	}
	
	
}
