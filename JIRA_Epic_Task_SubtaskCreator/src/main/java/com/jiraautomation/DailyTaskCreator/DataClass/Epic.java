package com.jiraautomation.DailyTaskCreator.DataClass;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Epic")
public class Epic {
	private String epicName;
	
	private List<Parameter> parameterList;
	
	private List<Task> taskList;

	
	@XmlAttribute(name="epicname")
	public String getEpicname() {
		return epicName;
	}

	public void setEpicname(String epicName) {
		this.epicName = epicName;
	}

	@XmlElement(name="Parameter")
	public List<Parameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}

	@XmlElementWrapper(name="TaskList")
	@XmlElement(name="Task")
	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	
}
