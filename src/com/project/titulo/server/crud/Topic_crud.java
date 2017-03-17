package com.project.titulo.server.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.titulo.server.DataBaseProperties;
import com.project.titulo.shared.model.ResumeTopic;
import com.project.titulo.shared.model.Topic;

public class Topic_crud {
	
	  // add new topic in database
		public static Boolean create(Topic myTopic) throws IllegalArgumentException {

			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "INSERT INTO forum_topic (title_topic,description_topic,cod_user) "
						+ "VALUES ('"+ myTopic.getTitle()+ "','"+ myTopic.getDescription()
						+ "','"+ myTopic.getIduser()+ "')";

				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				// close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

		// get soome topic
		public static Topic read(String idtopic) throws IllegalArgumentException {

			Topic mytopic = null;
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "SELECT * FROM forum_topic WHERE cod_topic = '"+idtopic+"' LIMIT 1";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();

				// recorremos resultado
				while (result.next()) {
					mytopic = new Topic(result.getString("cod_topic"),
							result.getString("title_topic"),
							result.getString("description_topic"),
							result.getString("cod_user"),
							result.getString("creation_topic"),
							result.getString("banned_topic"),
							result.getString("edition_topic"),
							result.getString("comments_topic"));
				}
				// close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return mytopic;
		}

		// update topic
		public static Boolean update(Topic myTopic) throws IllegalArgumentException {

			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = " UPDATE forum_topic SET " + "title_topic='"
						+ myTopic.getTitle() + "', " + "description_topic='"
						+ myTopic.getDescription() + "' WHERE cod_topic= "
						+ myTopic.getIdtopic() + " ";

				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				// close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

		// get all resume topic
		public static List<ResumeTopic> NewestResumeTopic() throws IllegalArgumentException {

			List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "SELECT * FROM topic_resume_view ORDER BY creation_topic DESC";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				
				// recorremos resultado
				while (result.next()) {
					// add element to list
					resumetopic.add(new ResumeTopic(result.getString("cod_topic"),
							result.getString("title_topic"), result
									.getString("cod_user"), result
									.getString("name_user"), result
									.getString("total"), result
									.getString("creation_topic")));
				}
				// close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return resumetopic;
		}

		// get all resume topic
		public static List<ResumeTopic> OldestResumeTopic() throws IllegalArgumentException {

			List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "SELECT * FROM topic_resume_view ORDER BY creation_topic ASC";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				
				// recorremos resultado
				while (result.next()) {
					// add element to list
					resumetopic.add(new ResumeTopic(result.getString("cod_topic"),
							result.getString("title_topic"), result
									.getString("cod_user"), result
									.getString("name_user"), result
									.getString("total"), result
									.getString("creation_topic")));
				}
				// close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return resumetopic;
		}

		// get all resume topic
		public static List<ResumeTopic> MyResumeTopic(String iduser)
				throws IllegalArgumentException {

			List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "SELECT * FROM topic_resume_view WHERE cod_user = '"
						+ iduser + "' ORDER BY creation_topic ASC";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				
				// recorremos resultado
				while (result.next()) {
					// add element to list
					resumetopic.add(new ResumeTopic(result.getString("cod_topic"),
							result.getString("title_topic"), result
									.getString("cod_user"), result
									.getString("name_user"), result
									.getString("total"), result
									.getString("creation_topic")));
				}
				// close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return resumetopic;
		}

		// get all resume topic
		public static List<ResumeTopic> SearchResumeTopic(String specialword)
				throws IllegalArgumentException {

			List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "SELECT * FROM topic_resume_view "
						+ " WHERE UPPER(title_topic) LIKE '%"
						+ specialword.toUpperCase() + "%' "
						+ " ORDER BY creation_topic DESC";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				
				// recorremos resultado
				while (result.next()) {
					// add element to list
					resumetopic.add(new ResumeTopic(result.getString("cod_topic"),
							result.getString("title_topic"), result
									.getString("cod_user"), result
									.getString("name_user"), result
									.getString("total"), result
									.getString("creation_topic")));
				}
				
				// close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return resumetopic;
		}
}
