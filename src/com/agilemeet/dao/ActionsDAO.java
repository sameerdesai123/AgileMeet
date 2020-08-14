package com.agilemeet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.databaseutils.DatabaseConnection;
import com.agilemeet.model.Action;

public class ActionsDAO {
	int userId;
	
	public ActionsDAO(int userId){
		this.userId = userId;
	}
	
	private static final String GET_ACTION_ITEMS = "SELECT `meeting_points`.`id` AS `id`, `task`, `completed` FROM `meeting_points`, `users`, `meetings` WHERE `meeting_points`.`meetingId`=`meetings`.`id` AND `meetings`.`user_id` = `users`.`id` AND `meeting_points`.`action_item`=1 AND `users`.`id`= ? AND `meeting_points`.`completed` = 0;";
	private static final String MARK = "UPDATE `meeting_points` SET `action_item`= 0,`completed`= 1 WHERE `id`= ?;";
	private static final String CHECK = "SELECT `meeting_points`.`id` AS `id` FROM `meeting_points`, `meetings`, `users` WHERE `users`.`id`=`meetings`.`user_id` AND `meetings`.`id`=`meeting_points`.`meetingId` AND `users`.`id`=? AND `meeting_points`.`id`=?;";
	public List<Action> getItems() throws SQLException{
		List<Action> actions = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_ACTION_ITEMS)){
			ps.setInt(1, this.userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				actions.add(new Action(rs.getInt("id") ,rs.getString("task"), rs.getInt("completed")));
			}
			return actions;
		}catch(Exception e){
			e.printStackTrace();
			return actions;
		}
	}
	
	public void markAsDone(int id) {
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(MARK)){
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean checkPoint(int id){
		int rows= 0;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(CHECK)){
			ps.setInt(1, this.userId);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				rows += 1; 
			return rows > 0;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
