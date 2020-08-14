package com.agilemeet.admin.web;

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

import com.agilemeet.admin.dao.MeetingInfoDAO;
import com.agilemeet.admin.model.MeetingInfo;

/**
 * Servlet implementation class MeetingInfoAdminView
 */
@WebServlet("/admin/meetings")
public class MeetingInfoAdminView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeetingInfoAdminView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MeetingInfoDAO dao = new MeetingInfoDAO();
		Logger logger = LoggerFactory.getLogger(MeetingInfoAdminView.class);
		try{
			List<MeetingInfo> meetings = dao.getData();
			HttpSession session = request.getSession();
			session.setAttribute("meetings", meetings);
			response.sendRedirect(request.getContextPath() + "/admin/meetings.jsp");
		}catch(Exception e){
			logger.error("FAILED TO LOAD MEETINGS ADMIN VIEW : " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
