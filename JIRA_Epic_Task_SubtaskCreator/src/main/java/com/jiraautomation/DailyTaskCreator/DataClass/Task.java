package com.jiraautomation.DailyTaskCreator.DataClass;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Task")
public class Task {

	private String taskname;
	
	private List<Parameter> parameterList;
	
	private List<Subtask> subtasks;

	@XmlElement(name="Subtask")
	public List<Subtask> getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(List<Subtask> subtasks) {
		this.subtasks = subtasks;
	}

	@XmlAttribute(name="taskname")
	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	@XmlElement(name="Parameter")
	public List<Parameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}
	
	
	
}
