package com.project.titulo.server.crud;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.project.titulo.server.DataBaseProperties;
import com.project.titulo.server.helpers.EmailAlert;
import com.project.titulo.server.helpers.FileWritter;
import com.project.titulo.shared.model.UserFile;

public class DataFile_crud {
	// add new file
		public static Boolean create(UserFile myFile) throws IllegalArgumentException {


			// return fail
			Boolean res = false;
			PreparedStatement pstmt = null;
			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "INSERT INTO `user_file` (`cod_file`, `title_file`, `description_file`, `creation_file`, `dimension_file`, `labelx_file`, `labely_file`, `labelz_file`, `plot_file`, `metric_file`, `data_file`, `cod_user`)  "
						+ "VALUES (NULL, '"
						+ myFile.getTitle()
						+ "', '"
						+ myFile.getDescription()
						+ "', CURRENT_TIMESTAMP, '"
						+ myFile.getDimension()
						+ "', '"
						+ myFile.getLabelx()
						+ "', '"
						+ myFile.getLabely()
						+ "', '"
						+ myFile.getLabely()
						+ "', '0', '0', '"
						+ myFile.getData().replaceAll("[;,]", " ")
						+ "', '"
						+ myFile.getIduser() + "');";

				pstmt = conn.prepareStatement(Query);
				int state = pstmt.executeUpdate();

				// succes
				if (state == 1) {
					res = true;
				}
				
				//close elements
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				res = false;
			}
			return res;
		}

		// update a file
		public static Boolean update(UserFile myFile) throws IllegalArgumentException {


			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = " UPDATE user_file SET " + "title_file='"
						+ myFile.getTitle() + "', " + "description_file='"
						+ myFile.getDescription() + "', " + "dimension_file='"
						+ myFile.getDimension() + "'," + "labelx_file='"
						+ myFile.getLabelx() + "', " + "labely_file='"
						+ myFile.getLabely() + "', " + "labelz_file='"
						+ myFile.getLabelz() + "', " + "data_file='"
						+ myFile.getData() + "' " + " WHERE cod_file = "
						+ myFile.getIdfile() + "";

				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				//close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

		// delete a file
		public static Boolean delete(String iddatafile)
				throws IllegalArgumentException {


			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "DELETE FROM user_file WHERE cod_file ="
						+ iddatafile + "";
				PreparedStatement ps = conn.prepareStatement(Query);
				int state = ps.executeUpdate();
				if (state == 1) {
					File file = new File("./files/" + iddatafile + ".txt");
					file.delete();
				}

				//close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				return false;
			}
			return true;
		}

		// a file data
		public static UserFile read(String iddatafile)
				throws IllegalArgumentException {


			UserFile myFile = null;
			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "SELECT * FROM user_file WHERE cod_file = '"
						+ iddatafile + "' LIMIT 1";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				// recorremos resultado
				while (result.next()) {
					/*
					 * String Idfile, String Title, String Dimension, String Labelx,
					 * String Labely, String Labelz, String Description, String
					 * Iduser, String Creation, String Data, String Plot, String
					 * Metric
					 */
					myFile = new UserFile(result.getString("cod_file"),
							result.getString("title_file"),
							result.getString("dimension_file"),
							result.getString("labelx_file"),
							result.getString("labely_file"),
							result.getString("labelz_file"),
							result.getString("description_file"),
							result.getString("cod_user"),
							result.getString("creation_file"),
							result.getString("data_file"),
							result.getString("plot_file"),
							result.getString("metric_file"));
				}
				result.close();
				ps.close();
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return myFile;
		}

		// all user files
		public static List<UserFile> getUserFiles(String iduser)
				throws IllegalArgumentException {


			List<UserFile> listFiles = new ArrayList<UserFile>();
			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "SELECT * FROM user_file WHERE cod_user = '"
						+ iduser + "'";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				// recorremos resultado
				while (result.next()) {
					// add element to list String Idfile, String Title, String
					// Description, String Iduser, String Creation
					listFiles.add(new UserFile(result.getString("cod_file"), result
							.getString("title_file"), result
							.getString("dimension_file"), result
							.getString("labelx_file"), result
							.getString("labely_file"), result
							.getString("labelz_file"), result
							.getString("description_file"), result
							.getString("cod_user"), result
							.getString("creation_file"), result
							.getString("data_file"), result.getString("plot_file"),
							result.getString("metric_file")));
				}

				//close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return listFiles;
		}

		// get count of user files
		public static Integer getCountUserFiles(String iduser)
				throws IllegalArgumentException {


			int totalfiles = 0;
			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "SELECT COUNT(cod_file) AS userfilecount FROM user_file WHERE cod_user = "
						+ iduser;
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				// recorremos resultado
				while (result.next())
					totalfiles = result.getInt(1);

				result.close();
				ps.close();
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return -1;
			}
			return totalfiles;

		}

		// select file for plot
		public static Boolean addPlotFile(String iddatafile)
				throws IllegalArgumentException {


			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = " UPDATE user_file SET plot_file = 1 WHERE cod_file='"
						+ iddatafile + "'";
				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				//close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}

			return true;
		}

		// remove plot file selected
		public static Boolean removePlotFile(String iddatafile)
				throws IllegalArgumentException {


			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = " UPDATE user_file SET plot_file = 0 WHERE cod_file='"
						+ iddatafile + "'";
				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				//close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

		// select files for metric
		public static Boolean addMetricFile(String iddatafile)
				throws IllegalArgumentException {


			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = " UPDATE user_file SET metric_file = 1 WHERE cod_file='"
						+ iddatafile + "'";
				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				//close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

		// remove metric files selected
		public static Boolean removeMetricFile(String iddatafile)
				throws IllegalArgumentException {


			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = " UPDATE user_file SET metric_file = 0 WHERE cod_file='"
						+ iddatafile + "'";
				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				//close elements
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				return false;
			}
			return true;
		}

		// Get selected plot Files
		public static List<UserFile> getUserFilesPlot(String iduser, String path)
				throws IllegalArgumentException {


			List<UserFile> listFiles = new ArrayList<UserFile>();

			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "SELECT * FROM plot_file_view WHERE cod_user = '"
						+ iduser + "'";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				// resume errors
				List<String> ErrorMessage = new ArrayList<>();
				// recorremos resultado
				while (result.next()) {
					// add element to list String Idfile, String Title, String
					// Description, String Iduser, String Creation
					listFiles.add(new UserFile(result.getString("cod_file"), result
							.getString("title_file"), result
							.getString("dimension_file"), "", "", "", result
							.getString("description_file"), result
							.getString("cod_user"), result
							.getString("creation_file"), result
							.getString("data_file"), "", ""));

					
					// create file
					String aux = FileWritter.Write( path, result.getString("cod_file"), result.getString("data_file"));
					if(aux!=null)
						ErrorMessage.add(aux);
				}
				if (ErrorMessage.size() > 0) {
					EmailAlert.WarningEmail("ServerServiceImpl - getUserFilesPlot",
							ErrorMessage.toString());
				}

				//close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return listFiles;
		}

		// Get selected metric Files
		public static List<UserFile> getUserFilesMetric(String iduser)
				throws IllegalArgumentException {


			List<UserFile> listFiles = new ArrayList<UserFile>();
			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "SELECT * FROM metric_file_view WHERE cod_user = '"
						+ iduser + "'";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				// recorremos resultado
				while (result.next()) {
					// add element to list String Idfile, String Title, String
					// Description, String Iduser, String Creation
					listFiles.add(new UserFile(result.getString("cod_file"), result
							.getString("title_file"), result
							.getString("dimension_file"), "", "", "", result
							.getString("description_file"), result
							.getString("cod_user"), result
							.getString("creation_file"), result
							.getString("data_file"), "", ""));
				}

				//close elements
				result.close();
				ps.close();
				conn.close();
				
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
			}
			return listFiles;
		}
		
		// get user files selecterd for metrics
		public static List<UserFile> getFullFilesMetric(String user, String path) {

			List<UserFile> listFiles = new ArrayList<UserFile>();
			try {
				//ask por conection
				Connection conn = DataBaseProperties.CreateConn();
				
				//query
				String Query = "SELECT * FROM user_file WHERE cod_user = '" + user
						+ "' AND metric_file = 1";
				PreparedStatement ps = conn.prepareStatement(Query);
				ResultSet result = ps.executeQuery();
				// error list
				List<String> ErrorMessage = new ArrayList<>();
				
				// recorremos resultado
				while (result.next()) {
					listFiles.add(new UserFile(result.getString("cod_file"), result
							.getString("title_file"), result
							.getString("dimension_file"), result
							.getString("labelx_file"), result
							.getString("labely_file"), result
							.getString("labelz_file"), result
							.getString("description_file"), result
							.getString("cod_user"), result
							.getString("creation_file"), result
							.getString("data_file"), result.getString("plot_file"),
							result.getString("metric_file")));

					
					String aux = FileWritter.Write( path, result.getString("cod_file"), result.getString("data_file"));
					if(aux!=null)
						ErrorMessage.add(aux);

				}
				// send alert
				if (ErrorMessage.size() > 0) {
					EmailAlert.WarningEmail("ServerServiceImpl - getUserFilesPlot",
							ErrorMessage.toString());
				}
				result.close();
				ps.close();
			} catch (SQLException sqle) {
				GWT.log(sqle.toString());
				sqle.printStackTrace();
			}
			return listFiles;
		}
}
