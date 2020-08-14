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
import com.agilemeet.model.MeetingPoints;
import com.agilemeet.model.User;

@WebServlet("/meeting/*")

public class MeetingView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(MeetingView.class);
    public MeetingView() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,NumberFormatException {
		String mId = request.getPathInfo().substring(1);
		try{
			int meetingId = Integer.parseInt(mId);
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			MeetingDAO meetingDao = new MeetingDAO(user.getId());
			Meeting meeting = meetingDao.selectMeeting(meetingId);
			MeetingPointsDAO meetingPointsDao = new MeetingPointsDAO(meeting.getId());
			List<MeetingPoints> meetingPoints = meetingPointsDao.selectMeetingPoints();
			session.setAttribute("meeting", meeting);
			session.setAttribute("meetingPoints", meetingPoints);
			response.sendRedirect(request.getContextPath() + "/views/meeting.jsp");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("FAILED DISPLAY MEETING ID ", mId);
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
