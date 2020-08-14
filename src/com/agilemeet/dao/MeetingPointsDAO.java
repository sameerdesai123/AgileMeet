package com.agilemeet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.databaseutils.DatabaseConnection;
import com.agilemeet.model.MeetingPoints;

public class MeetingPointsDAO {
	
	int meetingId;
	
	public MeetingPointsDAO(int meetingId){
		this.meetingId = meetingId;
	}
	
	private static final String INSERT_MEETING_POINTS_SQL = "INSERT INTO `meeting_points`(`meetingId`, `task`, `action_item`, `assignee`) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_MEETING_POINTS_SQL = "UPDATE `meeting_points` SET `task`=?,`action_item`=?,`assignee`=? WHERE `id`=? AND `meetingId`=?;";
	private static final String SELECT_MEETINGS_POINTS = "SELECT * FROM `meeting_points` WHERE `meetingId`=?;";
	private static final String SELECT_MY_ACTION_ITEMS = "SELECT `meeting_points`.* FROM `meeting_points`, `meetings` WHERE `meetings`.`user_id`=? AND `meeting_points`.`meetingId`=`meetings`.`id` AND `meeting_points`.`action_item`=1;";
	private static final String DELETE_MEETING_POINTS_SQL = "DELETE FROM `meeting_points` WHERE `meeting_points`.meetingId =?;";
	private static final String DELETE_MEETING_POINT = "DELETE FROM `meeting_points` WHERE `id`=?;";
	
	public List<Integer> insertMeetingPoints(List<MeetingPoints> meetingPoints) throws SQLException{
		List<Integer> ids = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection();){
			for(MeetingPoints m: meetingPoints){
				PreparedStatement ps = con.prepareStatement(INSERT_MEETING_POINTS_SQL, Statement.RETURN_GENERATED_KEYS);
				if (m.getAssignee() == 0) {
					ps.setInt(4, 1);
				}
				ps.setInt(1, this.meetingId);
				ps.setString(2, m.getTask());
				ps.setInt(3, m.getActionItem());
				if(m.getAssignee() != 0){
					ps.setInt(4, m.getAssignee());
				}
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next())
					ids.add(rs.getInt(1));
				ps.closeOnCompletion();
			}
			return ids;
		}catch(Exception e){
			e.printStackTrace();
			return ids;
		}
	}
	
	public int insertMeetingPoint(MeetingPoints m) throws SQLException{
		int id = 0;
		try( Connection con = DatabaseConnection.getConnection();){
			PreparedStatement ps = con.prepareStatement(INSERT_MEETING_POINTS_SQL, Statement.RETURN_GENERATED_KEYS);
			if (m.getAssignee() == 0) {
				ps.setInt(4, 1);
			}
			ps.setInt(1, this.meetingId);
			ps.setString(2, m.getTask());
			ps.setInt(3, m.getActionItem());
			if(m.getAssignee() != 0){
				ps.setInt(4, m.getAssignee());
			}
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			ps.closeOnCompletion();
			return id;
		}catch(Exception e){
			e.printStackTrace();
			return id;
		}
	}
	
	// Update meeting point
	public boolean updateMeetingPoint(MeetingPoints meetingPoints) throws SQLException{
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_MEETING_POINTS_SQL)){
			ps.setInt(5, this.meetingId);
			ps.setInt(4, meetingPoints.getId());
			if (meetingPoints.getAssignee() == 0) {
				ps.setInt(3, 1);
				ps.setInt(2, 0);
			}else{
				ps.setInt(3, meetingPoints.getAssignee());
				ps.setInt(2, 1);
			}
			ps.setString(1, meetingPoints.getTask());	
			boolean rows = ps.executeUpdate() > 0;
			return rows;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// update List of meeting Points
	
	public boolean updateAllMeetingPoints(List<MeetingPoints> meetingPoints) throws SQLException{
		try{
			for(MeetingPoints mp: meetingPoints){
				if(mp.getId() == 0){
					this.insertMeetingPoint(mp);
				}else{
					this.updateMeetingPoint(mp);
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// select meeting by id
	public List<MeetingPoints> selectMeetingPoints(){
		List<MeetingPoints> meetingPoints = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_MEETINGS_POINTS)){
			ps.setInt(1, this.meetingId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MeetingPoints m = new MeetingPoints();
				m.setId(rs.getInt("id"));
				m.setTask(rs.getString("task"));
				m.setMeeting_id(rs.getInt("meetingId"));
				m.setAssignee(rs.getInt("assignee"));
				m.setActionItem(rs.getInt("action_item"));
				meetingPoints.add(m);
			}
			return meetingPoints;
		}catch(Exception e){
			e.printStackTrace();
			return meetingPoints;
		}
	}
	
	public List<MeetingPoints> selectMyActionItems(int userId){
		List<MeetingPoints> meetingPoints = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_MY_ACTION_ITEMS)){
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MeetingPoints m = new MeetingPoints();
				m.setId(rs.getInt("id"));
				m.setTask(rs.getString("task"));
				m.setMeeting_id(rs.getInt("meetingId"));
				m.setAssignee(rs.getInt("assignee"));
				m.setActionItem(rs.getInt("action_item"));
				meetingPoints.add(m);
			}
			return meetingPoints;
		}catch(Exception e){
			e.printStackTrace();
		}
		return meetingPoints;
	}
	
	public boolean deleteAllMeetingPoints(List<MeetingPoints> meetingPoints) throws SQLException{
		try{
			for(MeetingPoints mp: meetingPoints){
				this.deleteMeetingPoint(mp.getId());
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// delete all points of a meeting
	public boolean deleteAllMeetingPoints() throws SQLException{
		boolean rowDeleted = false;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_MEETING_POINTS_SQL)){
			ps.setInt(1, this.meetingId);
			rowDeleted = ps.executeUpdate() > 0;
			return rowDeleted;
		}catch(Exception e){
			e.printStackTrace();
			return rowDeleted;
		}
	}
	
	// delete all points of a meeting
		public boolean deleteMeetingPoint(int id) throws SQLException{
			boolean rowDeleted = false;
			try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_MEETING_POINT)){
				ps.setInt(1, id);
				rowDeleted = ps.executeUpdate() > 0;
				return rowDeleted;
			}catch(Exception e){
				e.printStackTrace();
				return rowDeleted;
			}
		}
}
