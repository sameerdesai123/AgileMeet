package com.agilemeet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.agilemeet.databaseutils.DatabaseConnection;

public class LoginDAO {
	
	private static final String LOGIN_USER = "SELECT * FROM `adminInfo` where uname=? and password=?";
	
	public String validate(String uname, String password){
		String res="";
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(LOGIN_USER)){
			ps.setString(1, uname);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				res = rs.getString("uname");
			}
			return res;
		}catch(Exception e){
			e.printStackTrace();
			return res;
		}
	}
}
