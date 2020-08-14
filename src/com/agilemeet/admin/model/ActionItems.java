package com.agilemeet.admin.model;

public class ActionItems {
	private int meetingId;
	private String task;
	private String assignee;
	private String modified;
	public ActionItems(int meetingId, String task, String assignee, String modified) {
		super();
		this.meetingId = meetingId;
		this.task = task;
		this.assignee = assignee;
		this.modified = modified;
	}
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	
	
}
