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

import com.agilemeet.admin.dao.ActiveDAO;
import com.agilemeet.admin.dao.DashBoardDAO;
import com.agilemeet.admin.dao.LoginDAO;
import com.agilemeet.admin.model.DashBoardData;
import com.agilemeet.utils.AESEncryptionDecryption;

@WebServlet("/admin/login")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AESEncryptionDecryption aesObj = new AESEncryptionDecryption();
       
    public AdminLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Logger logger = LoggerFactory.getLogger(AdminLogin.class);
		try{
			LoginDAO dao = new LoginDAO();
			DashBoardDAO dashDao = new DashBoardDAO(); 
			ActiveDAO acDao = new ActiveDAO();
			String uname = aesObj.encrypt(request.getParameter("uname"));
			String pass = aesObj.encrypt(request.getParameter("pass"));
			if(dao.validate(uname, pass).length() > 0){
				logger.info("SUCESSFUL ADMIN LOGIN");
				session.setAttribute("admin-name", uname);
				session.setAttribute("role", "admin");
				DashBoardData data = (DashBoardData) dashDao.getData();
				session.setAttribute("currentTask", dashDao.getTask());
				session.setAttribute("dashData", data);
				session.setAttribute("graphData", acDao.getDaysOfWeek());
				
				response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
			}else{
				logger.error("ADMIN LOGIN FAILED");
				response.sendRedirect(request.getContextPath() + "/");
			}
		}catch(Exception e){
			logger.error("ADMIN LOGIN EXCEPTION " + e.getMessage());
		}
	}

}
