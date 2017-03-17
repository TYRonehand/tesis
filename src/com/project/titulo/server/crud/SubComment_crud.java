package com.project.titulo.server.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.titulo.server.DataBaseProperties;
import com.project.titulo.shared.model.SubAnswer;

public class SubComment_crud {
	// add new answer to topic
		public static Boolean create(SubAnswer mySubComment)
				throws IllegalArgumentException {

			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "INSERT INTO forum_subcomment (description_subcomment, cod_comment, cod_user) "
						+ "VALUES ('"
						+ mySubComment.getDescription()
						+ "',"
						+ mySubComment.getIdcomment()
						+ ","
						+ mySubComment.getIduser() + ")";
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				// recorremos resultado
				ps.close();
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

		// get some comment
		public static List<SubAnswer> read(String idcomment)
				throws IllegalArgumentException {

			List<SubAnswer> myComments = new ArrayList<SubAnswer>();
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = "SELECT * FROM forum_subcomment WHERE cod_comment="
						+ idcomment + " ORDER BY creation_subcomment ASC";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				// recorremos resultado
				/*
				 * String idsubcomment, String Idcomment, String Description, String
				 * Creation, String Iduser
				 */
				while (result.next()) {
					myComments.add(new SubAnswer(
							result.getString("cod_subcomment"), result
									.getString("cod_comment"), result
									.getString("description_subcomment"), result
									.getString("creation_subcomment"), result
									.getString("cod_user")));
				}
				result.close();
				ps.close();
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return myComments;
		}

		// set a comment
		public static Boolean update(SubAnswer myAnswer)
				throws IllegalArgumentException {

			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				String Query = " UPDATE forum_subcomment SET "
						+ " description_subcomment='" + myAnswer.getDescription()
						+ "' WHERE cod_subcomment = " + myAnswer.getIdcomment();
				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();
				ps.close();
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

}
