package com.jiraautomation.DailyTaskCreator.DataClass;


import javax.xml.bind.annotation.*;

@XmlRootElement(name="DailyTaskList")
public class DailtTaskList {
	

    private Project project;
    
    @XmlElement(name="Project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
    
	
	
	

}
