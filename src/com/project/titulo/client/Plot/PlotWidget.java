package com.project.titulo.client.Plot;

import java.util.ArrayList;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.UserFile;

public class PlotWidget extends Composite {

	/*style*/
	private MyStyle ms = new MyStyle();
	
	/*variables*/
	private String IDUSER=null;
	
	//panels
	@UiField VerticalPanel panel;
	@UiField VerticalPanel gridItems;
	//Grid option
	@UiField SimpleCheckBox linesGrid;	
	@UiField SimpleCheckBox pointsGrid;	
	//plot live
	@UiField Button plotBtn;
	//save
	@UiField Button epsBtn;	
	@UiField Button pdfBtn;	
	@UiField Button pngBtn;	
	@UiField Button svgBtn;	
	//labels
	@UiField TextBox titleBox;
	@UiField TextBox labelxBox;
	@UiField TextBox labelyBox;
	@UiField TextBox labelzBox;
	//limits and dimension
	@UiField SimpleCheckBox dimensionCheckbox;
	@UiField DoubleBox minxBox;
	@UiField DoubleBox maxxBox;
	@UiField DoubleBox minyBox;
	@UiField DoubleBox maxyBox;
	@UiField DoubleBox minzBox;
	@UiField DoubleBox maxzBox;
	
	//------------------------------------
	
	
	//Create a CellTable.
	private CellTable<UserFile> table = null;
	//goto url
	public GoToUrl url = new GoToUrl();
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	

	//------------------------------------
	//creation binder
	private static PlotWidgetUiBinder uiBinder = GWT.create(PlotWidgetUiBinder.class);
	//binder widget
	interface PlotWidgetUiBinder extends UiBinder<Widget, PlotWidget> {
	}

	//------------------------------------
	
	//initializator
	public PlotWidget(String iduser) 
	{
		//get id user
		this.IDUSER=iduser;
		//properties
		initWidget(uiBinder.createAndBindUi(this));
		//set style to buttons from bootstrap
		plotBtn.addStyleName(ms.getButtonStyle());	
		epsBtn.addStyleName(ms.getButtonStyle());	
		pdfBtn.addStyleName(ms.getButtonStyle());	
		pngBtn.addStyleName(ms.getButtonStyle());	
		svgBtn.addStyleName(ms.getButtonStyle());	
		//load properties
		gridItems.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		//table
		LoadFilesData();
	}
	
	//load data from database
	private void LoadFilesData()
	{
		serverService.getUserFilesPlot(this.IDUSER, new AsyncCallback<List<UserFile>>(){

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
		//add creation
		table.addColumn(creationColumn, "Created");
	     
		/*ADD REMOVE BUTTONCELL*/
		ButtonCell buttonCell = new ButtonCell();
		Column<UserFile, String> buttonColumn = new Column<UserFile, String>(buttonCell) {
		  @Override
		  public String getValue(UserFile object) {
			  
			  return "remove";
		  }
		};
		//
		buttonColumn.setFieldUpdater(new FieldUpdater<UserFile, String>() 
		{
			  public void update(int index, UserFile object, String value) 
			  {
				  serverService.removePlotFile(object.getIddatafile(), new AsyncCallback<Boolean>(){

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
							}
							else
							{

								ErrorVerify.getErrorAlert("faildel");
							}
						}}
				  ); 
			  }
		});
		table.addColumn(buttonColumn, "Action");
		

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

	//save images for extension
	private void SaveImg(String extension)
	{
		//labels
		List<String> labelxyz = new ArrayList<String>();
		labelxyz.add(this.labelxBox.getText());//0
		labelxyz.add(this.labelyBox.getText());//1
		labelxyz.add(this.labelzBox.getText());//2
		//limits
		List<String> limitxyz = new ArrayList<String>();
		limitxyz.add(this.minxBox.getText());
		limitxyz.add(this.maxxBox.getText());
		limitxyz.add(this.minyBox.getText());
		limitxyz.add(this.maxyBox.getText());
		limitxyz.add(this.minzBox.getText());
		limitxyz.add(this.maxzBox.getText());
		
		//2Dimension plot
		if(!this.dimensionCheckbox.getValue())
		{
			//service draw
			serverService.CreateImage2D(this.pointsGrid.getValue(), this.linesGrid.getValue(), extension,this.titleBox.getText(), labelxyz, limitxyz, this.IDUSER, new AsyncCallback<String>(){
	
				@Override
				public void onFailure(Throwable caught) {
					ErrorVerify.getErrorAlert("offline");
				}
				@Override
				public void onSuccess(String result) {
					if(!result.isEmpty())
					{
						//Window.Location.assign(GWT.getHostPageBaseURL()+result);
						ErrorVerify.getErrorAlert("successadd");
						//Window.open(GWT.getHostPageBaseURL()+result,"_blank",window);
						Window.Location.assign(GWT.getHostPageBaseURL()+result);
					}
					else
					{
						ErrorVerify.getErrorAlert("nofileplot");
					}
				}});
		}
		else//3Dimension plot
		{
			//draw img
			serverService.CreateImage3D(this.pointsGrid.getValue(), this.linesGrid.getValue(), extension, this.titleBox.getText(), labelxyz, limitxyz, this.IDUSER, new AsyncCallback<String>(){
	
				@Override
				public void onFailure(Throwable caught) {
					ErrorVerify.getErrorAlert("offline");
				}
				@Override
				public void onSuccess(String result) {
					if(!result.isEmpty())
					{
						//Window.Location.assign(GWT.getHostPageBaseURL()+result);
						ErrorVerify.getErrorAlert("successadd");
						Window.Location.assign(GWT.getHostPageBaseURL()+result);
					}
					else
					{
						ErrorVerify.getErrorAlert("nofileplot");
					}
				}});
		}
	}
	
	//ON CLICK EVENTS------------------------------------------------------------------------
	
	
	//plot live 
	@UiHandler("plotBtn")
	void onPlotBtnClick(ClickEvent event) 
	{
		SaveImg("html");
    }

	//guarda imagen en png
	@UiHandler("pngBtn")
	void onPngBtnClick(ClickEvent event) 
	{
		SaveImg("png");
    }
	
	//guarda imagen en eps
	@UiHandler("epsBtn")
	void onEpsBtnClick(ClickEvent event) 
	{
		SaveImg("eps");
    }

	//guarda imagen en svg
	@UiHandler("svgBtn")
	void onSvgBtnClick(ClickEvent event) 
	{
		SaveImg("svg");
    }
	
	//guarda imagen en pdf
	@UiHandler("pdfBtn")
	void onPdfBtnClick(ClickEvent event) 
	{
		SaveImg("pdf");
    }
	
	
}
