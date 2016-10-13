package com.project.titulo.client.Metric;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.MetricResults;

public class ResultsWidget extends Composite {

	//uifields	
	@UiField Button backBtn;
	@UiField VerticalPanel panel;
	
	//------------------------------------
	//Create a CellTable.
	private CellTable<MetricResults> table = null;
	
	//goto url
	public GoToUrl url = new GoToUrl();
	
	private static ResultsWidgetUiBinder uiBinder = GWT
			.create(ResultsWidgetUiBinder.class);

	interface ResultsWidgetUiBinder extends UiBinder<Widget, ResultsWidget> {
	}

	public ResultsWidget(List<MetricResults> results,  String MetricName) {
		initWidget(uiBinder.createAndBindUi(this));

		//set style to buttons from bootstrap
		backBtn.addStyleName("btn btn-primary");
		
		
		if(results != null){
			LoadTable(results,MetricName);
		}else{
			ErrorVerify.getErrorAlert("fatal");
		}
	}

	
	//Create data table
	private void LoadTable(List<MetricResults> RESULTS,  String MetricName)
	{
		//cleaning
		panel.clear();
		table = new CellTable<MetricResults>();
		
		//properties
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		table.addStyleName("cellTable");
		table.setSize("100%", "25px");
		table.setPageStart(0);
		table.setPageSize(8);
		table.setEmptyTableWidget(new Label("No data found!"));
	  
		
		/*ADD FILENAME TEXTCELL*/
		TextColumn<MetricResults> nameColumn = new TextColumn<MetricResults>() {
		     @Override
		     public String getValue(MetricResults object) {
		        return object.getAproximationNameFile();
		     }
		};
		table.addColumn(nameColumn, "File");
		
		/*ADD dimension TEXTCELL*/
		TextColumn<MetricResults> dimColumn = new TextColumn<MetricResults>() {
		     @Override
		     public String getValue(MetricResults object) {
		        return object.getParetoNameFile();
		     }
		};
		table.addColumn(dimColumn, "Pareto-Front");

		/*ADD CREATION DATE TEXTCELL*/
		TextColumn<MetricResults> creationColumn = new TextColumn<MetricResults>() {
		     @Override
		     public String getValue(MetricResults object) {
		        return object.getResults();
		     }
		};
		table.addColumn(creationColumn, "Result");
	      
		
		// draw table
		table.setRowCount(RESULTS.size(), true);
		table.setRowData(0, RESULTS);
		
		//simple pager
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(table);
		pager.addStyleName("pagerTable");
		
		panel.setBorderWidth(0);
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		panel.setWidth("350");
		panel.add(new Label("Metric: "+MetricName+" - RESULTS"));
		panel.add(table);
		panel.add(pager);
		
	}
	
	
	
	//click upload
	@UiHandler("backBtn")
	void onRegisteLinkClick(ClickEvent event) 
	{
		url.GoTo("METRIC");
	}
}
