package com.project.titulo.client.AdminProfile;


import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.plotOptions.ColumnPlotOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.model.AdminChartResume;
import com.project.titulo.shared.model.AdminResume;

public class ResumeUser extends Composite {

	@UiField VerticalPanel lastmonthusers;
	@UiField VerticalPanel lastmonthtopics;
	@UiField Label totalusers;
	@UiField Label onlineusers;
	@UiField Label totaltopics;
	@UiField Label totalfiles;
	@UiField Label totalsizefiles;
	@UiField Label avgsizefiles;
	
	// RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	private static ResumeUserUiBinder uiBinder = GWT.create(ResumeUserUiBinder.class);

	interface ResumeUserUiBinder extends UiBinder<Widget, ResumeUser> {
	}

	public ResumeUser() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//carga
		LoadInformation();
		//crea crea timer
		Timer ReloadPage = new Timer(){

			@Override
			public void run() {
				LoadInformation();
				
			}
			
		};
		ReloadPage.schedule(5*60000);
		
	}
	
	private void LoadInformation(){
		//get info resume
			serverService.getResumeInfo(new AsyncCallback<AdminResume>(){
				@Override
				public void onFailure(Throwable caught) {
					totalusers.setText("-");
					onlineusers.setText("-");
					totaltopics.setText("-");
					totalfiles.setText("-");
					totalsizefiles.setText("-");
					avgsizefiles.setText("-");
				}
				@Override
				public void onSuccess(AdminResume result) {
					totalusers.setText(Integer.toString(result.getTotalUsers()));
					
					onlineusers.setText(Integer.toString(result.getOnlineUsers()));
					
					totaltopics.setText(Integer.toString(result.getTotalTopics()));
					
					totalfiles.setText(Integer.toString(result.getTotaFiles()));
					
					totalsizefiles.setText(Double.toString(result.getTotalSizeFiles())+" KB");
					
					double avgsize = result.getTotalSizeFiles() / result.getTotaFiles();
					avgsizefiles.setText("~ "+Math.round(avgsize*100)/100+" KB");
					
				}});
			
			
			//clean elements
			this.lastmonthusers.clear();
			this.lastmonthtopics.clear();
			
			//load chart users
			serverService.getChartUsers(new AsyncCallback<AdminChartResume>(){

				@Override
				public void onFailure(Throwable caught) {
					lastmonthusers.add(new Label("Reload Page"));
					
				}

				@Override
				public void onSuccess(AdminChartResume result) {
					ChartLastYearUsers(result);
				}});
			
			

			//load chart topics
			serverService.getChartTopics(new AsyncCallback<AdminChartResume>(){

				@Override
				public void onFailure(Throwable caught) {
					lastmonthtopics.add(new Label("Reload Page"));
					
				}

				@Override
				public void onSuccess(AdminChartResume result) {
					ChartLastYearTopics(result);
				}});
		
	}
	
	private void ChartLastYearUsers(AdminChartResume chartInfo){
		
		final Chart chart = new Chart()
		 .setWidth100()  
        .setType(Series.Type.COLUMN)  
        .setChartTitleText("Registered Users Last 6 Months")
        .setColumnPlotOptions(new ColumnPlotOptions()  
            .setPointPadding(0.2)  
            .setBorderWidth(0)
            .setColor("#337ab7")
        )  
        .setLegend(new Legend()  
            .setLayout(Legend.Layout.VERTICAL)  
            .setAlign(Legend.Align.LEFT)  
            .setVerticalAlign(Legend.VerticalAlign.TOP)  
            .setX(100)  
            .setY(70)  
            .setFloating(true)  
            .setBackgroundColor("#FFFFFF")  
            .setShadow(true)  
        )  
        .setToolTip(new ToolTip()  
            .setFormatter(new ToolTipFormatter() {  
                public String format(ToolTipData toolTipData) {  
                    return toolTipData.getXAsString() + ": " + toolTipData.getYAsLong();  
                }  
            })  
        );  

		 
		//months
	    chart.getXAxis()  
	         .setCategories(chartInfo.months);  
	
	    chart.getYAxis()  
	         .setAxisTitleText("Registered (Users)")  
	         .setMin(0);  

	    //total
	    chart.addSeries(chart.createSeries()  
	         .setName("Users")  
	         .setPoints(chartInfo.totals)  
	     );  
		lastmonthusers.add(chart);
		
		
	}
	private void ChartLastYearTopics(AdminChartResume chartInfo){
		 final Chart chart = new Chart()
		 .setWidth100()  
         .setType(Series.Type.COLUMN)  
         .setChartTitleText("Created Topic Last 6 Months")
         .setColumnPlotOptions(new ColumnPlotOptions()  
             .setPointPadding(0.2)  
             .setBorderWidth(0)
             .setColor("#5cb85c")
         )  
         .setLegend(new Legend()  
             .setLayout(Legend.Layout.VERTICAL)  
             .setAlign(Legend.Align.LEFT)  
             .setVerticalAlign(Legend.VerticalAlign.TOP)  
             .setX(100)  
             .setY(70)  
             .setFloating(true)  
             .setBackgroundColor("#FFFFFF")  
             .setShadow(true)  
         )  
         .setToolTip(new ToolTip()  
             .setFormatter(new ToolTipFormatter() {  
                 public String format(ToolTipData toolTipData) {  
                     return toolTipData.getXAsString() + ": " + toolTipData.getYAsLong();  
                 }  
             })  
         );  
		 
		//months
	    chart.getXAxis()  
	         .setCategories(chartInfo.getMonths());  
	
	    chart.getYAxis()  
	         .setAxisTitleText("Registered (Topics)")  
	         .setMin(0);  
	    //totals 
	    chart.addSeries(chart.createSeries()  
	         .setName("Topics")  
	         .setPoints(chartInfo.getTotals())  
	     );  
		lastmonthtopics.add(chart);
	}

}
