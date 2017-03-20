package com.vishnu.automation.ReportsFromJIRA.Dataclass;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Report")
public class Report {

	private String reportName;
	
	@XmlAttribute(name="reportname")
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	private JIRAOperations jiraObject;
	private EmailOperations emailObject;
	
	@XmlElement(name="JIRA")
	public JIRAOperations getJiraObject() {
		return jiraObject;
	}
	
	public void setJiraObject(JIRAOperations jiraObject) {
		this.jiraObject = jiraObject;
	}
	@XmlElement(name="Email")
	public EmailOperations getEmailObject() {
		return emailObject;
	}
	public void setEmailObject(EmailOperations emailObject) {
		this.emailObject = emailObject;
	}
	
}
