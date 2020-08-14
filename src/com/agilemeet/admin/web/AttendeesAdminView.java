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

import com.agilemeet.admin.dao.AttendeesDAO;
import com.agilemeet.admin.model.Attendees;

/**
 * Servlet implementation class AttendeesAdminView
 */
@WebServlet("/admin/attendees")
public class AttendeesAdminView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AttendeesAdminView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AttendeesDAO dao = new AttendeesDAO();
		Logger logger = LoggerFactory.getLogger(ActionItemsAdminView.class);
		try{
			List<Attendees> attendees = dao.getData();
			HttpSession session = request.getSession();
			session.setAttribute("attendees", attendees);
			response.sendRedirect(request.getContextPath() + "/admin/attendees.jsp");
		}catch(Exception e){
			logger.error("FAILED TO LOAD attendees ITEMS ADMIN VIEW : " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
