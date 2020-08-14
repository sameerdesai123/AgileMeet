package com.agilemeet.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemeet.dao.UserDAO;
import com.agilemeet.model.User;
import com.agilemeet.utils.AESEncryptionDecryption;

/**
 * Servlet implementation class Register
 */

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDao = new UserDAO(); 
    private AESEncryptionDecryption aesObj = new AESEncryptionDecryption();
    private Logger logger = LoggerFactory.getLogger(Register.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Add user to Database
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = aesObj.encrypt(request.getParameter("password"));
		User newUser = new User(name, email, password);
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = request.getRequestDispatcher("/");
		try {
			if(userDao.insertUser(newUser)){
				logger.info("REGISTERED NEW USER ", newUser.getEmail());
				session.setAttribute("status", "SUCCESS");
				session.setAttribute("msg", "Successfully registered!");
				dispatcher.forward(request, response);
			} else{
				session = request.getSession();
				session.setAttribute("status", "DANGER");
				session.setAttribute("msg", "Failed to create account, email already registered");
				logger.error("FAILED TO REGISTER :: email already registered");
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			logger.error("FAILED TO REGISTER ", e.getCause());
		}
	}
}
