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

import com.agilemeet.admin.model.Users;
import com.agilemeet.utils.AESEncryptionDecryption;

@WebServlet("/admin/users/*")
public class SpecificUserView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AESEncryptionDecryption crypt = new AESEncryptionDecryption();   
    
	public SpecificUserView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger = LoggerFactory.getLogger(SpecificUserView.class);
		try{
			HttpSession session = request.getSession();
			int id = Integer.parseInt(request.getPathInfo().substring(1));
			List<Users> users = (List<Users>) session.getAttribute("users");
			session.setAttribute("user", getUser(id, users));
			response.sendRedirect(request.getContextPath() + "/admin/user-view.jsp");
		}catch(Exception e){
			logger.error("Failed to retrieve User : " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}
	}

	private Users getUser(int id, List<Users> users){
		Users user = null;
		try{
			for(Users u: users){
				if(u.getId() == id) {
					u.setPass(crypt.decrypt(u.getPass()));
					return u;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return user;
		}
		return user;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
