package com.vishnu.automation.DailyTaskCreator.DataClass;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Project")
public class Project {
	
	//name
	//project key
	
	//Task class ( wrapper TaskList>
	
	private String name;
	private String projectKey;
//	private List<Task> tasks;
	private List<Epic> epicList;
	
	@XmlElement(name="Epic")
	public List<Epic> getEpicList() {
		return epicList;
	}
	public void setEpicList(List<Epic> epicList) {
		this.epicList = epicList;
	}
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name="Projectkey")
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	
//	@XmlElementWrapper(name="TaskList")
//	@XmlElement(name="Task")
//	public List<Task> getTasks() {
//		return tasks;
//	}
//	public void setTasks(List<Task> tasks) {
//		this.tasks = tasks;
//	}
	

	
}
