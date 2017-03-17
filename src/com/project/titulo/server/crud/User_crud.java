package com.project.titulo.server.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.project.titulo.server.DataBaseProperties;
import com.project.titulo.server.helpers.EmailAlert;
import com.project.titulo.server.helpers.SecretCode;
import com.project.titulo.shared.model.User;

public class User_crud {

	public static List<User> FindUserList(String opcion)
			throws IllegalArgumentException {

		List<User> allusers = new ArrayList<User>();
		try {
			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			String Query = "";
			if (opcion.equals("new")) {
				Query = "SELECT * FROM account_user ORDER BY cod_user DESC";
			} else if (opcion.equals("old")) {
				Query = "SELECT * FROM account_user ORDER BY cod_user ASC";
			} else {
				Query = "SELECT * FROM account_user WHERE name_user LIKE '%"
						+ opcion + "%'  OR lastname_user LIKE '%" + opcion
						+ "%' ORDER BY cod_user ASC";
			}
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();

			// get fields
			while (result.next()) {
				allusers.add(new User(result.getString("cod_user"), result
						.getString("email_user"),
						result.getString("name_user"), result
								.getString("lastname_user"), result
								.getString("country_user"), result
								.getString("occupation_user"), result
								.getString("web_user"), result
								.getString("institution_user"), result
								.getString("password_user"), result
								.getString("creation_user"), result
								.getString("securitycode_user"), result
								.getString("lastconnection_user")));
			}

			// close elements
			result.close();
			ps.close();
			conn.close();
		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}
		return allusers;
	}

	public static Boolean Exist(String mail) throws IllegalArgumentException {

		int count = 0;
		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			String query = "select * from account_user where email_user = '"
					+ mail.toUpperCase() + "' limit 1";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet result = ps.executeQuery();

			// get fields
			while (result.next()) {
				count++;
			}

			// close elements
			result.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}

		if (count == 0) {
			return false;
		}
		return true;
	}

	public static Boolean userRecovery(String mail, String PIN)
			throws IllegalArgumentException {

		int count = 0;
		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			String query = "SELECT * FROM account_user WHERE email_user = '"
					+ mail.toUpperCase() + "' AND securitycode_user='" + PIN
					+ "' limit 1";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet result = ps.executeQuery();

			// get fields
			while (result.next()) {
				count++;
			}
			// close elements
			result.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}

		if (count > 0) {
			eraseUserPin(mail);
			return true;
		}
		return false;
	}

	// user info validation
	public static User authenticateUser(String user, String pass)
			throws IllegalArgumentException {

		User miuser = null;

		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			// consultamos a base de datos
			String Query = "SELECT * FROM account_user WHERE email_user = '"
					+ user.toUpperCase() + "' AND password_user = '" + pass
					+ "' OR securitycode_user ='" + pass + "' LIMIT 1";
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			// recorremos resultado
			while (result.next()) {

				miuser = new User(result.getString("cod_user"),
						result.getString("email_user"),
						result.getString("name_user"),
						result.getString("lastname_user"),
						result.getString("country_user"),
						result.getString("occupation_user"),
						result.getString("web_user"),
						result.getString("institution_user"),
						result.getString("password_user"),
						result.getString("creation_user"),
						result.getString("securitycode_user"),
						result.getString("lastconnection_user"));
			}

			// send admin email debugg
			if (miuser != null) {
				String MessageEmail = "<h1>Recent User Log In From:</h1> <p><span><b>User: </b></span> "
						+ miuser.getName()
						+ " "
						+ miuser.getLastname()
						+ " </p><p><span><b>Email: </b></span>  "
						+ miuser.getMail() + "</p>";
				EmailAlert.WarningEmail("ServerServiceImpl - authenticateUser",
						MessageEmail);
			}

			// close elements
			result.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}
		// log date connection
		if (miuser != null)
			saveDateLogin(miuser.getId());
		return miuser;
	}

	// user info no security
	public static User read(String iduser)
			throws IllegalArgumentException {

		User miuser = null;
		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			// consultamos a base de datos
			String Query = "SELECT * FROM account_user WHERE cod_user = '"
					+ iduser + "' LIMIT 1";
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			// recorremos resultado
			while (result.next()) {
				miuser = new User(result.getString("cod_user"),
						result.getString("email_user"),
						result.getString("name_user"),
						result.getString("lastname_user"),
						result.getString("country_user"),
						result.getString("occupation_user"),
						result.getString("web_user"),
						result.getString("institution_user"),
						result.getString("password_user"),
						result.getString("creation_user"),
						result.getString("securitycode_user"),
						result.getString("lastconnection_user"));
			}
			// close elements
			result.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
		}
		return miuser;
	}

	// update info user
	public static Boolean update(User myUser)
			throws IllegalArgumentException {

		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			// actualizamos usuario
			String Query = " UPDATE account_user SET name_user='"
					+ myUser.getName() + "', " + " lastname_user='"
					+ myUser.getLastname() + "', ";

			if (myUser.getMail().length() > 0)
				Query += " email_user='" + myUser.getMail() + "',";
			if (myUser.getPassword().length() > 0)
				Query += " password_user='" + myUser.getPassword() + "',";

			Query += " country_user='" + myUser.getCountry() + "', "
					+ " occupation_user='" + myUser.getOcupation() + "', "
					+ " web_user='" + myUser.getWeb() + "',"
					+ " institution_user='" + myUser.getInstitution() + "' "
					+ " WHERE cod_user= '" + myUser.getId() + "'";

			System.err.println("QUERY UPDATE CLIENT: " + Query);

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

	// add new user in database
	public static Boolean create(User newUser)
			throws IllegalArgumentException {

		Boolean res = false;
		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			// consultamos a base de datos
			String Query = "INSERT INTO account_user (email_user,name_user,lastname_user,country_user,password_user)"
					+ "VALUES ('"
					+ newUser.getMail().toUpperCase()
					+ "','"
					+ newUser.getName()
					+ "','"
					+ newUser.getLastname()
					+ "','"
					+ newUser.getCountry()
					+ "','"
					+ newUser.getPassword()
					+ "');";

			PreparedStatement ps = conn.prepareStatement(Query);
			ps.executeUpdate();

			// close elements
			ps.close();
			conn.close();

			res = true;
		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
			res = false;
		}
		return res;
	}

	// recovery account
	public static Boolean changeUserPassword(String mail, String password)
			throws IllegalArgumentException {

		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			// consultamos a base de datos
			String Query = " UPDATE account_user SET password_user = '"
					+ password + "' WHERE email_user='" + mail.toUpperCase()
					+ "'";

			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();

			// close elements
			result.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
			return false;
		}
		return true;
	}

	// delete a user
	public static Boolean delete(String iduser)
			throws IllegalArgumentException {

		Boolean res = false;
		try {

			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// query
			String Query = "DELETE FROM account_user WHERE cod_user=" + iduser;
			PreparedStatement ps = conn.prepareStatement(Query);
			ps.executeUpdate();

			// close elements
			ps.close();
			conn.close();

			res = true;
		} catch (SQLException sqle) {
			System.err.println("Error: " + sqle.toString());
			sqle.printStackTrace();
			res = false;
		}
		return res;
	}

	// get pin to user and Send email verify for lost password
	public static String sendEmailVerify(String email)
			throws IllegalArgumentException {

		if (Exist(email)) {
			SecretCode code = new SecretCode();
			String CODE = code.getCode();
			Boolean valid = false;
			// set user PIN
			try {

				// ask por conection
				Connection conn = DataBaseProperties.CreateConn();

				// query
				// actualizamos usuario
				String Query = " UPDATE account_user SET securitycode_user = '"
						+ CODE + "' WHERE email_user='" + email.toUpperCase()
						+ "'";
				// execute query
				PreparedStatement ps = conn.prepareStatement(Query);
				ps.executeUpdate();

				// close elements
				ps.close();
				conn.close();

				valid = true;// can send email
			} catch (SQLException sqle) {
				System.err.println("Error: " + sqle.toString());
				sqle.printStackTrace();
				valid = false;// somthing bad
			}
			// true send email
			if (valid == true) {

				String Subjettext = "You've requested a password recovery.";
				String Messagehtml = "Petition for new password! \n Hi there!, recently received a request to change password. that is why we attach your PIN recovery ["
						+ CODE+ "]. \n Under any reason, if you did not request this change, forget this email. \n\nHave a nice day!!";
				
				// true it was sended and all ok - false problem
				return EmailAlert.SendEmail(email, Subjettext, Messagehtml);
				
			}
			// System.err.print("*RECOVERY PASS ERROR:create pin");
			return "badpin";
		}
		// System.err.print("*RECOVERY PASS ERROR:mail dont exist");
		return "mailnoexist";
	}

	// clear pin from user
	private static void eraseUserPin(String email) {

		// set user PIN
		try {
			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// actualizamos usuario
			String Query = " UPDATE account_user SET securitycode_user = '' WHERE email_user='"
					+ email.toUpperCase() + "'";
			// execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException sqle) {
			GWT.log(sqle.toString());
			sqle.printStackTrace();
		}
	}

	// save login date from user
	private static void saveDateLogin(String cod_user) {
		// set new date to login
		try {
			// ask por conection
			Connection conn = DataBaseProperties.CreateConn();

			// actualizamos usuario
			String Query = " UPDATE account_user SET lastconnection_user = CURRENT_TIMESTAMP WHERE cod_user='"
					+ cod_user + "'";
			// execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ps.executeUpdate();

			// close elements
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			GWT.log(sqle.toString());
			sqle.printStackTrace();
		}
	}
}
