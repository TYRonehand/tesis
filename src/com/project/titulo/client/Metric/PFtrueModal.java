package com.project.titulo.client.Metric;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PFtrueModal extends Composite {

	private static PFtrueModalUiBinder uiBinder = GWT
			.create(PFtrueModalUiBinder.class);

	interface PFtrueModalUiBinder extends UiBinder<Widget, PFtrueModal> {
	}

	public PFtrueModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
