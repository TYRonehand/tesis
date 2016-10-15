package com.project.titulo.client.Metric;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.MetricResults;

public class ResultsWidget extends Composite {

	//uifields---------------	
	@UiField Button backBtn;	
	@UiField Button csvBtn;
	@UiField Label titleName;
	@UiField HTML htmlTable;
	
	//my results--------------------
	private List<MetricResults> RESULTS = null;
	
	//goto url------------------
	private GoToUrl url = new GoToUrl();
	
	
	private static ResultsWidgetUiBinder uiBinder = GWT
			.create(ResultsWidgetUiBinder.class);

	interface ResultsWidgetUiBinder extends UiBinder<Widget, ResultsWidget> {
	}

	public ResultsWidget(List<MetricResults> results,  String MetricName) {
		initWidget(uiBinder.createAndBindUi(this));
		//set style to buttons from bootstrap
		backBtn.addStyleName("btn btn-primary");
		csvBtn.addStyleName("btn btn-primary");
		//save results
		this.RESULTS = results;
		this.titleName.setText("Metric "+MetricName+" Results");
		
		if(results.size()>0)
			LoadTable();
		else
			ErrorVerify.getErrorAlert("fatal");
	}

	
	//Create data table
	private void LoadTable()
	{
		//start table
		String htmlCodeTable ="<table style='width:100%;' border='1'>";
			
			//title fields
			htmlCodeTable+="<tr>";
			htmlCodeTable+="<th>Files</th>";
			
			for(int i = 0; i < RESULTS.get(0).getParetoNameFile().size(); i++)
			{
				htmlCodeTable+="<th>"+RESULTS.get(0).getParetoNameFile().get(i)+"</th>";
			}
			htmlCodeTable+="</tr>";
			
			//data content
			for(int vertical = 0; vertical < RESULTS.size(); vertical++)
			{
				htmlCodeTable+="<tr>";
				htmlCodeTable+="<th>"+RESULTS.get(vertical).getAproximationNameFile()+"</th>";
				for(int horizontal = 0; horizontal < RESULTS.get(vertical).getResultsList().size(); horizontal++)
				{
					htmlCodeTable+="<td>"+RESULTS.get(vertical).getResultsList().get(horizontal)+"</td>";
				}
				htmlCodeTable+="</tr>";
				
			}
		//end table
		htmlCodeTable+="</table>";
		
		this.htmlTable.setHTML(htmlCodeTable);
		
	}
	
	
	
	//click upload
	@UiHandler("backBtn")
	void onRegisteLinkClick(ClickEvent event) 
	{
		url.GoTo("METRIC");
	}
}
