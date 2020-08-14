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

@WebServlet("/actions/mark-as-complete/*")
public class MarkCompleted extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MarkCompleted() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger = LoggerFactory.getLogger(Actions.class);
		try{
			HttpSession session = request.getSession();
			int id = Integer.parseInt(request.getPathInfo().substring(1));
			User user = (User) session.getAttribute("user");
			ActionsDAO dao = new ActionsDAO(user.getId());
			if(dao.checkPoint(id)){
				dao.markAsDone(id);
			}else{
				throw new Exception();
			}
			List<Action> actions = dao.getItems();
			session.setAttribute("actions", actions);
			response.sendRedirect(request.getContextPath() + "/views/actions.jsp");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("FAILED Mark as done");
			response.sendRedirect(request.getContextPath() + "/views/actions.jsp");
		}
	}

}
