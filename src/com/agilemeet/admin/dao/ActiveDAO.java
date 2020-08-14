package com.agilemeet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.agilemeet.databaseutils.DatabaseConnection;

public class ActiveDAO {
	private static final String GET_DATES = "SELECT `modified` FROM `meeting_points` WHERE 1;";
	
	public List<Integer> getDaysOfWeek(){
		List<Integer> map = new ArrayList<>();
		for(int i=0;i<7;i++)
			map.add(0);
		
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_DATES)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String date = rs.getString("modified").substring(0, 9);
				Calendar c = Calendar.getInstance();
				c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				map.set(dayOfWeek-1, map.get(dayOfWeek-1) + 1);
			}
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return map;
		}
	}
}
