package com.project.titulo.client.notfound;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NotfoundWidget extends Composite {

	@UiField VerticalPanel panel404;
	private static NotfoundWidgetUiBinder uiBinder = GWT
			.create(NotfoundWidgetUiBinder.class);

	interface NotfoundWidgetUiBinder extends UiBinder<Widget, NotfoundWidget> {
	}

	public NotfoundWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		panel404.setHeight((Window.getClientHeight()*.8)+"px");
	}

}
