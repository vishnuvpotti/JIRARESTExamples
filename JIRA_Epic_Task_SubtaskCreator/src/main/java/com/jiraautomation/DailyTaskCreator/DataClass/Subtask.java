package com.jiraautomation.DailyTaskCreator.DataClass;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Subtask")
public class Subtask {
	
	private String name;
	private List<Parameter> parameterList;
	
	@XmlAttribute(name="subtaskname")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name="Parameter")
	public List<Parameter> getParameterList() {
		return parameterList;
	}
	
	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}
	
	

}
