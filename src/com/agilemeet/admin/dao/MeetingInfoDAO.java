package com.agilemeet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.admin.model.MeetingInfo;
import com.agilemeet.databaseutils.DatabaseConnection;

public class MeetingInfoDAO {
	private static final String GET_DATA = "SELECT M.`id` as id ,M.`title` as title, U.`name` as name FROM `meetings` M, `users` U WHERE U.`id`=M.`user_id`;";

	public List<MeetingInfo> getData(){
		List<MeetingInfo> meeting = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_DATA)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				meeting.add(new MeetingInfo(rs.getInt("id"), rs.getString("title"), rs.getString("name")));
			}
			return meeting;
		}catch(Exception e){
			e.printStackTrace();
			return meeting;
		}
	}
}
