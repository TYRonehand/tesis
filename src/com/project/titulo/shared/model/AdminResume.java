package com.project.titulo.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AdminResume implements IsSerializable  {

	private int  lastMonthUsers;
	private int  onlineUsers;
	private int  totalUsers;
	private int  totalTopics;
	private int  lastMonthTopic;
	private int  totaFiles;
	private double  totalSizeFiles;
	
	public AdminResume(){
		
	}



	public int getLastMonthUsers() {
		return lastMonthUsers;
	}


	public void setLastMonthUsers(int lastMonthUsers) {
		this.lastMonthUsers = lastMonthUsers;
	}


	public int getOnlineUsers() {
		return onlineUsers;
	}


	public void setOnlineUsers(int onlineUsers) {
		this.onlineUsers = onlineUsers;
	}


	public int getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}


	public int getTotalTopics() {
		return totalTopics;
	}


	public void setTotalTopics(int totalTopics) {
		this.totalTopics = totalTopics;
	}


	public int getLastMonthTopic() {
		return lastMonthTopic;
	}


	public void setLastMonthTopic(int lastMonthTopic) {
		this.lastMonthTopic = lastMonthTopic;
	}


	public int getTotaFiles() {
		return totaFiles;
	}


	public void setTotaFiles(int totaFiles) {
		this.totaFiles = totaFiles;
	}



	public double getTotalSizeFiles() {
		return totalSizeFiles;
	}



	public void setTotalSizeFiles(double totalSizeFiles) {
		this.totalSizeFiles = totalSizeFiles;
	}
	
}
