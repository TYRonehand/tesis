package com.project.titulo.client.Plot;

import java.util.List;

import org.moxieapps.gwt.highcharts.client.BaseChart;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Color;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;

import com.google.gwt.i18n.client.*;   

import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;  

import com.project.titulo.shared.TextToDouble;
public class PlotHightCharts {
	
	private String title;
	@SuppressWarnings("unused")
	private int dimension;
	private List<String> labelxyz;
	private List<String> categories;
	private List<TextToDouble> values;
	
	public PlotHightCharts(String tit, int dim, List<String> labxyz, List<String> cat,List<TextToDouble> val){
		this.title		=	tit;
		this.dimension	=	dim;
		this.labelxyz	=	labxyz;
		this.categories = 	cat;
		this.values 	= 	val;
	}
	
	// scatter chart 2D
	public Chart createChart2D() 
	{  
        final Chart chart = new Chart()  
            .setType(Series.Type.SCATTER)  
            .setZoomType(BaseChart.ZoomType.X_AND_Y)  
            .setChartTitleText(this.title)   
            .setLegend(new Legend()  
                .setLayout(Legend.Layout.VERTICAL)  
                .setAlign(Legend.Align.RIGHT)  
                .setVerticalAlign(Legend.VerticalAlign.TOP)  
                .setX(0)  
                .setY(70)  
                .setBorderWidth(1)  
                .setFloating(true)  
                .setBackgroundColor("#FFFFFF")  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return toolTipData.getXAsDouble() + ", " + toolTipData.getYAsDouble();  
                    }  
                })  
            )  
            .setScatterPlotOptions(new ScatterPlotOptions()  
                .setMarker(new Marker()  
                    .setRadius(5)  
                    .setHoverState(new Marker()  
                        .setEnabled(true)  
                        .setLineColor(new Color(100, 100, 100))  
                    )  
                )  
                .setHoverStateMarker(new Marker()  
                    .setEnabled(false)  
                )  
            );  
        chart.getXAxis()  
            .setAxisTitleText(this.labelxyz.get(0))  
            .setStartOnTick(true)  
            .setEndOnTick(true)  
            .setShowLastLabel(true);  
        chart.getYAxis()  
            .setAxisTitleText(this.labelxyz.get(1));  

        //colors (8 files max per user)
        String[] colors = {"#337ab7","#d9534f","#5bc0de","#5cb85c","#f0ad4e","#ff33cc","#800000","#006600"};
        
        //list of files
        for(int file=0;file<this.values.size();file++)//files
        {
        	//list of points to Matriz
        	Number[][] numbers = new Number[this.values.get(file).getListPoints().size()][2];
        	//list of points 
        	for(int point=0;point<this.values.get(file).getListPoints().size();point++)
        	{
        		//list of axis
        		for(int axi=0;axi<2;axi++)
        		{
        			numbers[point][axi] = this.values.get(file).getListPoints().get(point).getAxieIndex(axi);
        		}
        	}
        	//create serie
        	chart.addSeries(chart.createSeries()  
                    .setName(this.categories.get(file))  
                    .setPlotOptions(new ScatterPlotOptions()  
                        .setColor(colors[file])  
                    )  
                    .setPoints(numbers)  
                );  
        }
        return chart;  
    }  

	public Chart HyperAreaIndicator() {  
		  
        final Chart chart = new Chart()  
            .setType(Series.Type.AREA)  
            .setChartTitleText("Historic and Estimated Worldwide Population Growth by Region")  
            .setChartSubtitleText("Source: Wikipedia.org")  
            .setAreaPlotOptions(new AreaPlotOptions()  
                .setStacking(PlotOptions.Stacking.NORMAL)  
                .setLineColor("#666666")  
                .setLineWidth(1)  
                .setMarker(new Marker()  
                    .setLineWidth(1)  
                    .setLineColor("#666666")  
                )  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(  
                    new ToolTipFormatter() {  
                        public String format(ToolTipData toolTipData) {  
                            return toolTipData.getXAsDouble() + ": " +  
                                NumberFormat.getFormat("#,###").format(toolTipData.getYAsDouble()) + " millions";  
                        }  
                    }  
                )  
            );  
  
        chart.getXAxis()  
            .setCategories(  
                "1750", "1800", "1850", "1900", "1950", "1999", "2050"  
            )  
            .setTickmarkPlacement(XAxis.TickmarkPlacement.ON)  
            .setAxisTitleText(null);  
  
        chart.getYAxis()  
            .setAxisTitleText("Billions")  
            .setLabels(new YAxisLabels()  
                .setFormatter(  
                    new AxisLabelsFormatter() {  
                        public String format(AxisLabelsData axisLabelsData) {  
                            return String.valueOf(axisLabelsData.getValueAsLong() / 1000);  
                        }  
                    }  
                )  
            );  
  
        chart.addSeries(chart.createSeries()  
            .setName("lsa-0.fit")  
            .setPoints(new Number[] { 502, 635, 809, 947, 1402, 3634, 5268 })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("lsa-10.fit")  
            .setPoints(new Number[] { 106, 107, 111, 133, 221, 767, 1766 })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("lsa-20.fit")  
            .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("lsa-30.fit")  
            .setPoints(new Number[] { 18, 31, 54, 156, 339, 818, 1201 })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("lsa-40.fit")  
            .setPoints(new Number[] { 2, 2, 2, 6, 13, 30, 46 })  
        );  
  
        return chart;  
    }  
}
