package com.namobuddha.emailsender;

//File Name SendEmail.java

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.namobuddha.emailsender.model.StudentDetails;
import com.namobuddha.emailsender.util.cs202.ExcelReader;
import com.namobuddha.emailsender.util.cs202.TemplateReader;

public class CS202EmailSender {
	Session session;
	final Properties config;
	final TemplateReader templateReader;

	public CS202EmailSender(final Properties config) {
		Properties properties = System.getProperties();
		this.config = config;
		templateReader = new TemplateReader(this.config.getProperty("vmLocation"));

		// Setup mail server
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								config.getProperty("fromemail"), config.getProperty("pass"));
					}
				});
	}

	public void sendToAll(List<StudentDetails> students) {
		int i = 0;
		for (StudentDetails student : students) {

			try {
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(config.getProperty("fromemail")));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(student.getEmailId()));

				// Set Subject: header field
				message.setSubject(config.getProperty("subject"));
				message.setText(templateReader.generate(student));

				// Send message
				if(config.get("action").equals("print")) {
					this.printMessage(message);
				} else {
					Transport.send(message);
					System.out.println("Message Successfully sent to "
						+ student.getName());
					i++;
				}
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}
		}
		System.out.println("\n\nTotal Emails sent: " + i);
	}
	
	private void printMessage(MimeMessage message) {
		try {
			System.out.println("From: " + message.getFrom()[0].toString());
			System.out.println("TO: " + message.getRecipients(Message.RecipientType.TO)[0]);
			System.out.println("Subject: " + message.getSubject());
			System.out.println("Body: \n" + message.getContent().toString());
			System.out.println("*****************************************\n\n");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			
		}
	}

	public static void main(String[] args) {
		Properties config = new Properties();
		try {
			config.load(new FileInputStream("data/cs202.properties"));
		} catch (FileNotFoundException e) {
			System.err.println("Config file doesn't exist");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error in reading config file");
			e.printStackTrace();
		}
		
		Scanner scan = new Scanner(System.in);
		System.out.println("xxxx");
		String password = scan.nextLine();
		scan.close();
		config.setProperty("pass", password);
		
		int section1students = Integer.parseInt(config.getProperty("section1Students"));
		int section2students = Integer.parseInt(config.getProperty("section2Students"));
		
		int initialSheet = Integer.parseInt((String)config.get("sheetIndex"));;
		int[] nrOfStudents = new int[] { section1students, section2students };

		System.out.println("Reading Grades from: " + config.getProperty("excellocation"));
		ExcelReader reader = new ExcelReader(initialSheet, nrOfStudents, config.getProperty("excellocation"));
		CS202EmailSender sender = new CS202EmailSender(config);
		sender.sendToAll(reader.getStudentsDetails());

	}
}
