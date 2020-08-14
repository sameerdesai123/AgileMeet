package com.agilemeet.web;

import java.io.IOException;
import java.util.List;

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
import com.agilemeet.model.Meeting;
import com.agilemeet.model.User;

/**
 * Servlet implementation class DeleteMeeting
 */
@WebServlet("/delete-meeting")
public class DeleteMeeting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMeeting() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Logger logger = LoggerFactory.getLogger(DeleteMeeting.class);
		try{
			User user = (User) session.getAttribute("user");
			MeetingDAO dao = new MeetingDAO(user.getId());
			
			Meeting meeting = (Meeting) session.getAttribute("meeting");
			MeetingPointsDAO mpDao = new MeetingPointsDAO(meeting.getId());
			mpDao.deleteAllMeetingPoints();
			dao.deleteMeeting(meeting.getId());
			logger.info("DELETED MEETING " + meeting.getTitle());
			
			List<Meeting> meetings =  dao.selectMyMeetings();
			session.setAttribute("myMeetings", meetings);
			
			logger.info("RETREIVED MEETINGS");
			
			response.sendRedirect(request.getContextPath() + "/");
		}catch(Exception e){
			logger.error("FAILED MEETING DELETION " + e.getMessage());
		}
	}

}
