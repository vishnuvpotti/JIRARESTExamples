package com.jiraautomation.DailyTaskCreator.DataClass;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Parameter")
public class Parameter {
	
	private String field;
	private String value;
	private String fieldtype;
	
	@XmlAttribute(name="field")
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	@XmlElement(name="Value")
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlAttribute(name="fieldtype")
	public String getFieldtype() {
		return fieldtype;
	}
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
	
}
