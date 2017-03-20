package com.vishnu.automation.ReportsFromJIRA.Core;

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

import com.vishnu.automation.ReportsFromJIRA.Dataclass.EmailOperations;
import com.vishnu.automation.ReportsFromJIRA.Utilities.Logger;

import org.jsoup.*;

public class EmailTasks {

	// public String filename = "";
	public static String host = "emailhostserver";
	public static String htmlData = "";

	/**
	 * Function to process the Email task.
	 * 
	 * @param emailObjectData
	 *            : Pass the EmailOperations data from xml.
	 */
	public void ProcessEmailReports(EmailOperations emailObjectData) {

		// Prepare Email
		String fromAddress = emailObjectData.getFromEmailAddress() == null ? "no-reply@yourdomain.com.au" : emailObjectData.getFromEmailAddress();
		String emailBodyMessage = emailObjectData.getEmailBodyMessage().replace("%resultData%", EmailTasks.htmlData);
		String attachmentName = emailObjectData.getAttachmentName();
		String[] toAddressArray = emailObjectData.getEmailAddresses().split(",");
		String[] bccAddressArray = emailObjectData.getBcc().split(",");
		final String dir = System.getProperty("user.dir");
		// Logger.WriteLog("Current dirictory is = " + dir);
		Path path = Paths.get(dir, attachmentName);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(GetSession());
			message.setFrom(new InternetAddress(fromAddress));
			message.setSubject(emailObjectData.getSubject());

			if (bccAddressArray.length >= 1) {
				for (String bcc : bccAddressArray) {
					System.out.println("Bcc address: " + bcc);
					if (bcc.trim().length() > 1) {
						message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
					}
				}
			}

			// Set To: header field of the header.
			System.out.println("To address" + toAddressArray.length);
			if (toAddressArray.length >= 1) {
				for (String toadd : toAddressArray) {
					System.out.println("To address: " + toadd);
					if (toadd.length() >= 1) {
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(toadd));
					}
				}
			}
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			// messageBodyPart.setText(emailBodyMessage);
			String msgType = emailObjectData.getMessageType() == null ? "text/plain"
					: (emailObjectData.getMessageType().equalsIgnoreCase("html") ? "text/html" : "text/plain");

			if (emailObjectData.getMessageType().equalsIgnoreCase("html")) {
				emailBodyMessage = emailBodyMessage.replaceAll("\n", "<br />");
				// System.out.println("after Body msg:" + emailBodyMessage);
				messageBodyPart.setContent(emailBodyMessage, msgType);
			}
			// Create a multipart message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			if (emailObjectData.getAttachmentsNeeded()) {
				System.out.println("File path for attachment = " + path.toString());
				Logger.WriteLog("File path for attachment = " + path.toString());
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

	/**
	 * Function to convert HTML to text format.
	 * 
	 * @param html
	 *            : Send the html string.
	 * @return : Plain text as string
	 */
	private String ConvertHTMLToText(String html) {
		return Jsoup.parse(html).text();
	}

	/**
	 * Function to get Session object for sending email
	 * 
	 * @return : Return Session object
	 */
	private Session GetSession() {
		Properties properties = System.getProperties();
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		return session;
	}

	/**
	 * Function to create attachment to the email.
	 * 
	 * @param file
	 *            : Send the complete file path with extension
	 * @param filename
	 *            : Send the display name of the attachment in the email .
	 * @return
	 */
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
