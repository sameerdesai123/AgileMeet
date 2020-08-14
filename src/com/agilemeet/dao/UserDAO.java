package com.agilemeet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.databaseutils.DatabaseConnection;
import com.agilemeet.model.User;


public class UserDAO {
	private static final String INSERT_USERS_SQL = "INSERT INTO `users`(`name`, `email`, `password`) VALUES (?, ?, ?);";
	private static final String UPDATE_USERS_SQL = "UPDATE `users` SET `name`=?,`email`=?,`password`=? WHERE `id`= ?;";
	private static final String SELECT_ALL_USERS = "SELECT * FROM `users`;";
	private static final String SELECT_USER_BY_ID = "SELECT `name`, `email`, `password` FROM `users` WHERE `id`=?;";
	private static final String DELETE_USERS_SQL = "DELETE from users where id=?;";
	private static final String LOGIN_USER = "SELECT * FROM `users` WHERE `email`=? AND `password`=?";
	private static final String GET_TASK = "SELECT `task` FROM `tasknotify` WHERE 1;";
	
	// Create or insert user
	
	public User validate(String email, String password){
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(LOGIN_USER)){
			User user = null;
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				email = rs.getString("email");
				user = new User(id,name, email);
			}
			return user;
		}catch(Exception e){
			e.printStackTrace();
			User a = null;
			return a;
		}
	}
	
	public String getTask(){
		String task = null;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_TASK)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				task = rs.getString("task");
			}
			return task;
		}catch(Exception e){
			e.printStackTrace();
			return task;
		}
	}
	
	public boolean insertUser(User user) throws SQLException{

		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_USERS_SQL)){
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// Update user
	public boolean updateUser(User user) throws SQLException{
		boolean rowUpdated = false;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_USERS_SQL)){
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getId());
			rowUpdated = ps.executeUpdate() > 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rowUpdated;
	}
	
	// select user by id
	public User selectUser(int id){
		User user = null;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID)){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				user = new User(id, name, email, password);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
	// select users
	public List<User> selectAllUsers(){
		List<User> users = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_ALL_USERS)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				users.add(new User(id, name, email, password));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return users;
	}
	
	// delete user
	public boolean deleteUser(int id) throws SQLException{
		boolean rowDeleted = false;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_USERS_SQL)){
			ps.setInt(1, id);
			rowDeleted = ps.executeUpdate() > 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rowDeleted;
	}
}
