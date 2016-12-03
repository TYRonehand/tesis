package com.project.titulo.client.Plot;

import java.util.ArrayList;
import java.util.List;







import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.MyStyle;
import com.project.titulo.client.ServerService;
import com.project.titulo.client.ServerServiceAsync;
import com.project.titulo.shared.ErrorVerify;
import com.project.titulo.shared.TextToDouble;
import com.project.titulo.shared.model.UserFile;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PlotLive extends Composite {
	//widgets
	@UiField VerticalPanel plotPanel;
	@UiField Button backBtn;
	/* style */
	private MyStyle ms = new MyStyle();
	// RPC
	private final ServerServiceAsync serverService = GWT.create(ServerService.class);
	
	private static PlotLiveUiBinder uiBinder = GWT
			.create(PlotLiveUiBinder.class);

	interface PlotLiveUiBinder extends UiBinder<Widget, PlotLive> {
	}

	public PlotLive(final int axis, final String title, final List<String> labelxyz, String cod_user) {
		
		initWidget(uiBinder.createAndBindUi(this));
		backBtn.addStyleName(ms.getButtonStyle(0));
		
		//get data
		serverService.getUserFilesPlot(cod_user, new AsyncCallback<List<UserFile>>(){

			@Override
			public void onFailure(Throwable caught) {
				ErrorVerify.getErrorAlert("offline");
			}

			@Override
			public void onSuccess(List<UserFile> result) {
				if(!result.isEmpty())
				{
					List<TextToDouble> pointsFile = new ArrayList<TextToDouble>();
					List<String> categories = new ArrayList<String>();
					
					for (UserFile file : result) {
						categories.add(file.getTitle());
						// text to double points
						TextToDouble set = new TextToDouble();
						set.create(file.getData(), axis);
						pointsFile.add(set);
					}
					//plot class
					PlotHightCharts mychart = new PlotHightCharts(title, axis, labelxyz, categories, pointsFile);
					//create widget
					if(axis==2)
						plotPanel.add(mychart.createChart2D());
					else
						ErrorVerify.getErrorAlert("baddimension");
					
				}else{
					plotPanel.add(new Label("No selected files"));
				}
			}});
	}

	
	
	@UiHandler("backBtn")
	void onPlotBtnClick(ClickEvent event) {
		Window.Location.reload();
	}
}
