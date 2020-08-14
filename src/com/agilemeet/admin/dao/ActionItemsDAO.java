package com.agilemeet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.agilemeet.admin.model.ActionItems;
import com.agilemeet.databaseutils.DatabaseConnection;

public class ActionItemsDAO {
	private static final String GET_AC_ITEMS = "SELECT `meetingId`, `task`, `assignee`, `modified` FROM `meeting_points` WHERE `meeting_points`.`action_item`=1;";
	
	public List<ActionItems> getData(){
		List<ActionItems> actionItems = new ArrayList<>();
		try( Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_AC_ITEMS)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				actionItems.add(new ActionItems(rs.getInt("meetingId"), rs.getString("task"), rs.getString("assignee"), rs.getString("modified")));
			}
			return actionItems;
		}catch(Exception e){
			e.printStackTrace();
			return actionItems;
		}
	}
}
