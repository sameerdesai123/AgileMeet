package com.agilemeet.admin.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class AdminLogout
 */
@WebServlet("/admin/logout")
public class AdminLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogout() {
        super();
        // TODO Auto-generated constructor stub
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
		Logger logger = LoggerFactory.getLogger(AdminLogout.class);
		try{
			String p = request.getContextPath() + "/admin";
			HttpSession session = request.getSession();
			session.setAttribute("admin-name", null);
			session.setAttribute("role", null);
			session.removeAttribute("admin-name");
			session.removeAttribute("role");
			session.invalidate();
			logger.info("ADMIN LOGGED OUT");
			response.sendRedirect(p);
		}catch(Exception e){
			logger.error("ADMIN LOGOUT FAILURE CAUSE: " + e.getMessage());
		}
	}

}
