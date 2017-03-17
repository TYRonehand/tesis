package com.project.titulo.server.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.titulo.server.DataBaseProperties;
import com.project.titulo.shared.model.Answer;

public class Comment_crud {
	// add new answer to topic
		public static Boolean create(Answer myAnswer) throws IllegalArgumentException {

			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "INSERT INTO forum_comment (description_comment,cod_topic,cod_user) VALUES ('"
						+ myAnswer.getDescription()
						+ "','"
						+ myAnswer.getIdtopic()
						+ "','" + myAnswer.getIduser() + "')";
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

		// get some comment
		public static List<Answer> read(String idtopic) throws IllegalArgumentException {

			List<Answer> myComments = new ArrayList<Answer>();
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "SELECT * FROM forum_comment WHERE cod_topic="
						+ idtopic + " ORDER BY creation_comment ASC";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				
				// recorremos resultado
				while (result.next()) {
					myComments.add(new Answer(result.getString("cod_comment"),
							result.getString("cod_topic"), result
									.getString("description_comment"), result
									.getString("creation_comment"), result
									.getString("edition_comment"), result
									.getString("response_comment"), result
									.getString("cod_user")));
				}
				
				// close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return myComments;
		}

		// set a comment
		public static Boolean update(Answer myAnswer) throws IllegalArgumentException {

			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = " UPDATE forum_comment SET "
						+ "description_comment='" + myAnswer.getDescription()
						+ "' WHERE  cod_comment= " + myAnswer.getIdcomentary()
						+ " ";
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
}
