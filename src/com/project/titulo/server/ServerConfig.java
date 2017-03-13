package com.project.titulo.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ServerConfig {
		// private String status;
		private static String url	=	"jdbc:mysql://localhost:3306/asmop_bd";
		private static String user	=	"root";
		private static String pass	=	"ubuntu16.04mysql";

		//constructor
		public ServerConfig(){
			
		}
		
		// create conection
		public static Connection CreateConn() {
			Connection myconn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				System.err.println(e.toString());
				e.printStackTrace();
			}

			try {
				myconn = DriverManager.getConnection(url, user, pass);
				
			} catch (SQLException e) {
				System.err.println(e.toString());
				e.printStackTrace();
			}

			return myconn;
		}
}
