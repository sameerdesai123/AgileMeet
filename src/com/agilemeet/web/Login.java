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
import com.agilemeet.dao.UserDAO;
import com.agilemeet.model.Meeting;
import com.agilemeet.model.User;
import com.agilemeet.utils.AESEncryptionDecryption;

/**
 * Servlet implementation class Login
 */

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AESEncryptionDecryption aesObj = new AESEncryptionDecryption();
    private UserDAO userDao = new UserDAO();
    
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger = LoggerFactory.getLogger(Login.class);
		try{
			String email = request.getParameter("email");
			String password = aesObj.encrypt(request.getParameter("password"));
			User user = userDao.validate(email, password);
			if(user != null){
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				MeetingDAO meetingDao = new MeetingDAO(user.getId());
				List<Meeting> meetings =  meetingDao.selectMyMeetings();
				session.setAttribute("myMeetings", meetings);
				logger.info("USER LOGIN " +  user.getEmail());
				session.setAttribute("status", "INFO");
				session.setAttribute("msg", userDao.getTask());
				response.sendRedirect(request.getContextPath() + "/views/home.jsp");
			} else{
				HttpSession session = request.getSession();
				session.setAttribute("msg", "Login Failed, credentials mismatch");
				session.setAttribute("status", "DANGER");
				logger.info("USER LOGIN FAILED"  + email);
				response.sendRedirect(request.getContextPath() +"/views/home.jsp");
			}
		}catch(Exception e){
			logger.error("LOGIN FAILURE CAUSE: " +  e.getCause());
		}
	}

}
