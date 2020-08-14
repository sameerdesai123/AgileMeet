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

import com.agilemeet.admin.dao.ActionItemsDAO;
import com.agilemeet.admin.model.ActionItems;

/**
 * Servlet implementation class ActionItemsAdminView
 */
@WebServlet("/admin/action-items")
public class ActionItemsAdminView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ActionItemsAdminView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionItemsDAO dao = new ActionItemsDAO();
		Logger logger = LoggerFactory.getLogger(ActionItemsAdminView.class);
		try{
			List<ActionItems> actionItems = dao.getData();
			HttpSession session = request.getSession();
			session.setAttribute("actionItems", actionItems);
			response.sendRedirect(request.getContextPath() + "/admin/actionItems.jsp");
		}catch(Exception e){
			logger.error("FAILED TO LOAD ACTION ITEMS ADMIN VIEW : " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
