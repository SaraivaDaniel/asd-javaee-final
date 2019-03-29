package com.danielsaraiva.sharebroker.emailsender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	public Properties getProperties() throws IOException {
		InputStream inputStream = null;

		try {
			Properties properties = new Properties();
			String propertyFile = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);

			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("File '" + propertyFile + "' not found on classpath");
			}

			return properties;
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}

		return null;
	}

	public void sendEmail(String toEmail, String subject, String body) {
		try {
			Properties properties = getProperties();
			String user = properties.getProperty("emailsender.user");
			
			Authenticator auth = configAuth(user, properties.getProperty("emailsender.password"));
			Session session = Session.getInstance(properties, auth);
			
			MimeMessage message = new MimeMessage(session);
			configHeaders(message);
			configContent(user, toEmail, subject, body, message);

			System.out.println("Email pronto para ser enviado!");
			Transport.send(message);
			System.out.println("Email enviado com sucesso!!");
		} catch (Exception e) {
			System.out.println("Erro ao enviar email");
			e.printStackTrace();
		}
	}

	private Authenticator configAuth(final String fromEmail, final String password) {
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		return auth;
	}

	private void configHeaders(MimeMessage message) throws MessagingException {
		message.addHeader("Content-type", "text/html; charset=UTF-8");
		message.addHeader("Format", "flowed");
		message.addHeader("Content-Transfer-Encoding", "8bit");
	}

	private void configContent(String fromEmail, String toEmail, String subject, String body, MimeMessage message) {
		try {
			message.setFrom(fromEmail);
			message.setReplyTo(InternetAddress.parse(fromEmail, false));
			message.setSubject(subject, "UTF-8");
			message.setContent(body, "text/html");
			message.setSentDate(new Date());
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		} catch (Exception e) {
			System.out.println("Erro ao preparar o email");
			e.printStackTrace();
		}
	}
}
