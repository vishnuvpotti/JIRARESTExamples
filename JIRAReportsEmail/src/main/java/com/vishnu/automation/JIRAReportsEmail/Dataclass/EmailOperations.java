package com.vishnu.automation.JIRAReportsEmail.Dataclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EmailOperations")
public class EmailOperations {
	private String subject;
	private String emailBodyMessage;
	private String emailAddresses;
	private String messageType;
	private String attachments;
	private String attachmentName;
	private Boolean attachmentsNeeded;
	private String fromEmailAddress;
	private String bcc;


	@XmlElement(name = "Subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@XmlElement(name = "EmailBodyMessage")
	public String getEmailBodyMessage() {
		return emailBodyMessage;
	}

	public void setEmailBodyMessage(String emailBodyMessage) {
		this.emailBodyMessage = emailBodyMessage;
	}

	@XmlElement(name = "EmailAddresses")
	public String getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(String emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	@XmlElement(name = "MessageType")
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@XmlElement(name = "Attachments")
	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	@XmlElement(name = "AttachmentName")
	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public Boolean getAttachmentsNeeded() {
		if (attachments.equalsIgnoreCase("Yes") || attachments.equalsIgnoreCase("True")) {
			this.attachmentsNeeded = true;
		} else {
			this.attachmentsNeeded = false;
		}
		return attachmentsNeeded;
	}
	
	@XmlElement(name = "FromEmailAddress")
	public String getFromEmailAddress() {
		return fromEmailAddress;
	}

	public void setFromEmailAddress(String fromEmailAddress) {
		this.fromEmailAddress = fromEmailAddress;
	}
	
	@XmlElement(name = "BCC")
	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

}
