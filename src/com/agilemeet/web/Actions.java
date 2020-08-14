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

import com.agilemeet.dao.ActionsDAO;
import com.agilemeet.model.Action;
import com.agilemeet.model.User;

/**
 * Servlet implementation class Actions
 */
@WebServlet("/actions")
public class Actions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Actions() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger = LoggerFactory.getLogger(Actions.class);
		try{
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			ActionsDAO dao = new ActionsDAO(user.getId());
			List<Action> actions = dao.getItems();
			session.setAttribute("actions", actions);
			logger.info("CALLED");
			response.sendRedirect(request.getContextPath() + "/views/actions.jsp");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("FAILED DISPLAY  ACTIONS ");
			response.sendRedirect(request.getContextPath() + "/home.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
