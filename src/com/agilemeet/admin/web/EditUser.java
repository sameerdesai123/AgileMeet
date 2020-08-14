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

import com.agilemeet.admin.dao.UserDAO;
import com.agilemeet.admin.model.Users;
import com.agilemeet.utils.AESEncryptionDecryption;

@WebServlet("/admin/users/edit")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AESEncryptionDecryption crypt = new AESEncryptionDecryption();
	
    public EditUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Logger logger = LoggerFactory.getLogger(DeleteUser.class);
		try{
			if(session.getAttribute("role").equals("admin")){
				UserDAO dao = new UserDAO();
				Users user = (Users) session.getAttribute("user");
				user.setName(request.getParameter("name"));
				user.setEmail(request.getParameter("email"));
				user.setPass(crypt.encrypt(request.getParameter("password")));
				dao.updateUser(user);
				logger.info("User Updated " + user.toString());
				response.sendRedirect(request.getContextPath() + "/admin/users.jsp");
			}else{
				throw new Exception();
			}
		}catch(Exception e){
			logger.error("FAILED TO UPDATE USER : " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}
	}

}
