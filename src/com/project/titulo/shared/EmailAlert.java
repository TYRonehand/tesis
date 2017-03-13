package com.project.titulo.shared;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAlert {

	private static String adminEmail = "gutierr.carlos@gmail.com";
	private static String authEmail = "krgj fmuu plqh niaj";

	// send user email
	public static String SendEmail(String to, String Subjettext,
			String Messagehtml) {

		System.err.println("Function: Alert - SendEmail");

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

						return new PasswordAuthentication(adminEmail, authEmail);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adminEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(Subjettext);
			message.setText(Messagehtml);

			Transport.send(message);

		} catch (MessagingException e) {
			System.err.print("*SEND EMAIL ERROR: " + e.toString());
			return "badsendmail";
		}
		return "goodsendmail";
	}

	// send user email
	public static String WarningEmail(String section, String Messagehtml) {
		
		System.err.println("Function: Alert - WarningEmail");
		
		Date today = new Date();
		
		String Subjettext = today.toString()+" FunctionAlert: " + section;

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

						return new PasswordAuthentication(adminEmail, authEmail);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adminEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adminEmail));
			message.setSubject(Subjettext);
			message.setText(Messagehtml);

			Transport.send(message);

		} catch (MessagingException e) {
			System.err.print("*SEND EMAIL ERROR: " + e.toString());
			return "badsendmail";
		}
		return "goodsendmail";
	}
}
