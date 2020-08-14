package com.agilemeet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.agilemeet.admin.model.DashBoardData;
import com.agilemeet.databaseutils.DatabaseConnection;

public class DashBoardDAO {
	private static final String GET_DATA = "SELECT * FROM `master` WHERE `master`.`mid`=?;";
	private static final String GET_UNIQ_ATTENDEES = "SELECT COUNT(*) as uCount FROM `uniqueattendees` WHERE 1;";
	private static final String GET_TASK = "SELECT `task` FROM `tasknotify` WHERE `tid`=1;";
	private static final String SET_TASK = "UPDATE `tasknotify` SET `task`=? WHERE `tid`=1;";
	
	public DashBoardData getData(){
		DashBoardData obj = new DashBoardData();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_DATA)){
			ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				obj.setUserCount(rs.getInt("userCount"));
				obj.setMeetingCount(rs.getInt("meetingCount"));
				obj.setActionCount(rs.getInt("actionCount"));
				obj.setLastUpdated(rs.getString("modified"));
			}
			obj.setUniqueAttendees(getUniqAttendees());
			return obj;
		}catch(Exception e){
			e.printStackTrace();
			return obj;
		}
	}
	
	public String getTask(){
		String task = "";
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
	
	public String setTask(String s) {
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SET_TASK)){
			ps.setString(1, s);
			ps.executeUpdate();
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return s;
		}
	}
	
	protected int getUniqAttendees(){
		int res = -1;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_UNIQ_ATTENDEES)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				res = rs.getInt("uCount");
			}
			return res;
		}catch(Exception e){
			e.printStackTrace();
			return res;
		}
	}
}
