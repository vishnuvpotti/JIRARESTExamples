package com.vishnu.automation.DailyTaskCreator.Core;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.api.domain.input.LinkIssuesInput;
import com.atlassian.util.concurrent.Promise;
import com.google.common.collect.Lists;
import com.vishnu.automation.DailyTaskCreator.DataClass.DailtTaskList;
import com.vishnu.automation.DailyTaskCreator.DataClass.Epic;
import com.vishnu.automation.DailyTaskCreator.DataClass.Fields;
import com.vishnu.automation.DailyTaskCreator.DataClass.Parameter;
import com.vishnu.automation.DailyTaskCreator.DataClass.Subtask;
import com.vishnu.automation.DailyTaskCreator.DataClass.Task;
import com.vishnu.automation.DailyTaskCreator.Utilities.JIRAHelper;
import com.vishnu.automation.DailyTaskCreator.Utilities.Logger;

public class DailyTaskCreator {

	public static String projectKey = "";
	public int totalTasksCreatedCounter = 0;
	public int totalSubTasksCreatedCounter = 0;
	public int totalEpicCreatedCounter = 0;
	public static String filePath = "";

	public DailyTaskCreator(String file) {
		filePath = file;
	}

	public void ProcessDailyTaskCreation() {
		Instant starts = Instant.now();
		Instant ends = null;
		// TODO Auto-generated method stub
		String taskKey, subtaskKey = "";
		JAXBContext jc = null;
		DailtTaskList dailyTaskList = null;
		Unmarshaller unmarshaller = null;
		JiraRestClient client = null;
		JIRAHelper jiraHelperObject = null;

		try {
			jc = JAXBContext.newInstance(DailtTaskList.class);
			unmarshaller = jc.createUnmarshaller();
			dailyTaskList = (DailtTaskList) unmarshaller.unmarshal(new File(filePath));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.WriteLog("Exception while parsing xml. Please check detailed exception info.");
			Logger.WriteLog("Exception message:" + e.getMessage());
			Logger.WriteLog("Exception stacktrace:" + e.getStackTrace());
		}

		try {
			System.out.println("Get all  details");
			System.out.println("Get Project name:" + dailyTaskList.getProject().getName());
			System.out.println("Get Project key:" + dailyTaskList.getProject().getProjectKey());
			System.out.println("Total Epics to be created:" + dailyTaskList.getProject().getEpicList().size());
			Logger.WriteLog("Total Epics to be created:" + dailyTaskList.getProject().getEpicList().size());

			projectKey = dailyTaskList.getProject().getProjectKey();

			for (Epic epic : dailyTaskList.getProject().getEpicList()) {

				jiraHelperObject = new JIRAHelper();
				client = jiraHelperObject.GetJIRAclient();
				System.out.println("Processing Epic :" + epic.getEpicname());
				Logger.WriteLog("Processing Epic :" + epic.getEpicname());
				System.out.println("Total tasks to be created for this epic.:" + epic.getTaskList().size());
				Logger.WriteLog("Total tasks to be created for this epic.:" + epic.getTaskList().size());
				String epicKey = CreateEpicOrTaskOrSubTask(epic.getEpicname(), epic.getParameterList(), "epic", client);
				System.out.println("epic key:" + epicKey);
				if (!epicKey.isEmpty()) {
					totalEpicCreatedCounter++;
					AddCommentsIfNeeded(epicKey, epic.getParameterList(), client);
					LinkIssuesIfNeeded(epicKey, epic.getParameterList(), client);
				}

				for (Task task : epic.getTaskList()) {

					System.out.println("Task name: " + task.getTaskname());
					// Create Task;
					// Get task key;
					// Include Epic link in the task.
					if (!epicKey.isEmpty()) {
						Logger.WriteLog("Create Tasks for Epic :" + epicKey);
						Parameter epicParam = new Parameter();
						epicParam.setField(Fields.epicLink);
						epicParam.setValue(epicKey);
						epicParam.setFieldtype("simple");
						// System.out.println("Initial parameter length for
						// task:" + task.getParameterList().size());
						List<Parameter> newParamList = task.getParameterList();
						newParamList.add(epicParam);
						task.setParameterList(newParamList);
						// System.out.println("Updated parameter length for
						// task:" + newParamList.size());
					}
					taskKey = CreateEpicOrTaskOrSubTask(task.getTaskname(), task.getParameterList(), "", client);
					// Check if comments needs to be added for this task.

					if (!taskKey.isEmpty()) {
						totalTasksCreatedCounter++;
						System.out.println("Task created with key:" + taskKey);
						Logger.WriteLog("Task created with key:" + taskKey);
						AddCommentsIfNeeded(taskKey, task.getParameterList(), client);
						LinkIssuesIfNeeded(taskKey, task.getParameterList(), client);
						System.out.println("Subtask count is: " + task.getSubtasks().size());
						for (Subtask subtask : task.getSubtasks()) {
							System.out.println("SubTask name: " + subtask.getName());
							subtaskKey = CreateEpicOrTaskOrSubTask(subtask.getName(), subtask.getParameterList(), taskKey, client);
							if (!subtaskKey.isEmpty()) {
								totalSubTasksCreatedCounter++;
								System.out.println("Subtask created with key :" + subtaskKey);
								Logger.WriteLog("Subtask created with key :" + subtaskKey);
								AddCommentsIfNeeded(subtaskKey, subtask.getParameterList(), client);
								LinkIssuesIfNeeded(subtaskKey, subtask.getParameterList(), client);
							} else {
								Logger.WriteLog("Subtask creation failed.");
							}
						}
						System.out.println("All subtasks created for Task :" + taskKey);
					} else {
						Logger.WriteLog("Task creation failed.Skipped subtask creation.");
					}
				}
			} // End Epic
		} catch (Exception e) {
			Logger.WriteLog("Exception while creating tasks and subtasks. Please check detailed exception info.");
			Logger.WriteLog("Exception message:" + e.getMessage());
			Logger.WriteLog("Exception stacktrace:" + e.getStackTrace());
		} finally {
			jiraHelperObject.DestroyJIRAInstance(client);
		}

		Logger.WriteLog("Total Epics created : " + totalEpicCreatedCounter);
		Logger.WriteLog("Total Tasks created : " + totalTasksCreatedCounter);
		Logger.WriteLog("Total Subtasks created : " + totalSubTasksCreatedCounter);
		ends = Instant.now();
		System.out.println("Total duration is :" + (Duration.between(starts, ends)));
		Logger.WriteLog("Total duration is :" + (Duration.between(starts, ends)));
		System.exit(0);

	}

	/**
	 * Function to Create Task / Subtask
	 * 
	 * @param name
	 *            : Name of the task / subtask
	 * @param parameterList
	 *            : The list of parameters associated with task / subtask
	 * @param parentKey
	 *            : Send "" if a task needs to be created, else the parent key.
	 * @param client
	 *            : Send the JIRARestClient object.
	 * @return
	 */
	public String CreateEpicOrTaskOrSubTask(String name, List<Parameter> parameterList, String parentKey, JiraRestClient client) {
		// Logger.WriteLog("CreateTaskOrSubTask");
		// System.out.println("CreateTaskOrSubTask");
		if (parentKey.equalsIgnoreCase("epic") && (parameterList == null || parameterList.size() < 1)) {

			System.out.println("No paramters provided for Epic. Skipping creation");
			Logger.WriteLog("No paramters provided for Epic. Skipping creation");
			return "";
		}

		JIRAHelper jiraHelperObject = new JIRAHelper();
		String key = "";
		IssueInput newIssue = null;
		final List<Promise<BasicIssue>> promises = Lists.newArrayList();
		// Get task name
		// Get task summary;
		// get task paramters.
		// populate task paramters one by one.
		// Create Task.
		try {
			IssueInputBuilder issuebuilder = new IssueInputBuilder();
			issuebuilder.setProjectKey(projectKey);

			for (Parameter parameterInfo : parameterList) {
				FieldInput paramField = null;
				// Logger.WriteLog("Create field for parameter :" +
				// parameterInfo.getField());
				try {
					String fieldType = parameterInfo.getFieldtype() == null ? "simple" : parameterInfo.getFieldtype();
					// Logger.WriteLog("Param:" +parameterInfo.getField().toLowerCase() );
					switch (parameterInfo.getField().toLowerCase()) {
					case "summary":
						// Logger.WriteLog("Creating summary field");
						issuebuilder.setSummary(FormatInputValue(parameterInfo.getValue()));
						// Logger.WriteLog("Done setting summary field");
						break;
					case "description":
						// Logger.WriteLog("Creating description field");
						issuebuilder.setDescription(FormatInputValue(parameterInfo.getValue()));
						break;
					case "datereported":
						// Logger.WriteLog("Creating datereported field");
						paramField = jiraHelperObject.CreateFieldValue(Fields.dateReported, FormatInputValue(parameterInfo.getValue()), "simple", true,
								"value");
						issuebuilder.setFieldInput(paramField);
						break;
					case "businessteam":
						// Logger.WriteLog("Creating businessteam field with
						// value:"+parameterInfo.getValue());
						paramField = jiraHelperObject.CreateFieldValue(Fields.businessTeam, parameterInfo.getValue(), "complex", true, "value");
						issuebuilder.setFieldInput(paramField);
						break;
					case "dailytaskssla":
						// Logger.WriteLog("Creating dailytaskssla field");
						paramField = jiraHelperObject.CreateFieldValue(Fields.dailyTasksSLA, FormatInputValue(parameterInfo.getValue()), "complex", true,
								"value");
						issuebuilder.setFieldInput(paramField);
						break;
					case "teamleader":
						// Logger.WriteLog("Here teamleader");
						paramField = jiraHelperObject.CreateFieldValue(Fields.teamLeader, FormatInputValue(parameterInfo.getValue()), "complex", true, "name");
						issuebuilder.setFieldInput(paramField);
						break;
					case "assignee":
						Logger.WriteLog("Creating assignee field with value: " + parameterInfo.getValue());
						issuebuilder.setAssigneeName(FormatInputValue(parameterInfo.getValue()));
						break;
					case "customfield_10004":
						Logger.WriteLog("Creating Epic Link " + parameterInfo.getValue());
						if (jiraHelperObject.VerifyIfRecordExistInJIRA(parameterInfo.getValue())) {
							paramField = jiraHelperObject.CreateFieldValue(parameterInfo.getField(), FormatInputValue(parameterInfo.getValue()), fieldType,
									true, "value");
							issuebuilder.setFieldInput(paramField);
						} else {
							Logger.WriteLog("Specified key not exists. Hence not linking epic.");
						}
						break;
					case "comment":
						// Do nothing
						break;
					case "link":
						// Do nothing
						break;
					default:
						Logger.WriteLog("Creating field: " + parameterInfo.getField());
						paramField = jiraHelperObject.CreateFieldValue(parameterInfo.getField(), FormatInputValue(parameterInfo.getValue()), fieldType, true,
								"value");
						issuebuilder.setFieldInput(paramField);
						break;
					}
				} catch (Exception e) {
					Logger.WriteLog("Exception while creating field :" + parameterInfo.getField() + " for Task: " + name);
				}
			}
			// Logger.WriteLog("Done creating parameter list");
			switch (parentKey) {
			case "epic":
				Logger.WriteLog("Create Epic");
				issuebuilder.setIssueTypeId(10000L);
				break;
			case "":
				Logger.WriteLog("Create Parent task");
				issuebuilder.setIssueTypeId(10002L);
				break;
			default:
				Logger.WriteLog("Create sub task to parent :" + parentKey);
				issuebuilder.setIssueTypeId(10003L);
				Map<String, Object> parent = new HashMap<String, Object>();
				parent.put("key", parentKey);
				FieldInput parentField = new FieldInput("parent", new ComplexIssueInputFieldValue(parent));
				issuebuilder.setFieldInput(parentField);
			}

			newIssue = issuebuilder.build();
			System.out.println("\tCreating: " + name);
			promises.add(client.getIssueClient().createIssue(newIssue));
			key = promises.get(promises.size() - 1).claim().getKey();
			String taskType = parentKey.equalsIgnoreCase("epic") == true ? "Epic" : parentKey == "" ? "Task" : "Subtask";
			Logger.WriteLog(taskType + " with key - " + key + " is created successfully");

		} catch (Exception e) {
			Logger.WriteLog("Exception while creating task. " + e.getMessage());
			Logger.WriteLog("Exception stacktrace. " + e.getStackTrace());
			Logger.WriteLog("Exception cause. " + e.getCause());
			// e.printStackTrace();
		}

		return key;
	}

	/**
	 * Function to format input value from xml. eg %date:yy-MM-dd - Test Summary converts to currentdate -Test Summary
	 * 
	 * @param inputString
	 *            : The string which needs to be formatted. IF plain string is passed, it is returned as it is.
	 * @return the formatted string.
	 */
	private String FormatInputValue(String inputString) {
		// Logger.WriteLog("FormatInputValue");
		String processedValue = "";
		try {
			if (inputString.contains("%")) {
				String valuetoReplace = inputString.substring(inputString.indexOf("%") + 1, inputString.lastIndexOf("%"));
				String stringWithinsymbol = inputString.substring(inputString.indexOf("%"), inputString.lastIndexOf("%") + 1);
				System.out.println("Get formatted value of " + valuetoReplace);
				if (valuetoReplace.contains("date")) {
					String[] dateFormat = valuetoReplace.split(":");
					if (dateFormat.length >= 2) {
						DateFormat df = new SimpleDateFormat(dateFormat[1]);
						Date dateobj = new Date();
						System.out.println("Formatted date from xml " + df.format(dateobj));
						processedValue = inputString.replace(stringWithinsymbol, df.format(dateobj));
						Logger.WriteLog("Data after formatting is " + processedValue);

					} else if (dateFormat.length == 1) {
						Logger.WriteLog("Defaulting date format.");
						DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
						Date dateobj = new Date();
						System.out.println("Default Formatted date " + df.format(dateobj));
						processedValue = inputString.replace(stringWithinsymbol, df.format(dateobj));
						Logger.WriteLog("Data after formatting is " + processedValue);
					} else {
						Logger.WriteLog("Date format is not entered properly.Please check");
					}

				} else if (valuetoReplace.contains("")) // placeholder for
														// future enhancements.
				{
					// Do nothing as of now.
				}
			} else // Return input as it doesnot contain any string to decode or
					// format.
			{
				processedValue = inputString;
			}
		} catch (Exception e) {
			System.out.println("Exception while formatting data.");
			Logger.WriteLog("Exception while formatting data." + e.getMessage());
			Logger.WriteLog("Exception stacktrace." + e.getStackTrace());
		}
		return processedValue;
	}

	private void AddCommentsIfNeeded(String key, List<Parameter> parameterList, JiraRestClient client) {
		// System.out.println("AddCommentsIfNeeded");
		for (Parameter parameter : parameterList) {
			if (parameter.getField().equalsIgnoreCase("comment")) {
				// System.out.println("Comment parameter found. Creating comment now.");
				Logger.WriteLog("Comment parameter found and adding for key :" + key);
				Issue issue = client.getIssueClient().getIssue(key).claim();
				String commentsToAdd = parameter.getValue();
				// System.out.println("Adding comments : " + commentsToAdd + " to key : " + key);
				client.getIssueClient().addComment(issue.getCommentsUri(), Comment.valueOf(commentsToAdd)).claim();

				// Verify comments added.
				issue = client.getIssueClient().getIssue(key).claim();

				final Iterator<Comment> iterator = issue.getComments().iterator();
				final Comment commentFromJIRA = iterator.next();
				Boolean commentsVerification = commentsToAdd.equals(commentFromJIRA.getBody());
				String outputMessage = commentsVerification == true ? "Comments are added successfully for key :" + key
						: "Comments not added due to some issues for key :" + key;
				System.out.println(outputMessage);
				Logger.WriteLog(outputMessage);

			}
		}
	}

	private void LinkIssuesIfNeeded(String key, List<Parameter> parameterList, JiraRestClient client) {
		for (Parameter parameter : parameterList) {
			if (parameter.getField().equalsIgnoreCase("link")) {
				// System.out.println("Link issues");
				Logger.WriteLog("Link issue now");
				LinkIssuesInput linkIssuesInput = new LinkIssuesInput(parameter.getValue(), key, parameter.getFieldtype(), null);
				client.getIssueClient().linkIssue(linkIssuesInput).claim();
				// System.out.println("Link issues done");
			}
		}
	}
}
