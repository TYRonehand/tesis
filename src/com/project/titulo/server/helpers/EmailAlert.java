package com.project.titulo.server.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.project.titulo.server.DataBaseProperties;


public class EmailAlert {

	/*authorization gmail */
	private static String authEmail = "gutierr.carlos@gmail.com";
	private static String authCode = "krgj fmuu plqh niaj";
	

	/*create user email*/
	public static String SendEmail(String to, String subject, String content) {

		System.err.println("Function: Alert - SendEmail");

		return Send(authEmail, to, subject, content);
		
	}

	/*create warning*/
	public static String WarningEmail(String section, String content) {
		
		System.err.println("Function: Alert - WarningEmail");
		
		Date today = new Date();
		
		String subject = today.toString()+" FunctionAlert: " + section;

		//envio aviso
		String auxmessage = "";
		Connection conn = DataBaseProperties.CreateConn();
		String Query = "SELECT DISTINCT mail_admin FROM account_administrator";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			while (result.next()) {

				auxmessage = Send(authEmail, result.getString("mail_admin").toLowerCase(), subject, content);	
				
			}
			result.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return auxmessage;
	}
	
	/*send email and properties*/
	private static String Send(String from, String to, String subject, String content){
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication(authEmail, authCode);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(content,"text/html");

			Transport.send(message);

		} catch (MessagingException e) {
			System.err.print("*SEND EMAIL ERROR: " + e.toString());
			return "badsendmail";
		}
		return "goodsendmail";
	}
}
