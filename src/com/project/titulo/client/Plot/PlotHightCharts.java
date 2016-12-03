package com.project.titulo.client.Plot;

import java.util.List;

import org.moxieapps.gwt.highcharts.client.BaseChart;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Color;
import org.moxieapps.gwt.highcharts.client.Frame;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Options3D;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;
import org.moxieapps.gwt.highcharts.client.plotOptions.ScatterPlotOptions;

import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.Points;
@SuppressWarnings("unused")
public class PlotHightCharts {
	
	
	private String title;
	private int dimension;
	private List<String> labelxyz;
	private List<String> categories;
	private List<TextToDouble> values;
	
	public PlotHightCharts(String tit, int dim, List<String> labxyz, List<String> cat,List<TextToDouble> val){
		this.title		=	tit;
		this.dimension	=	dim;
		this.labelxyz		=	labxyz;
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
                .setAlign(Legend.Align.LEFT)  
                .setVerticalAlign(Legend.VerticalAlign.TOP)  
                .setX(100)  
                .setY(100)  
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

        //series
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

	
}
