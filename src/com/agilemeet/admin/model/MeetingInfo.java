package com.agilemeet.admin.model;

public class MeetingInfo {
	private int id;
	private String title;
	private String userName;
	public MeetingInfo() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}

	public MeetingInfo(int id, String title, String userName) {
		super();
		this.id = id;
		this.title = title;
		this.userName = userName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
