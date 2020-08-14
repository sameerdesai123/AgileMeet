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

import com.agilemeet.admin.dao.DashBoardDAO;

/**
 * Servlet implementation class ChangeTask
 */
@WebServlet("/change-task")
public class ChangeTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeTask() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger = LoggerFactory.getLogger(ChangeTask.class);
		HttpSession session = request.getSession();
		try{
			DashBoardDAO dao = new DashBoardDAO();
			session.setAttribute("currentTask", dao.setTask(request.getParameter("taskUpdate")));
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}catch(Exception e){
			logger.error("FAILED TO UPDATE TASK : " + e.getMessage());
			session.setAttribute("message", "Task not updated. Try again!");
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}
	}

}
