package com.project.titulo.client.breadcrumb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BreadWidget extends Composite {

	@UiField VerticalPanel  backbreadPanel;
	@UiField FlowPanel  breadPanel;
	
	private static BreadWidgetUiBinder uiBinder = GWT
			.create(BreadWidgetUiBinder.class);

	interface BreadWidgetUiBinder extends UiBinder<Widget, BreadWidget> {
	}

	public BreadWidget( String option) {
		initWidget(uiBinder.createAndBindUi(this));
		backbreadPanel.setStyleName("breadcrumb");
		BreadSelection(option);
	}
	private void BreadSelection(String option){
		
		//clean breadpanel
		breadPanel.clear();
		
		//home link
		InlineHyperlink home = new InlineHyperlink();
		home.setTargetHistoryToken("");
		home.setText(">Home");
		
		//opciones url
		switch(option){
				
			case "PROFILE":
				breadPanel.add(home);
				InlineHyperlink profile = new InlineHyperlink();
				profile.setTargetHistoryToken("");
				profile.setText(">Profile");
				breadPanel.add(profile);
				break;

			case "HOME":
				breadPanel.add(home);
				
				break;
				
			case "ADMIN":
				breadPanel.add(home);
				
				break;		
				
			case "FILES":
				breadPanel.add(home);
				
				InlineHyperlink FILES = new InlineHyperlink();
				FILES.setTargetHistoryToken("");
				FILES.setText(">My Files");
				breadPanel.add(FILES);
				
				break;
				
			case "PLOT":
				breadPanel.add(home);
				
				InlineHyperlink PLOT = new InlineHyperlink();
				PLOT.setTargetHistoryToken("");
				PLOT.setText(">Plot");
				breadPanel.add(PLOT);
				
				break;
				
			case "METRIC":
				breadPanel.add(home);
				
				InlineHyperlink METRIC = new InlineHyperlink();
				METRIC.setTargetHistoryToken("");
				METRIC.setText(">Metric");
				breadPanel.add(METRIC);
				
				break;
				
			case "FORUM":
				breadPanel.add(home);
				
				InlineHyperlink FORUM = new InlineHyperlink();
				FORUM.setTargetHistoryToken("");
				FORUM.setText(">Forum");
				breadPanel.add(FORUM);
				
				break;
				
			case "FAQ":
				breadPanel.add(home);
				
				InlineHyperlink FAQ = new InlineHyperlink();
				FAQ.setTargetHistoryToken("");
				FAQ.setText(">FAQ");
				breadPanel.add(FAQ);
				
				break;
				
			case "TOPIC":
				breadPanel.add(home);
				
				InlineHyperlink FORUM2 = new InlineHyperlink();
				FORUM2.setTargetHistoryToken("");
				FORUM2.setText(">Forum");
				breadPanel.add(FORUM2);
				
				InlineHyperlink TOPIC = new InlineHyperlink();
				TOPIC.setTargetHistoryToken("");
				TOPIC.setText(">Read Topic");
				breadPanel.add(TOPIC);
				
				break;
				
			default:
				break;
		}
		
		
	}

}
