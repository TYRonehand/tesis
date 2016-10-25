package com.project.titulo.client.breadcrumb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.titulo.client.GoToUrl;

public class BreadWidget extends Composite {

	@UiField VerticalPanel  backbreadPanel;
	@UiField FlowPanel  breadPanel;

	//goto url
	public GoToUrl url = new GoToUrl();
	
	private static BreadWidgetUiBinder uiBinder = GWT
			.create(BreadWidgetUiBinder.class);

	interface BreadWidgetUiBinder extends UiBinder<Widget, BreadWidget> {
	}

	public BreadWidget( String option) {
		initWidget(uiBinder.createAndBindUi(this));
		backbreadPanel.setStyleName("breadcrumb");
		BreadSelection(option);
	}
	@SuppressWarnings("deprecation")
	private void BreadSelection(String option){
		
		//clean breadpanel
		breadPanel.clear();
		
		//home link
		InlineHyperlink HOME = new InlineHyperlink();
		HOME.setTargetHistoryToken("");
		HOME.setText(">Home");
		HOME.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				url.GoTo("HOME");
			}});
		
		//opciones url
		switch(option){
				
			case "PROFILE":
				breadPanel.add(HOME);
				InlineHyperlink profile = new InlineHyperlink();
				profile.setTargetHistoryToken("");
				profile.setText(">Profile");
				profile.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("PROFILE");
					}});
				breadPanel.add(profile);
				break;

			case "HOME":
				breadPanel.add(HOME);
				
				break;
				
			case "ADMIN":
				//home link
				InlineHyperlink ADMIN = new InlineHyperlink();
				ADMIN.setTargetHistoryToken("");
				ADMIN.setText(">Administration");
				ADMIN.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("ADMIN");
					}});
				breadPanel.add(ADMIN);
				
				break;		
				
			case "FILES":
				breadPanel.add(HOME);
				
				InlineHyperlink FILES = new InlineHyperlink();
				FILES.setTargetHistoryToken("");
				FILES.setText(">My Files");
				FILES.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("FILES");
					}});
				breadPanel.add(FILES);
				
				break;
				
			case "PLOT":
				breadPanel.add(HOME);
				
				InlineHyperlink PLOT = new InlineHyperlink();
				PLOT.setTargetHistoryToken("");
				PLOT.setText(">Plot");
				PLOT.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("PLOT");
					}});
				breadPanel.add(PLOT);
				
				break;
				
			case "METRIC":
				breadPanel.add(HOME);
				
				InlineHyperlink METRIC = new InlineHyperlink();
				METRIC.setTargetHistoryToken("");
				METRIC.setText(">Metric");
				METRIC.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("METRIC");
					}});
				breadPanel.add(METRIC);
				
				break;
				
			case "FORUM":
				breadPanel.add(HOME);
				
				InlineHyperlink FORUM = new InlineHyperlink();
				FORUM.setTargetHistoryToken("");
				FORUM.setText(">Forum");
				FORUM.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("FORUM");
					}});
				breadPanel.add(FORUM);
				
				break;
				
			case "FAQ":
				breadPanel.add(HOME);
				
				InlineHyperlink FAQ = new InlineHyperlink();
				FAQ.setTargetHistoryToken("");
				FAQ.setText(">FAQ");
				FAQ.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("FAQ");
					}});
				breadPanel.add(FAQ);
				
				break;
				
			case "TOPIC":
				breadPanel.add(HOME);
				
				InlineHyperlink FORUM2 = new InlineHyperlink();
				FORUM2.setTargetHistoryToken("");
				FORUM2.setText(">Forum");
				FORUM2.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("FORUM");
					}});
				breadPanel.add(FORUM2);
				
				InlineHyperlink TOPIC = new InlineHyperlink();
				TOPIC.setTargetHistoryToken("");
				TOPIC.setText(">Read Topic");
				TOPIC.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						url.GoTo("TOPIC");
					}});
				breadPanel.add(TOPIC);
				
				break;
				
			default:
				break;
		}
		
		
	}

}
