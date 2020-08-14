package com.agilemeet.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemeet.dao.MeetingDAO;
import com.agilemeet.dao.MeetingPointsDAO;
import com.agilemeet.model.Attender;
import com.agilemeet.model.Meeting;
import com.agilemeet.model.MeetingPoints;
import com.agilemeet.model.User;
import com.agilemeet.utils.MailThreadPoolExecutor;

@WebServlet("/save-meeting")
public class SaveMeeting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(SaveMeeting.class);
    public SaveMeeting() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(404);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			MeetingDAO dao = new MeetingDAO(user.getId());
			
			Meeting meeting = (Meeting) session.getAttribute("meeting");
			meeting.setTitle(request.getParameter("title"));
			meeting.setDescription(request.getParameter("description"));
			meeting = dao.insertMeeting(meeting);
			
			MeetingPointsDAO mpDao = new MeetingPointsDAO(meeting.getId());
			
			String[] tasks = request.getParameterValues("meetingPointTasks");
			String[] actionItems = request.getParameterValues("actionItem");
			String[] assignees = request.getParameterValues("assignee");
			
			List<MeetingPoints> meetingPoints = generateMeetingPoints(meeting, tasks, actionItems, assignees);
			
			mpDao.insertMeetingPoints(meetingPoints);
			logger.info("ADDED NEW MEETING" + meeting.getId());
			
			List<Meeting> meetings =  dao.selectMyMeetings();
			session.setAttribute("myMeetings", meetings);
			
			logger.info("RETREIVED MEETINGS");
			
			this.sendMails(meeting, meetingPoints);
			
			response.sendRedirect(request.getContextPath() + "/");
		}catch(Exception e){
			logger.error("FAILURE SAVE MEETING " + e.getCause());
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

	private void sendMails(Meeting meeting, List<MeetingPoints> mps){
		String title = meeting.getTitle();
		List<Attender> attendees = meeting.getAttendees();
		Map<Integer, String> mapEmail = new HashMap<>();
		List<String> emails = new ArrayList<>();
		List<String> tasks = new ArrayList<>();
		for(Attender a: attendees){
			mapEmail.put(a.getId(), a.getEmail());
		}
		for(MeetingPoints mp: mps){
			if(mp.getAssignee() != 1){
				tasks.add(mp.getTask());
				emails.add(mapEmail.get(mp.getAssignee()));
			}
		}
		MailThreadPoolExecutor.runPool(title, meeting.getOrganizerEmail(), emails, tasks);
	}
	
	private List<MeetingPoints> generateMeetingPoints(Meeting meeting, String[] tasks, String[] actionItems, String[] assignees) {
		List<MeetingPoints> mps = new ArrayList<>();
		List<Attender> attenders = meeting.getAttendees();
		Map<String, String> mapEmail = new HashMap<>();
		Map<String, Integer> mapId = new HashMap<>(); 
		for(Attender a: attenders){
			mapEmail.put(a.getName(), a.getEmail());
			mapId.put(a.getName(), a.getId());
		}
		
		if((tasks.length != actionItems.length) || (tasks.length != assignees.length)){
			System.out.println("Returning Null");
			return mps;
		}
		else{
			for(int i=0;i<tasks.length;i++) {
				int ac = actionItems[i].equals("YES")? 1 : 0;
				int asId = 0;
				if(ac == 1){
					asId = assignees[i].equals("Not Assigned")? 0 : mapId.get(assignees[i]);
				}
				MeetingPoints mp = new MeetingPoints(meeting.getId(), tasks[i], ac, asId);
				mps.add(mp);
			}
			return mps;
		}
	}

}
