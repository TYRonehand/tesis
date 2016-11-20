package com.project.titulo.client.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FooterWidget extends Composite {

	private static FooterWidgetUiBinder uiBinder = GWT
			.create(FooterWidgetUiBinder.class);

	interface FooterWidgetUiBinder extends UiBinder<Widget, FooterWidget> {
	}

	public FooterWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
