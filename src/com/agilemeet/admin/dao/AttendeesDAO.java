package com.agilemeet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.admin.model.Attendees;
import com.agilemeet.databaseutils.DatabaseConnection;

public class AttendeesDAO {
private static final String GET_ATTENDEES = "SELECT * FROM `uniqueattendees` WHERE 1;";
	
	public List<Attendees> getData(){
		List<Attendees> attendees = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_ATTENDEES)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				attendees.add(new Attendees(rs.getString("name"), rs.getString("email")));
			}
			return attendees;
		}catch(Exception e){
			e.printStackTrace();
			return attendees;
		}
	}
}
