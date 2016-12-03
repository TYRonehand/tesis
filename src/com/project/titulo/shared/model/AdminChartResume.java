package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AdminChartResume implements IsSerializable  {

	public String[] months;
	public Number[] totals;
	
	public AdminChartResume(){
		months = new String[7];
		totals = new Number[7];
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public Number[] getTotals() {
		return totals;
	}

	public void setTotals(Number[] totals) {
		this.totals = totals;
	}
	
	
	
	
}
