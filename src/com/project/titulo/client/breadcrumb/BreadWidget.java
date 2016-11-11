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

	@UiField
	VerticalPanel backbreadPanel;
	@UiField
	FlowPanel breadPanel;


	private static BreadWidgetUiBinder uiBinder = GWT
			.create(BreadWidgetUiBinder.class);

	interface BreadWidgetUiBinder extends UiBinder<Widget, BreadWidget> {
	}

	public BreadWidget(String option) {
		initWidget(uiBinder.createAndBindUi(this));
		backbreadPanel.setStyleName("breadcrumb");
		BreadSelection(option);
	}

	private void BreadSelection(String option) {

		// clean breadpanel
		breadPanel.clear();

		// home link
		InlineHyperlink HOME = new InlineHyperlink();
		HOME.setTargetHistoryToken("home");
		HOME.setText(">Home");

		// opciones url
		switch (option.toUpperCase()) {

		case "PROFILE":
			breadPanel.add(HOME);
			InlineHyperlink profile = new InlineHyperlink();
			profile.setTargetHistoryToken("profile");
			profile.setText(">Profile");
			breadPanel.add(profile);
			break;

		case "HOME":
			breadPanel.add(HOME);

			break;

		case "ADMIN":
			// home link
			InlineHyperlink ADMIN = new InlineHyperlink();
			ADMIN.setTargetHistoryToken("admin");
			ADMIN.setText(">Administration");
			breadPanel.add(ADMIN);

			break;

		case "FILES":
			breadPanel.add(HOME);

			InlineHyperlink FILES = new InlineHyperlink();
			FILES.setTargetHistoryToken("files");
			FILES.setText(">My Files");
			breadPanel.add(FILES);

			break;

		case "PLOT":
			breadPanel.add(HOME);

			InlineHyperlink PLOT = new InlineHyperlink();
			PLOT.setTargetHistoryToken("plot");
			PLOT.setText(">Plot");
			breadPanel.add(PLOT);

			break;

		case "METRIC":
			breadPanel.add(HOME);

			InlineHyperlink METRIC = new InlineHyperlink();
			METRIC.setTargetHistoryToken("metric");
			METRIC.setText(">Metric");
			breadPanel.add(METRIC);

			break;

		case "FORUM":
			breadPanel.add(HOME);

			InlineHyperlink FORUM = new InlineHyperlink();
			FORUM.setTargetHistoryToken("forum");
			FORUM.setText(">Forum");
			breadPanel.add(FORUM);

			break;

		case "FAQ":
			breadPanel.add(HOME);

			InlineHyperlink FAQ = new InlineHyperlink();
			FAQ.setTargetHistoryToken("faq");
			FAQ.setText(">FAQ");
			breadPanel.add(FAQ);

			break;

		case "TOPIC":
			breadPanel.add(HOME);

			InlineHyperlink FORUM2 = new InlineHyperlink();
			FORUM2.setTargetHistoryToken("forum");
			FORUM2.setText(">Forum");
			breadPanel.add(FORUM2);

			InlineHyperlink TOPIC = new InlineHyperlink();
			TOPIC.setTargetHistoryToken("topic");
			TOPIC.setText(">Read Topic");
			breadPanel.add(TOPIC);

			break;

		default:
			break;
		}

	}

}
