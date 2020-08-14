package com.agilemeet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.databaseutils.DatabaseConnection;
import com.agilemeet.model.Attender;
import com.agilemeet.model.Meeting;

public class MeetingDAO {
	int userId;
	
	public MeetingDAO(int userId){
		this.userId = userId;
	}
	
	private static final String INSERT_MEETING_SQL = "INSERT INTO `meetings`(`user_id`, `title`, `start`, `end`, `description`, `location`, `orgname`, `orgemail`, `teamsize`) VALUES (?,?,?,?,?,?,?,?,?);";
	private static final String UPDATE_MEETING_SQL = "UPDATE `meetings` SET `user_id`=?,`title`=?,`start`=?,`end`=?,`description`=?,`location`=?,`orgname`=?,`orgemail`=?,`teamsize`=? WHERE `id`=?;";
	private static final String SELECT_ALL_MEETINGS = "SELECT * FROM `meetings` WHERE 1;";
	private static final String SELECT_MEETING_BY_ID = "SELECT * FROM `meetings` WHERE `id`=? AND `meetings`.`user_id`=?;";
	private static final String DELETE_MEETING_SQL = "DELETE FROM `meetings` WHERE `id`=?;";
	private static final String SELECT_MY_MEETINGS = "SELECT * FROM `meetings` WHERE `user_id`=?;";
	private static final String SELECT_TEAM = "SELECT * FROM `attendees` WHERE `meeting_id`=?;";
	private static final String INSERT_TEAM = "INSERT INTO `attendees`(`meeting_id`, `name`, `email`) VALUES (?, ?, ?);";
	private static final String DELETE_TEAM = "DELETE from `attendees` WHERE `meeting_id`=?";
	// Create or insert user
	
	public Meeting insertMeeting(Meeting meeting) throws SQLException{

		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_MEETING_SQL, Statement.RETURN_GENERATED_KEYS)){
			int id = 0;
			ps.setInt(1, this.userId);
			ps.setString(2, meeting.getTitle());
			ps.setString(3, meeting.getDateStart());
			ps.setString(4, meeting.getDateEnd());
			ps.setString(5, meeting.getDescription());
			ps.setString(6, meeting.getLocation());
			ps.setString(7, meeting.getOrganizerName());
			ps.setString(8, meeting.getOrganizerEmail());
			ps.setInt(9, meeting.getTeamSize());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			if(putAttendees(id, meeting.getAttendees())){
				return selectMeeting(id);
			}
			return selectMeeting(id);
		}catch(Exception e){
			e.printStackTrace();
			return selectMeeting(0);
		}
	}
	
	// Update user
	public boolean updateMeeting(Meeting meeting) throws SQLException{
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_MEETING_SQL)){
			ps.setInt(1, this.userId);
			ps.setString(2, meeting.getTitle());
			ps.setString(3, meeting.getDateStart());
			ps.setString(4, meeting.getDateEnd());
			ps.setString(5, meeting.getDescription());
			ps.setString(6, meeting.getLocation());
			ps.setString(7, meeting.getOrganizerName());
			ps.setString(8, meeting.getOrganizerEmail());
			ps.setInt(9, meeting.getTeamSize());
			ps.setInt(10, meeting.getId());
			boolean rows = ps.executeUpdate() > 0;
			return rows;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// select meeting by id
	public Meeting selectMeeting(int id){
		Meeting meeting = new Meeting();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_MEETING_BY_ID)){
			ps.setInt(1, id);
			ps.setInt(2, this.userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				meeting.setId(rs.getInt("id"));
				meeting.setTitle(rs.getString("title"));
				meeting.setDateStart(rs.getString("start"));
				meeting.setDateEnd(rs.getString("end"));
				meeting.setDescription(rs.getString("description"));
				meeting.setTeamSize(rs.getInt("teamsize"));
				meeting.setLocation(rs.getString("location"));
				meeting.setOrganizerName(rs.getString("orgname"));
				meeting.setOrganizerEmail(rs.getString("orgemail"));
			}
			meeting.setAttendees(getAttendees(id));
			return meeting;
		}catch(Exception e){
			e.printStackTrace();
			return meeting;
		}
	}
	
	protected static boolean putAttendees(int meeting_id, List<Attender> attendees){
		try( Connection con = DatabaseConnection.getConnection();){
			for(Attender a: attendees){
				PreparedStatement ps = con.prepareStatement(INSERT_TEAM);
				ps.setInt(1, meeting_id);
				ps.setString(2, a.getName());
				ps.setString(3, a.getEmail());
				ps.executeUpdate();
				ps.closeOnCompletion();
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	protected static List<Attender> getAttendees(int meeting_id){
		List<Attender> a = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_TEAM)){
			ps.setInt(1, meeting_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				a.add(new Attender(name, email, id));
			}
			return a;
		}catch(Exception e){
			e.printStackTrace();
			return a;
		}
	}
	
	// select all meetings
	public List<Meeting> selectAllMeetings(){
		List<Meeting> meetings = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_ALL_MEETINGS)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Meeting meeting = new Meeting();
				meeting.setId(rs.getInt("id"));
				meeting.setDateStart(rs.getString("start"));
				meeting.setDateEnd(rs.getString("end"));
				meeting.setDescription(rs.getString("description"));
				meeting.setTeamSize(rs.getInt("teamsize"));
				meeting.setLocation(rs.getString("location"));
				meeting.setOrganizerName(rs.getString("orgname"));
				meeting.setOrganizerEmail(rs.getString("orgemail"));
				meetings.add(meeting);
			}
			return meetings;
		}catch(Exception e){
			e.printStackTrace();
		}
		return meetings;
	}
	
	public List<Meeting> selectMyMeetings(){
		List<Meeting> meetings = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_MY_MEETINGS)){
			ps.setInt(1, this.userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Meeting meeting = new Meeting();
				meeting.setId(rs.getInt("id"));
				meeting.setTitle(rs.getString("title"));
				meeting.setDateStart(rs.getString("start"));
				meeting.setDateEnd(rs.getString("end"));
				meeting.setDescription(rs.getString("description"));
				meeting.setTeamSize(rs.getInt("teamsize"));
				meeting.setLocation(rs.getString("location"));
				meeting.setOrganizerName(rs.getString("orgname"));
				meeting.setOrganizerEmail(rs.getString("orgemail"));
				meeting.setAttendees(getAttendees(meeting.getId()));
				meetings.add(meeting);
			}
			//System.out.println("Executed get: " + meetings.toString());
			return meetings;
		}catch(Exception e){
			e.printStackTrace();
		}
		return meetings;
	}
	
	private boolean deleteTeam(int id){
		boolean rowDeleted = false;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_TEAM)){
			ps.setInt(1, id);
			rowDeleted = ps.executeUpdate() > 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rowDeleted;
	}
	
	// delete user
	public boolean deleteMeeting(int id) throws SQLException{
		boolean rowDeleted = false;
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_MEETING_SQL)){
			ps.setInt(1, id);
			this.deleteTeam(id);
			rowDeleted = ps.executeUpdate() > 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rowDeleted;
	}
}
