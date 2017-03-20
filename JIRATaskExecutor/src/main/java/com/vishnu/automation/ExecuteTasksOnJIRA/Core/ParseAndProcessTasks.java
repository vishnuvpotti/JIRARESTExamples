package com.vishnu.automation.ExecuteTasksOnJIRA.Core;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.vishnu.automation.ExecuteTasksOnJIRA.Dataclass.JIRATaskList;
import com.vishnu.automation.ExecuteTasksOnJIRA.Dataclass.Task;
import com.vishnu.automation.ExecuteTasksOnJIRA.Utilities.Logger;

/**
 * Hello world!
 */
public class ParseAndProcessTasks {
	public static String filePath = "";
	public static String taskToExecute = "";

	public ParseAndProcessTasks(String file) {
		filePath = file;
	}

	public ParseAndProcessTasks(String file, String reportName) {
		filePath = file;
		taskToExecute = reportName;
	}

	public void ProcessTasks() {
		JAXBContext jc = null;
		Unmarshaller unmarshaller = null;
		JIRATaskList jiraTasks = null;

		try {
			jc = JAXBContext.newInstance(JIRATaskList.class);
			unmarshaller = jc.createUnmarshaller();
			System.out.println("Filepath:" + filePath);
			jiraTasks = (JIRATaskList) unmarshaller.unmarshal(new File(filePath));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.WriteLog("Exception while parsing xml. Please check detailed exception info." + e.getMessage());
			System.out.println("Exception while parsing xml. Please check detailed exception info." + e.getMessage());
			Logger.WriteLog("Exception stacktrace:" + e.getStackTrace());
		}

		try {
			System.out.println("Get Tasks Count:" + jiraTasks.getTaskList().size());
			Logger.WriteLog("Get total Tasks Count:" + jiraTasks.getTaskList().size());

			for (Task tasks : jiraTasks.getTaskList()) {

				Logger.WriteLog("Start Processing Task :" + tasks.getTaskName());
				System.out.println("Start Processing Task : " + tasks.getTaskName());
				System.out.println("JIRA JQL for Report  is: " + tasks.getJql());
				Logger.WriteLog("JIRA JQL for Report is: " + tasks.getJql());
				if (!taskToExecute.isEmpty()) {
					if (tasks.getTaskName().equalsIgnoreCase(taskToExecute)) {
						ProcessTasks(tasks);
					}
				} else {
					// Generate all reports
					ProcessTasks(tasks);
				}
			}
		} catch (Exception e) {
			Logger.WriteLog("Exception occured while processing Report : " + e.getMessage());
			Logger.WriteLog("Exception stacktrace : " + e.getStackTrace());
			System.out.println("Exception occured while processing Report : " + e.getMessage());
			System.out.println("Exception stacktrace : " + e.getStackTrace());
		}
	}

	private void ProcessTasks(Task tasks) {
		// Perform JIRA Operation
		Logger.WriteLog("Process JIRA tasks for report :" + tasks.getTaskName());
		JIRATasks jiraTaskObject = new JIRATasks();
		jiraTaskObject.ProcessJIRATasks(tasks.getJql(), tasks.getActionList());

	}

}
