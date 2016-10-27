package com.project.titulo.client.Metric;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.model.MetricResults;

public class ResultsWidget extends Composite {

	/*style*/
	private MyStyle ms = new MyStyle();
	
	/*variables*/
	//uifields---------------	
	@UiField Button backBtn;	
	@UiField Button exportBtn;
	@UiField Label titleName;
	@UiField HTML htmlTable;
	
	//my results--------------------
	private List<MetricResults> RESULTS = null;
	private String IDUSER = null;
	//goto url------------------
	private GoToUrl url = new GoToUrl();
	//RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	
	private static ResultsWidgetUiBinder uiBinder = GWT
			.create(ResultsWidgetUiBinder.class);

	interface ResultsWidgetUiBinder extends UiBinder<Widget, ResultsWidget> {
	}

	public ResultsWidget(String iduser, List<MetricResults> results,  String MetricName) {
		initWidget(uiBinder.createAndBindUi(this));
		//set style to buttons from bootstrap
		backBtn.addStyleName(ms.getButtonStyle(0));
		exportBtn.addStyleName(ms.getButtonStyle(0));
		//save results
		this.IDUSER = iduser;
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
	
	
	
	//click go back
	@UiHandler("backBtn")
	void onRegisteLinkClick(ClickEvent event) 
	{
		url.GoTo("METRIC");
	}
	
	//click upload
	@UiHandler("exportBtn")
	void onExportFileClick(ClickEvent event) 
	{
		serverService.ExportResults(this.IDUSER, this.RESULTS, new AsyncCallback<Boolean>(){

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(Boolean result) {
				if(result){
					Window.open(GWT.getHostPageBaseURL()+"download/"+IDUSER+".csv","_blank","");
				}else{
					ErrorVerify.getErrorAlert("failexport");
				}
				
			}});
	}
	
	
}
