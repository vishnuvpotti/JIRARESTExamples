package com.vishnu.automation.ExecuteTasksOnJIRA.Dataclass;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "JIRATaskList")
public class JIRATaskList {

	private List<Task> taskList;

	@XmlElement(name = "Task")
	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
}
