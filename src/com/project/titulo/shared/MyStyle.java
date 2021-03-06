package com.project.titulo.shared;

import com.google.gwt.user.client.ui.RootPanel;

public class MyStyle {

	public MyStyle() {

	}

	public String getButtonStyle(int option) {
		switch (option) {
		case 0:
			return "mybuttonstyle";
		case 1:
			return "buttonAdd";
		case 2:
			return "buttonRemove";
		case 3:
			return "buttonView";
		case 4:
			return "buttonDelete";
		default:
			return "btn btn-primary";
		}
	}

	public String getOkStyle() {
		return "btn btn-success";
	}

	public String getCancelStyle() {
		return "btn btn-danger";
	}

	public void loadBackgorundStyle(String option) {
		switch (option) {
		case "0":
			RootPanel.getBodyElement().getStyle().clearBackgroundColor();
			RootPanel.getBodyElement().getStyle().clearBackgroundImage();
			RootPanel.getBodyElement().getStyle().setBackgroundColor("#C5C9D7");
			break;
		case "1":
			RootPanel.getBodyElement().getStyle().clearBackgroundColor();
			RootPanel.getBodyElement().getStyle().clearBackgroundImage();
			RootPanel.getBodyElement().getStyle().setBackgroundColor("#D7BBBB");
			break;
		case "2":
			RootPanel.getBodyElement().getStyle().clearBackgroundColor();
			RootPanel.getBodyElement().getStyle().clearBackgroundImage();
			RootPanel.getBodyElement().getStyle().setBackgroundColor("#BCD7BB");
			break;
		case "3":
			RootPanel.getBodyElement().getStyle().clearBackgroundColor();
			RootPanel.getBodyElement().getStyle().clearBackgroundImage();
			RootPanel.getBodyElement().getStyle().setBackgroundColor("#D3D0A1");
			break;
		case "4":
			RootPanel.getBodyElement().getStyle().clearBackgroundColor();
			RootPanel.getBodyElement().getStyle().clearBackgroundImage();
			RootPanel
					.getBodyElement()
					.getStyle()
					.setBackgroundImage(
							"background: #00000 url('assets/img/wallpaper/home.png') no-repeat right top");
			break;
		default:
			RootPanel.getBodyElement().getStyle().clearBackgroundColor();
			RootPanel.getBodyElement().getStyle().clearBackgroundImage();
			RootPanel.getBodyElement().getStyle().setBackgroundColor("#CECECE");
			break;
		}
	}

}
