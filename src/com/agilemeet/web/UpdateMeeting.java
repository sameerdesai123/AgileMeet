package com.agilemeet.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

/**
 * Servlet implementation class UpdateMeeting
 */
@WebServlet("/edit-meeting")
public class UpdateMeeting extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(UpdateMeeting.class);
    private List<MeetingPoints> mpForMails = new ArrayList<>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMeeting() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(404);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			MeetingDAO dao = new MeetingDAO(user.getId());
			
			Meeting meeting = (Meeting) session.getAttribute("meeting");
			meeting.setTitle(request.getParameter("title"));
			meeting.setDescription(request.getParameter("description"));
			dao.updateMeeting(meeting);
			
			List<MeetingPoints> mpList = (List<MeetingPoints>) session.getAttribute("mpList");
			MeetingPointsDAO mpDao = new MeetingPointsDAO(meeting.getId());
			
			int[] mpIds = Arrays.stream(request.getParameterValues("meetingPointId")).mapToInt(Integer::parseInt).toArray();
			String[] tasks = request.getParameterValues("meetingPointTasks");
			String[] actionItems = request.getParameterValues("actionItem");
			String[] assignees = request.getParameterValues("assignee");
			
			List<MeetingPoints> meetingPoints = generateMeetingPoints(meeting, mpIds, tasks, actionItems, assignees);
			mpList = generateDeletionList(mpList, meetingPoints);
			mpDao.updateAllMeetingPoints(meetingPoints);
			mpDao.deleteAllMeetingPoints(mpList);
			
			List<Meeting> meetings =  dao.selectMyMeetings();
			session.setAttribute("myMeetings", meetings);
			
			this.sendMails(meeting, this.mpForMails);
			
			logger.info("UPDATE COMPLETE ", meeting.getId());
			response.sendRedirect(request.getContextPath() + "/");
		}catch(Exception e){
			logger.error("FAILURE UPDATION ", e.getCause());
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
	
	private List<MeetingPoints> generateDeletionList(List<MeetingPoints> a, List<MeetingPoints> b){
		List<MeetingPoints> mp = new ArrayList<>();
		int f = 1;
		
		try{
			for(MeetingPoints i: a){
				if(i.getId() == 0) continue;
				f = 1;
				for(MeetingPoints j: b){
					if(j.getId() == i.getId()) {
						f = 0;
						this.mpForMails.add(j);
						break;
					}
				}
				if(f == 1){
					mp.add(i);
				}
			}
			return mp;
		}catch(Exception e){
			e.printStackTrace();
			return mp;
		}
	}
	
	private List<MeetingPoints> generateMeetingPoints(Meeting meeting,int[] mpIds, String[] tasks, String[] actionItems, String[] assignees) {
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
				MeetingPoints mp = new MeetingPoints(mpIds[i], meeting.getId(), tasks[i], ac, asId);
				mps.add(mp);
			}
			return mps;
		}
	}

}
