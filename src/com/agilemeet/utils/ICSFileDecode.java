package com.agilemeet.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.agilemeet.model.Attender;
import com.agilemeet.model.Meeting;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

public class ICSFileDecode {
	public static Meeting parseICS(String fname) throws Exception{
		System.out.println("Inside ICS File Decode Method");
		ICSFileDecode cusr = new ICSFileDecode();
		File file = new File(fname);
		Meeting result = cusr.getCalInfoFromIcs(file);
		// System.out.println(result.toString());
		return result;
	}
	
	 public Meeting getCalInfoFromIcs(File file) throws Exception {
		 
		  Meeting meeting = new Meeting();
		  net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
		  calendar.getProperties().add(
		    new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		  calendar.getProperties().add(Version.VERSION_2_0);
		  calendar.getProperties().add(CalScale.GREGORIAN);
		  
		  List<Attender> team = new ArrayList<Attender>();
		  FileInputStream fin = new FileInputStream(file);
		  CalendarBuilder builder = new CalendarBuilder();
		  calendar = builder.build(fin);
		  for (Iterator<CalendarComponent> i = calendar.getComponents().iterator(); i.hasNext();) {
		   Component component = (Component) i.next();
		   for (Iterator<Property> j = component.getProperties().iterator(); j.hasNext();) {
		    try {
		     String startdate = null, enddate = null, title = null, organizerEmail =null, organizerName= null;
		     String attendName=null, attendEmail=null, location=null, description=null;
		     Property property = (Property) j.next();
		     if ("DTSTART".equals(property.getName())) {
		      startdate = property.getValue();
		      if(meeting.getDateStart() == null)
		    	  meeting.setDateStart(modifyDateLayoutOfIcs(startdate));
		     }
		     if ("DTEND".equals(property.getName())) {
		      enddate = property.getValue();
		      if(meeting.getDateEnd() == null)
		    	 meeting.setDateEnd(modifyDateLayoutOfIcs(enddate));
		     }
		     if ("SUMMARY".equals(property.getName())) {
		    	 title = property.getValue();
		      if(meeting.getTitle() == null)
		    	  meeting.setTitle(title);
		     }
		     if ("DESCRIPTION".equals(property.getName())) {
		    	 description = property.getValue();
		      if(meeting.getDescription() == null)
		    	  meeting.setDescription(description);;
		     }
		     if ("LOCATION".equals(property.getName())) {
		    	 location = property.getValue();
		      if(meeting.getLocation() == null)
		    	  meeting.setLocation(location);
		     }
		     if ("ORGANIZER".equals(property.getName())) {
		    	  organizerName = property.getParameter("CN").getValue().toString();
			      organizerEmail = property.getValue().toString();
			      if(meeting.getOrganizerName() == null){
			    	  meeting.setOrganizerName(organizerName);
			    	  meeting.setOrganizerEmail(organizerEmail.replace("MAILTO:", ""));
			      }
			 }
		     
		     if ("ATTENDEE".equals(property.getName())) {
		    	  attendName = property.getParameter("CN").getValue().toString();
			      attendEmail = property.getValue().toString();
			      team.add(new Attender(attendName, attendEmail.replace("MAILTO:", "")));
			 }
		    } catch (Exception ex) {
		    	ex.printStackTrace();
		    }
		   }
		  }
		  meeting.setAttendees(team);
		  meeting.setTeamSize(team.size());
		  return meeting;
		 }

		 private String modifyDateLayoutOfIcs(String inputDate) {
		  try {

		   SimpleDateFormat inDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		   java.util.Date fromDate = inDateFormat.parse(inputDate);

		   SimpleDateFormat outDateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		   return outDateForm.format(fromDate);
		  } catch (Exception e) {
		   return inputDate;
		  }

		 }
}
