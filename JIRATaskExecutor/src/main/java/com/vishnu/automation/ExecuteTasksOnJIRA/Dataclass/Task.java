package com.vishnu.automation.ExecuteTasksOnJIRA.Dataclass;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "JIRAOperations")
public class Task {

	private String jql;
	private String taskName; 
	private List<Action> actionList;
	
	@XmlElement(name="JQL")
	public String getJql() {
		return jql;
	}
	public void setJql(String jql) {
		this.jql = jql;
	}

	@XmlAttribute(name="taskname")
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setActionList(List<Action> actionList) {
		this.actionList = actionList;
	}
		
	
	@XmlElement(name="Action")
	public List<Action> getActionList() {
		return actionList;
	}
	public String getTaskName() {
		return taskName;
	}
	

	
	
}
