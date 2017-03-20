package com.vishnu.automation.ReportsFromJIRA.Dataclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "JIRA")
public class JIRAOperations {

	private String jql;
	private String fields;
	private String fileType;
	private String fileName;
	
	@XmlElement(name="JQL")
	public String getJql() {
		return jql;
	}
	public void setJql(String jql) {
		this.jql = jql;
	}
	@XmlElement(name="Fields")
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	@XmlElement(name="FileType")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@XmlElement(name="Filename")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
