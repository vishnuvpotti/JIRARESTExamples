package com.vishnu.automation.DailyTaskCreator.Utilities;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

/**
 * Hello world!
 */
public class JIRAHelper {

	private static final String JIRA_URL = "your jira server";
	private static final String JIRA_ADMIN_USERNAME = "youraccount"; // "";
	private static final String JIRA_ADMIN_PASSWORD = "yourpassword";

	public JiraRestClient GetJIRAclient() {
		JiraRestClient client = null;
		URI uri = null;
		try {
			uri = new URI(JIRA_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		client = factory.createWithBasicHttpAuthentication(uri, JIRA_ADMIN_USERNAME, JIRA_ADMIN_PASSWORD);

		return client;
	}

	public Boolean DestroyJIRAInstance(JiraRestClient client) {
		Boolean returnvalue = true;

		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while closing JIRA instance");
			Logger.WriteLog("Exception while closing JIRA instance");
			returnvalue = false;
		}

		return returnvalue;
	}

	public String CreateTask(String project, String key, HashMap<String, String> parameterList) {
		String requestNumber = "";

		return requestNumber;

		// Create Task.
	}

	public Boolean VerifyIfRecordExistInJIRA(String key) {
		Boolean returnValue = true;
		Issue issueNum = null;
		JiraRestClient client = new JIRAHelper().GetJIRAclient();
		try {
			issueNum = client.getIssueClient().getIssue(key).claim();
			issueNum.getSummary();
		} catch (RestClientException e) {
		//	e.printStackTrace();
			returnValue = false;
		} finally {
			DestroyJIRAInstance(client);
		}
		return returnValue;

	}

	/**
	 * Function used to create field with values.
	 * 
	 * @param fieldName
	 * @param value
	 * @param fieldType
	 *            : Send simple / complex. If this field is sent blank, then by default simple.
	 * @return
	 */
	public FieldInput CreateFieldValue(String fieldName, String value, String fieldType, Boolean fieldIsString, String complexObjectKey) {
		// Logger.WriteLog("CreateFieldValue" + fieldName);
		FieldInput field = null;
		try {
			if (fieldType.equalsIgnoreCase("simple") || fieldType.equalsIgnoreCase("")) {
				if (fieldIsString) {
					field = new FieldInput(fieldName, value);
				} else {
					field = new FieldInput(fieldName, Integer.parseInt(value));
				}
			} else {
				if (fieldIsString) {
					field = new FieldInput(fieldName, ComplexIssueInputFieldValue.with(complexObjectKey, value));
				} else {
					field = new FieldInput(fieldName, ComplexIssueInputFieldValue.with(complexObjectKey, Integer.parseInt(value)));
				}

			}
		} catch (Exception e) {
			Logger.WriteLog("Exception while creating field." + fieldName);
		}
		return field;
	}


}
