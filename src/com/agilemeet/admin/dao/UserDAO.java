package com.agilemeet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.admin.model.Users;
import com.agilemeet.databaseutils.DatabaseConnection;

public class UserDAO {
    private static final String GET_USERS = "SELECT `id`, `name`, `email`, `password` FROM `users` WHERE 1;";
	private static final String UPDATE_USER = "UPDATE `users` SET `name`=?,`email`=?,`password`=? WHERE `id`=?;";
	private static final String DELETE_USER = "DELETE FROM `users` WHERE `id` = ?;";
	public List<Users> getData(){
		List<Users> users = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_USERS)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				users.add(new Users(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password")));
			}
			return users;
		}catch(Exception e){
			e.printStackTrace();
			return users;
		}
	}
	
	public boolean updateUser(Users u){
		int rowsUpdated = 0;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_USER)){
			ps.setString(1, u.getName());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getPass());
			ps.setInt(4, u.getId());
			rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;
		}catch(Exception e){
			e.printStackTrace();
			return rowsUpdated > 0;
		}
	}
	
	public boolean deleteUser(int id){
		int rowsUpdated = 0;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_USER)){
			ps.setInt(1, id);
			rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;
		}catch(Exception e){
			e.printStackTrace();
			return rowsUpdated > 0;
		}
	}
}
