package com.agilemeet.model;

import java.util.List;

public class Meeting {
	private String dateStart = null;
	private String dateEnd  = null;
	private String organizerName  = null;
	private String organizerEmail  = null;
	private int teamSize;
	private String description  = null;
	private String title  = null;
	private String location  = null;
	private List<Attender> attendees;
	private int id;
	
	
	public Meeting() {
		super();
	}

	public Meeting(String dateStart, String dateEnd, String tmZone, String tmStart, String tmEnd, String organizerName,
			String organizerEmail, int teamSize, String description, String title, String location,
			List<Attender> attendees, int id) {
		super();
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.organizerName = organizerName;
		this.organizerEmail = organizerEmail;
		this.teamSize = teamSize;
		this.description = description;
		this.title = title;
		this.location = location;
		this.attendees = attendees;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Meeting [dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", organizerName=" + organizerName + ", organizerEmail="
				+ organizerEmail + ", teamSize=" + teamSize + ", description=" + description + ", title=" + title
				+ ", location=" + location + ", attendees=" + this.genString(attendees) + ", id=" + id + "]";
	}
	
	private String genString(List<Attender> attendee){
		String r = "";
		for(Attender i: attendee)
			r += i.toString() + "\n";
		return r;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Meeting(String dateStart, String dateEnd, String tmZone, String tmStart, String tmEnd, String organizerName,
			String organizerEmail, int teamSize, String description, String title, String location,
			List<Attender> attendees) {
		super();
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.organizerName = organizerName;
		this.organizerEmail = organizerEmail;
		this.teamSize = teamSize;
		this.description = description;
		this.title = title;
		this.location = location;
		this.attendees = attendees;
	}
	public List<Attender> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<Attender> attendees) {
		this.attendees = attendees;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	public String getOrganizerName() {
		return organizerName;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	public String getOrganizerEmail() {
		return organizerEmail;
	}
	public void setOrganizerEmail(String organizerEmail) {
		this.organizerEmail = organizerEmail;
	}
	public int getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize; // include organizer
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
