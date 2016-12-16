package com.jiraautomation.DailyTaskCreator.Utilities;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.junit.Test;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

/**
 * Hello world!
 *
 */
public class JIRAHelper {

	private static final String JIRA_URL = "url";
	private static final String JIRA_ADMIN_USERNAME = "username"; // "";
	private static final String JIRA_ADMIN_PASSWORD = "password";

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

	public void VerifyIfRecordExistInJIRA(String key) {
		//String key = "CTP-14345";
		Issue issueNum = null;
		JiraRestClient client = new JIRAHelper().GetJIRAclient();
		try {
			issueNum = client.getIssueClient().getIssue(key).claim();
		} catch (RestClientException e) {
			// e.printStackTrace();
		}

		if (issueNum == null) {
			System.out.println("No such key");
		} else {

			System.out.println("issuenum summary:" + issueNum.getSummary());
		}

	}
	
	/**
	 * Function used to create field with values.
	 * 
	 * @param fieldName
	 * @param value
	 * @param fieldType
	 *            : Send simple / complex. If this field is sent blank, then by
	 *            default simple.
	 * @return
	 */
	public FieldInput CreateFieldValue(String fieldName, String value, String fieldType, Boolean fieldIsString) {
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
					field = new FieldInput(fieldName, ComplexIssueInputFieldValue.with("value", value));
				} else {
					field = new FieldInput(fieldName,
							ComplexIssueInputFieldValue.with("value", Integer.parseInt(value)));
				}

			}
		} catch (Exception e) {
			Logger.WriteLog("Exception while creating field." + fieldName);
		}
		return field;
	}

	/*
	public String FormatDataFromJIRA(Issue issue, String field, String fieldType) {
		// Logger.WriteLog("FormatDataFromJIRA");
		String extractedValue = "";
		try {
			if (fieldType.equalsIgnoreCase("simple")) {
				extractedValue = (issue.getField(field).getValue()) == null ? ""
						: issue.getField(field).getValue().toString();
			} else {
				extractedValue = (issue.getField(field).getValue()) == null ? ""
						: JSONHelper.GetCustomFieldValueFromJSON(issue.getField(field).getValue().toString(), "value");
			}

		} catch (Exception e) {
			System.out.println("Exception while formatting data from JIRA." + e.getMessage());
			Logger.WriteLog("Exception while formatting data from JIRA." + e.getMessage());
		}
		// Logger.WriteLog("Formatted data is : " +extractedValue );
		return extractedValue.trim();
	} */

	private int FormatStringtoInteger(String string) {

		int value = 0;
		try {
			Double dvalue = Double.valueOf(string);
			// Format to int
			value = dvalue.intValue();
			// System.out.println("Converted value:" + value);
		} catch (NumberFormatException e) {
			// returnValue= false;
		} catch (NullPointerException e) {
			// returnValue= false;
		}
		return value;
	}


}
