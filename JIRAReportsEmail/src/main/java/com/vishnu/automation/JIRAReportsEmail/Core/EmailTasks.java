package com.vishnu.automation.JIRAReportsEmail.Core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.vishnu.automation.JIRAReportsEmail.Dataclass.EmailOperations;
import com.vishnu.automation.JIRAReportsEmail.Utilities.Logger;

import org.jsoup.*;

public class EmailTasks {

	// public String filename = "";
	public static String host = "smtp.gmail.com"; // host smtp address.

	public void ProcessEmailReports(EmailOperations emailObjectData) {

		// Prepare Email
		String fromAddress = emailObjectData.getFromEmailAddress() == null ? "no-reply@vishnu.com" : emailObjectData.getFromEmailAddress();
		String emailBodyMessage = emailObjectData.getEmailBodyMessage();
		String attachmentName = emailObjectData.getAttachmentName();
		String[] toAddressArray = emailObjectData.getEmailAddresses().split(",");
		String[] bccAddressArray = emailObjectData.getBcc().split(",");
		final String dir = System.getProperty("user.dir");
		Logger.WriteLog("Current dirictory is = " + dir);
		Path path = Paths.get(dir, attachmentName);
		System.out.println("File path for attachment = " + path.toString());
		Logger.WriteLog("File path for attachment = " + path.toString());
		
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(GetSession());
			message.setFrom(new InternetAddress(fromAddress));
			message.setSubject(emailObjectData.getSubject());
			
			for (String bcc : bccAddressArray) {
			message.addRecipient(Message.RecipientType.BCC,new InternetAddress(bcc));
			}

			// Set To: header field of the header.
			for (String toadd : toAddressArray) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toadd));
			}
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			// messageBodyPart.setText(emailBodyMessage);
			String msgType = emailObjectData.getMessageType() == null ? "text/plain"
					: (emailObjectData.getMessageType().equalsIgnoreCase("html") ? "text/html" : "text/plain");
			
			if (emailObjectData.getMessageType().equalsIgnoreCase("html")) {
				emailBodyMessage = emailBodyMessage.replaceAll("\n", "<br />");
			//	System.out.println("after Body msg:" + emailBodyMessage);
				messageBodyPart.setContent(emailBodyMessage, msgType);
			}
			// Create a multipart message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			if (emailObjectData.getAttachmentsNeeded()) {
				multipart.addBodyPart(PrepareAttachment(path.toString(), emailObjectData.getAttachmentName()));
			}
			// Send message
			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			System.out.println("Exception while emailing report:" + e.getMessage());
			e.printStackTrace();
			Logger.WriteLog("Exception while emailing report:" + e.getMessage());
			Logger.WriteLog("Exception StackTrace:" + e.getStackTrace());
		}
	}

	private String ConvertHTMLToText(String html) {
		return Jsoup.parse(html).text();
	}

	private Session GetSession() {
		Properties properties = System.getProperties();
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		return session;
	}

	private BodyPart PrepareAttachment(String file, String filename) {
		Logger.WriteLog("Include attachments on the email");
		BodyPart messageAttachmentPart = new MimeBodyPart();
		messageAttachmentPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		try {
			messageAttachmentPart.setDataHandler(new DataHandler(source));
			messageAttachmentPart.setFileName(filename);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageAttachmentPart;
	}

}
