package com.project.titulo.server.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.titulo.server.DataBaseProperties;
import com.project.titulo.server.helpers.EmailAlert;
import com.project.titulo.shared.model.AdminChartResume;
import com.project.titulo.shared.model.AdminResume;
import com.project.titulo.shared.model.User;

public class Admin_crud {
	
	public static AdminResume ResumeInfo() throws IllegalArgumentException {

		AdminResume info = new AdminResume();
		try {
			//ask por conection
			Connection conn = DataBaseProperties.CreateConn();
			
			//query
			String Query = "select * from admin_info_resume";
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			
			// get fields
			result.next();
			info.setLastMonthUsers(result.getInt("value"));
			result.next();
			info.setOnlineUsers(result.getInt("value"));
			result.next();
			info.setTotalUsers(result.getInt("value"));
			result.next();
			info.setTotalTopics(result.getInt("value"));
			result.next();
			info.setLastMonthTopic(result.getInt("value"));
			result.next();
			info.setTotaFiles(result.getInt("value"));
			result.next();
			info.setTotalSizeFiles(result.getDouble("value"));
			
			//close elements
			result.close();
			ps.close();
			conn.close();
			
		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}
		
		return info;
	}
	
	public static AdminChartResume ChartUsers() throws IllegalArgumentException {


		AdminChartResume info = new AdminChartResume();
		try {
			//ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			//query
			String Query = "select * from admin_chart_users";
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();

			// get fields
			int i = 6;
			while (result.next()) {
				info.months[i] = result.getString("month");
				info.totals[i] = result.getInt("users");
				i--;
			}

			//close elements
			result.close();
			ps.close();
			conn.close();
		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}
		return info;
	}
	
	public static AdminChartResume ChartTopics() throws IllegalArgumentException {


		AdminChartResume info = new AdminChartResume();
		try {
			//ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			//query
			String Query = "select * from admin_chart_topics";
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();

			// get fields
			int i = 6;
			while (result.next()) {
				info.months[i] = result.getString("month");
				info.totals[i] = result.getInt("topics");
				i--;
			}

			//close elements
			result.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}

		return info;
	}
	
	public static User authenticateAdmin(String user, String pass) throws IllegalArgumentException {

		User miuser = null;

		try {
			//ask por conection
			Connection conn = DataBaseProperties.CreateConn();
			
			// consultamos a base de datos
			String Query = "SELECT * FROM account_administrator WHERE mail_admin = '"
					+ user.toUpperCase()
					+ "' AND password_admin = '"
					+ pass
					+ "' LIMIT 1";
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			
			// get fields
			while (result.next()) {

				miuser = new User(result.getString("cod_admin"),
						result.getString("mail_admin"),
						result.getString("name_admin"),
						result.getString("lastname_admin"), null,
						"Administrator", null, "ASMOP",
						result.getString("password_admin"), null, null,null);
			}
			
			//send admin email debugg
			if(miuser!=null){
				String MessageEmail = "<h1>Recent Admin Log In From:</h1><p><span><b>Admin: </b></span> "+miuser.getName()+" "+miuser.getLastname()+" </p><p><span><b>Email: </b></span>  "+miuser.getMail()+"</p>";
				EmailAlert.WarningEmail("ServerServiceImpl - authenticateAdmin", MessageEmail);
			}else{
				String MessageEmail = "<h2>Intent for log on ADMINISTRATION</h2><br><p>user:"+user+"</p><p>pass:"+pass+"</p>";
				EmailAlert.WarningEmail("ServerServiceImpl - authenticateAdmin", MessageEmail);
			}
			
			result.close();
			ps.close();
			conn.close();
		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}

		return miuser;
	}
	
}
