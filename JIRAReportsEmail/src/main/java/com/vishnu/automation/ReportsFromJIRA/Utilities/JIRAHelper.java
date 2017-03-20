package com.vishnu.automation.ReportsFromJIRA.Utilities;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Field;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueLink;
import com.atlassian.jira.rest.client.api.domain.Version;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
/**
 * Hello world!
 */
public class JIRAHelper {

	private static final String JIRA_URL = "jiraurl";
	private static final String JIRA_ADMIN_USERNAME = "uname"; // "";
	private static final String JIRA_ADMIN_PASSWORD = "pass";

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

	public void VerifyIfRecordExistInJIRA(String key) {
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
					// Logger.WriteLog("Here CreateFieldValue " + complexObjectKey + " with value : "+value);
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

	private String FormatDataFromJIRA(String issueStr, String field, String getFieldbyType, String jsonkey, JiraRestClient client) {
		// Logger.WriteLog("FormatDataFromJIRA");
		// System.out.println("FormatDataFromJIRA for field "+field);
		Issue issue = client.getIssueClient().getIssue(issueStr).claim();
		String extractedValue = "";
		if (jsonkey.isEmpty()) {
			jsonkey = "value";
		}

		try {
			if (getFieldbyType.equalsIgnoreCase("name")) {
				// System.out.println(issue.getFieldByName(field));
				extractedValue = (issue.getFieldByName(field).getValue()) == null ? ""
						: JSONHelper.GetCustomFieldValueFromJSON(issue.getFieldByName(field).getValue().toString(), jsonkey);
			} else {
				if (getFieldbyType.equalsIgnoreCase("id")) {
					extractedValue = (issue.getField(field).getValue()) == null ? ""
							: JSONHelper.GetCustomFieldValueFromJSON(issue.getField(field).getValue().toString(), jsonkey);
				} else {
					System.out.println("Invalid option.");
				}
			}
		} catch (Exception e) {
			// System.out.println("Exception while formatting data from JIRA." + e.getMessage());
			Logger.WriteLog("Exception while formatting data from JIRA." + e.getMessage());
		}
		// Logger.WriteLog("Formatted data is : " +extractedValue );
		// System.out.println("extractedValue " + extractedValue.trim());
		return extractedValue.trim();
	}

	private String FormatDataFromJIRA(Issue issue, String field, String getFieldbyType, String jsonkey, JiraRestClient client) {
		// Logger.WriteLog("FormatDataFromJIRA");
		// System.out.println("FormatDataFromJIRA for field "+field);

		String extractedValue = "";
		if (jsonkey.isEmpty()) {
			jsonkey = "value";
		}

		try {
			if (getFieldbyType.equalsIgnoreCase("name")) {
				// System.out.println(issue.getFieldByName(field));
				extractedValue = (issue.getFieldByName(field).getValue()) == null ? ""
						: JSONHelper.GetCustomFieldValueFromJSON(issue.getFieldByName(field).getValue().toString(), jsonkey);
			} else {
				if (getFieldbyType.equalsIgnoreCase("id")) {
					extractedValue = (issue.getField(field).getValue()) == null ? ""
							: JSONHelper.GetCustomFieldValueFromJSON(issue.getField(field).getValue().toString(), jsonkey);
				} else {
					System.out.println("Invalid option.");
				}
			}
		} catch (Exception e) {
			// System.out.println("Exception while formatting data from JIRA." + e.getMessage());
			Logger.WriteLog("Exception while formatting data from JIRA." + e.getMessage());
		}
		// Logger.WriteLog("Formatted data is : " +extractedValue );
		// System.out.println("extractedValue " + extractedValue.trim());
		return extractedValue.trim();
	}

	/**
	 * Function to retrieve values for JIRA Fields.
	 * 
	 * @param issueKey
	 *            : Send the key in JIRA
	 * @param field
	 *            : Send the field name . eg - summary / Summary / Issue Type / Security Level / Custom Field name / customfield_10032
	 * @param getFieldbyType
	 *            : Choose from : name / id
	 * @param jsonKey
	 *            : Send the key to parse Json : eg :value / name / description . "value" is used by default
	 * @param client
	 *            : Send the JIRARestClient instance.
	 * @return
	 */
	public String GetDataFromJIRAForField(String issueKey, String field, String getFieldbyType, JiraRestClient client) {
		String returnValue = "";
		Issue issue = client.getIssueClient().getIssue(issueKey).claim();
		try {
			// System.out.println("Get Field: " + field.toLowerCase());
			switch (field.toLowerCase()) {
			case "key":
				returnValue = issue.getKey();
				break;
			case "status":
				returnValue = issue.getStatus().getName();
				break;
			case "type":
			case "issuetype":
			case "issue type":
				returnValue = issue.getIssueType().getName().toString();
				break;
			case "summary":
				returnValue = issue.getSummary().toString();
				break;
			case "updated":
				returnValue = issue.getUpdateDate().toString("d/MM/yyyy hh:mm:sss a");
				break;
			case "created":
				returnValue = issue.getCreationDate().toString("d/MM/yyyy hh:mm:sss a");
				break;
			case "project":
				returnValue = issue.getProject().getName().toString();
				break;
			case "reporter":
				returnValue = issue.getReporter().getEmailAddress().toString();
				break;
			case "assignee":
				returnValue = issue.getAssignee().getDisplayName();
				break;
			case "labels":
			case "Labels":
				// System.out.println("Get Labels: " + issue.getLabels());
				returnValue = StringUtils.join(issue.getLabels(), ",");
				break;
			case "fix version/s":
				List<String> versionsList = new ArrayList<String>();
				for (Version version : issue.getFixVersions()) {
					versionsList.add(version.getName());
					// System.out.println("version name:"+version.getName());
				}
				returnValue = StringUtils.join(versionsList, ",");
				break;
			case "linked issues":
				List<String> valuesList = new ArrayList<String>();
				for (IssueLink link : issue.getIssueLinks()) {
					// System.out.println("LinkedIssues:"+link.getTargetIssueKey());
					valuesList.add(link.getTargetIssueKey());
				}
				returnValue = StringUtils.join(valuesList, ",");
				break;
			case "security level":
			case "securitylevel":
				returnValue = FormatDataFromJIRA(issue, field, getFieldbyType, "name", client);
				break;
			default:
				returnValue = FormatDataFromJIRA(issue, field, getFieldbyType, "", client);
				break;
			}
		} catch (Exception e) {
			System.out.println("Exception occured while getting value for JIRA field - " + field);
			Logger.WriteLog("Exception while getting value for JIRA field. " + field);
			Logger.WriteLog("Exception stacktrace - " + e.getStackTrace());
			Logger.WriteLog("Exception Message - " + e.getMessage());
		}
		return returnValue;
	}

	/**
	 * Function to retrieve values for JIRA Fields.
	 * 
	 * @param issueKey
	 *            : Send the key in JIRA
	 * @param field
	 *            : Send the field name . eg - summary / Summary / Issue Type / Security Level / Custom Field name / customfield_10032
	 * @param getFieldbyType
	 *            : Choose from : name / id
	 * @param jsonKey
	 *            : Send the key to parse Json : eg :value / name / description . "value" is used by default
	 * @param client
	 *            : Send the JIRARestClient instance.
	 * @return
	 */
	public String GetDataFromJIRAForField(Issue issue, String field, String getFieldbyType, JiraRestClient client) {

		String returnValue = "";

		// Issue issue = client.getIssueClient().getIssue(issueKey).claim();
		try {
			switch (field.toLowerCase()) {
			case "key":
				returnValue = issue.getKey();
				break;
			case "status":
				returnValue = issue.getStatus().getName();
				break;
			case "type":
			case "issuetype":
			case "issue type":
				returnValue = issue.getIssueType().getName().toString();
				break;
			case "summary":
				returnValue = issue.getSummary().toString();
				break;
			case "updated":
				returnValue = issue.getUpdateDate().toString("d/MM/yyyy hh:mm:sss a");
				break;
			case "created":
				returnValue = issue.getCreationDate().toString("d/MM/yyyy hh:mm:sss a");
				break;
			case "project":
				returnValue = issue.getProject().getName().toString();
				break;
			case "reporter":
				returnValue = issue.getReporter().getEmailAddress().toString();
				break;
			case "assignee":
				returnValue = issue.getAssignee().getDisplayName();
				break;
			case "labels":
			case "Labels":
				returnValue = StringUtils.join(issue.getLabels(), ",");
				break;
			case "fix version/s":
				List<String> versionsList = new ArrayList<String>();
				for (Version version : issue.getFixVersions()) {
					versionsList.add(version.getName());
				}
				returnValue = StringUtils.join(versionsList, ",");
				break;
			case "linked issues":
				List<String> valuesList = new ArrayList<String>();
				for (IssueLink link : issue.getIssueLinks()) {
					valuesList.add(link.getTargetIssueKey());
				}
				returnValue = StringUtils.join(valuesList, ",");
				break;
			case "security level":
			case "securitylevel":
				returnValue = FormatDataFromJIRA(issue, field, getFieldbyType, "name", client);
				break;
			default:
				returnValue = FormatDataFromJIRA(issue, field, getFieldbyType, "", client);
				break;
			}
		} catch (Exception e) {
			// System.out.println("Exception occured while getting value for JIRA field - "+field);
			Logger.WriteLog("Exception while getting value for JIRA field. " + field);
			Logger.WriteLog("Exception stacktrace - " + e.getStackTrace());
			Logger.WriteLog("Exception Message - " + e.getMessage());
		}
		return returnValue;
	}

	public String GetIDOfJIRAField(String field, JiraRestClient client) {
		String fieldIDToreturn = "";
		Iterable<Field> fieldsList = null;
		try {
			fieldsList = client.getMetadataClient().getFields().get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Field currentFieldInList : fieldsList) {
			if (field.equals(currentFieldInList.getName())) {
				// System.out.println("Field ID :" + currentFieldInList.getId() + " Name ;" + currentFieldInList.getName());
				fieldIDToreturn = currentFieldInList.getId();
			}
		}
		return fieldIDToreturn;
	}

	public Set<String> GetSetOfRequestedJIRAFields(String[] fieldArray, JiraRestClient client) {
		Set<String> myFields = new HashSet<String>();
		for (String field : fieldArray) {
			Logger.WriteLog("Adding field : " + field + " to set");
			myFields.add(GetIDOfJIRAField(field, client));
		}
		// Add mandatory fields to set

		myFields.add("issue");
		myFields.add("summary");
		myFields.add("issuetype");
		myFields.add("updated");
		myFields.add("status");
		myFields.add("created");
		myFields.add("Approved");
		myFields.add("project");
		Logger.WriteLog("Size of Set after adding all fields: " + myFields.size());
		return myFields;
	}

}
