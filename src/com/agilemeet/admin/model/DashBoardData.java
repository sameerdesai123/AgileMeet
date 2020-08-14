package com.agilemeet.admin.model;

public class DashBoardData {
	private int userCount;
	private int meetingCount;
	private int actionCount;
	private int uniqueAttendees;
	private String lastUpdated;

	public DashBoardData() {
		super();
	}

	public DashBoardData(int userCount, int meetingCount, int actionCount, String lastUpdated) {
		super();
		this.userCount = userCount;
		this.meetingCount = meetingCount;
		this.actionCount = actionCount;
		this.lastUpdated = lastUpdated;
	}
	public DashBoardData(int userCount, int meetingCount, int actionCount, int uniqueAttendees, String lastUpdated) {
		super();
		this.userCount = userCount;
		this.meetingCount = meetingCount;
		this.actionCount = actionCount;
		this.uniqueAttendees = uniqueAttendees;
		this.lastUpdated = lastUpdated;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getMeetingCount() {
		return meetingCount;
	}
	public void setMeetingCount(int meetingCount) {
		this.meetingCount = meetingCount;
	}
	public int getActionCount() {
		return actionCount;
	}
	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}
	public int getUniqueAttendees() {
		return uniqueAttendees;
	}
	public void setUniqueAttendees(int uniqueAttendees) {
		this.uniqueAttendees = uniqueAttendees;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	@Override
	public String toString() {
		return "DashBoardData [userCount=" + userCount + ", meetingCount=" + meetingCount + ", actionCount="
				+ actionCount + ", uniqueAttendees=" + uniqueAttendees + ", lastUpdated=" + lastUpdated + "]";
	}
	
	
}
