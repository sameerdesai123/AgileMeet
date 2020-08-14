package com.agilemeet.model;

public class MeetingPoints {
	private int id;
	private int meeting_id;
	private String task;
	private int actionItem;
	private int assignee;
	
	public MeetingPoints() {
		super();
	}
	@Override
	public String toString() {
		return "MeetingPoints [id=" + id + ", meeting_id=" + meeting_id + ", task=" + task + ", actionItem="
				+ actionItem + ", assignee=" + assignee + "]";
	}
	public MeetingPoints(int id, int meeting_id, String task, int actionItem, int assignee) {
		super();
		this.id = id;
		this.meeting_id = meeting_id;
		this.task = task;
		this.actionItem = actionItem;
		this.assignee = assignee;
	}
	public MeetingPoints(int meeting_id, String task, int actionItem, int assignee) {
		super();
		this.meeting_id = meeting_id;
		this.task = task;
		this.actionItem = actionItem;
		this.assignee = assignee;
	}
	public MeetingPoints(int meeting_id, String task) {
		super();
		this.meeting_id = meeting_id;
		this.task = task;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMeeting_id() {
		return meeting_id;
	}
	public void setMeeting_id(int meeting_id) {
		this.meeting_id = meeting_id;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public int getActionItem() {
		return actionItem;
	}
	public void setActionItem(int actionItem) {
		this.actionItem = actionItem;
	}
	public int getAssignee() {
		return assignee;
	}
	public void setAssignee(int assignee) {
		this.assignee = assignee;
	}
	
}
