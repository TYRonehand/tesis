package com.project.titulo.client.faq;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FAQWidget extends Composite {

	private static FAQWidgetUiBinder uiBinder = GWT
			.create(FAQWidgetUiBinder.class);

	interface FAQWidgetUiBinder extends UiBinder<Widget, FAQWidget> {
	}

	public FAQWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
