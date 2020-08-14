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

import com.agilemeet.admin.dao.UserDAO;
import com.agilemeet.admin.model.Users;
import com.agilemeet.utils.AESEncryptionDecryption;

@WebServlet("/admin/users")
public class UsersAdminView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UsersAdminView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Logger logger = LoggerFactory.getLogger(UsersAdminView.class);
		try{
			UserDAO dao = new UserDAO(); 
			List<Users> users = dao.getData();
			session.setAttribute("users", users);
			logger.info("SHOWING USERS ADMIN VIEW ");
			response.sendRedirect(request.getContextPath() + "/admin/users.jsp");	
		}catch(Exception e){
			logger.error("FAILED USERS VIEW ADMIN : " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
