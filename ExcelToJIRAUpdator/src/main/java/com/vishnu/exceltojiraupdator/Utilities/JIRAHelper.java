package com.vishnu.exceltojiraupdator.Utilities;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Objects;

import com.atlassian.jira.rest.client.api.IssueRestClient.Expandos;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.ChangelogGroup;
import com.atlassian.jira.rest.client.api.domain.ChangelogItem;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

public class JIRAHelper {

	private static final String JIRA_URL = "your jira url";
	private static final String JIRA_ADMIN_USERNAME = "testaccount"; // "";
	private static final String JIRA_ADMIN_PASSWORD = "testpassword";

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
		// System.out.println("Client is null? " + (client == null));
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

	/**
	 * Function used to create field with values.
	 * 
	 * @param fieldName
	 * @param value
	 * @param fieldType
	 *            : Send simple / complex. If this field is sent blank, then by default simple.
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
					field = new FieldInput(fieldName, ComplexIssueInputFieldValue.with("value", Integer.parseInt(value)));
				}

			}
		} catch (Exception e) {
			Logger.WriteLog("Exception while creating field." + fieldName);
		}
		return field;
	}

	/**
	 * Function to retrieve value for a particular field from JIRA
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String GetFieldValueFromJIRA(Issue issue, String field) {

		// Logger.WriteLog("GetFieldValueFromJIRA for key:" + issue.getKey() + "
		// and field : " + field);
		// System.out.println("GetFieldValueFromJIRA for key:" + issue.getKey()
		// + " and field : " + field);
		String fieldValue = "";
		try {

			// JiraRestClient client = GetJIRAclient();
			// Promise<Issue> issueNum = client.getIssueClient().getIssue(key);
			// Issue issueNum = client.getIssueClient().getIssue(key).claim();
			switch (field) {
			case "status":
				fieldValue = issue.getStatus().getName().toString();
				break;
			case "summary":
				fieldValue = issue.getSummary().toString();
				break;
			case "issuetype":
				fieldValue = issue.getIssueType().getName().toString();
				break;
			case "updated":
				fieldValue = issue.getUpdateDate().toString();
				break;
			case "created":
				fieldValue = issue.getCreationDate().toString();
				break;
			case "project":
				fieldValue = issue.getProject().getName().toString();
				break;
			default:
				// Logger.WriteLog("Getting for default type");
				fieldValue = issue.getField(field).getValue() == null ? ""
						: JSONHelper.GetCustomFieldValueFromJSON(issue.getField(field).getValue().toString(), "value");
				// System.out.println("Value from JIRA for custom field :" +
				// field + " is " + fieldValue);
				// Logger.WriteLog("Value from JIRA for custom field :" + field
				// + " is " + fieldValue);
				break;
			}
			// System.out.println("Value from JIRA for field :" + field + " is "
			// + fieldValue);
			// Logger.WriteLog("Value from JIRA for field :" + field + " is " +
			// fieldValue);
		} catch (Exception e) {
			System.out.println("Exception while getting from JIRA for field :" + field + " is " + fieldValue);
			Logger.WriteLog("Exception while getting from JIRA for field :" + field + " Exception details " + e.getMessage());
			// e.printStackTrace();
		}
		return fieldValue;
	}

	public String FormatDataFromJIRA(Issue issue, String field, String fieldType) {
		// Logger.WriteLog("FormatDataFromJIRA");
		String extractedValue = "";
		try {
			if (fieldType.equalsIgnoreCase("simple")) {
				extractedValue = (issue.getField(field).getValue()) == null ? "" : issue.getField(field).getValue().toString();
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

	public Boolean VerifyIfRecordExistInJIRA(String key) {
		// String key = "CTP-14345";
		Issue issueNum = null;
		Boolean returnValue = true;
		JiraRestClient client = new JIRAHelper().GetJIRAclient();
		try {
			issueNum = client.getIssueClient().getIssue(key).claim();
		} catch (RestClientException e) {
			// e.printStackTrace();
		}

		if (issueNum == null) {
			returnValue = false;
			// System.out.println("No such key");
		}
		// else {
		// returnValue =true;
		// //System.out.println("issuenum summary:" + issueNum.getSummary());
		// }

		DestroyJIRAInstance(client);
		return returnValue;

	}

	public void TransitionStatus(String key, String status, JiraRestClient client) {
		Logger.WriteLog("TransitionStatus: Start transition to " + status + " for key :" + key);
		try {
			Issue issue = client.getIssueClient().getIssue(key).claim();
			Promise<Iterable<Transition>> ptransitions = client.getIssueClient().getTransitions(issue);
			Iterable<Transition> transitions = ptransitions.claim();
			for (Transition t : transitions) {
				System.out.println(t.getName() + ":" + t.getId());
				if (t.getName().equalsIgnoreCase(status)) {
					// System.out.println("Matching");
					TransitionInput tinput = new TransitionInput(t.getId());
					client.getIssueClient().transition(issue, tinput).claim();
					break;
				}
			}
		} catch (Exception e) {
			Logger.WriteLog("Exception while transitioning status." + e.getMessage());
		}
	}

	private Boolean TransitionIssue(String key, String transition, JiraRestClient client) throws IOException {
		// System.out.println("Trasnition name passed :"+transition);
		Boolean returnValue = false;
		Issue issue = client.getIssueClient().getIssue(key).claim();
		Promise<Iterable<Transition>> ptransitions = client.getIssueClient().getTransitions(issue);
		Iterable<Transition> transitions = ptransitions.claim();

		for (Transition t : transitions) {
			// System.out.println(t.getName() + ":" + t.getId() );
			Logger.WriteLog(t.getName() + ":" + t.getId());
			if (t.getName().equalsIgnoreCase(transition)) {
				Logger.WriteLog("Performing transition as current value : " + t.getName() + "matching with expected transition value.");
				TransitionInput tinput = new TransitionInput(t.getId());
				client.getIssueClient().transition(issue, tinput).claim();
				returnValue = true;
				break;
			}
		}
		// System.out.println("TransitionIssue returnValue" + returnValue);
		return returnValue;
	}

	public Boolean PerformTransitions(String key, String transitions, JiraRestClient client) throws IOException {
		// System.out.println("PerformTransitions" + transitions);
		Boolean returnValue = true;
		if (transitions.contains(",")) {
			Logger.WriteLog("Transition contains multiple transitions");
			String[] transitionstatus = transitions.split(",");
			Logger.WriteLog("Number of transtions to be performed :" + transitionstatus.length);
			for (String transitiontochange : transitionstatus) {
				returnValue = returnValue & TransitionIssue(key, transitiontochange, client);
			}
		} else {
			returnValue = returnValue & TransitionIssue(key, transitions, client);
		}

		// System.out.println("PerformTransitions returnValue" + returnValue);
		// client.close();
		return returnValue;
	}

	/**
	 * Function to get History of Fields for issue.
	 * 
	 * @param key
	 *            : Send the issue key.
	 * @param fields
	 *            : Send the fields required as an array.
	 * @param client
	 *            : Send JIRARESTClient instance.
	 * @return :Returns values of all specified fields as coma separated String.History from multiple changes as a list.
	 */
	public List<String> GetAllHistoryForFields(String key, String[] fields, JiraRestClient client) {
		// System.out.println("Here GetAllHistoryForField");
		List<String> history = new ArrayList<String>();
		Expandos[] expandArr = new Expandos[] { Expandos.CHANGELOG };
		List<Expandos> expand = Arrays.asList(expandArr);
		final Issue issue = client.getIssueClient().getIssue(key, expand).claim();
		Iterable<ChangelogGroup> changeLogGroupList = issue.getChangelog();
		for (ChangelogGroup changelogGroup : changeLogGroupList) {
			// System.out.println(
			// "Outputting the changelog group created by '" + changelogGroup.getAuthor().getName() + "' at '" + changelogGroup.getCreated() + "'");
			Iterable<ChangelogItem> changelogItems = changelogGroup.getItems();
			String fieldValues = "";
			for (ChangelogItem changelogItem : changelogItems) {
				// Evaluate each changeitem.
				for (String field : fields) {
					if (changelogItem.getField().equalsIgnoreCase(field)) {
						// System.out.println("Value :" + changelogItem.getToString());
						fieldValues += changelogItem.getToString().trim() + ",";
					}
				}
			}
			// System.out.println("fieldValues :" + fieldValues);
			if (fieldValues.length() >= 1 && fieldValues.substring(0, fieldValues.lastIndexOf(',')).length() > 1) {
				// System.out.println("adding FieldValues:" + fieldValues.substring(0, fieldValues.lastIndexOf(',')));
				history.add(fieldValues.substring(0, fieldValues.lastIndexOf(',')));
			}
		}
		// System.out.println("Here GetAllHistoryForField done." + history.size());
		history.removeIf(Objects::isNull);
		return history;
	}

	/**
	 * Function to get History of Single Field for issue.
	 * 
	 * @param key
	 *            : Send the issue key.
	 * @param fields
	 *            : Send the fields required as an array.
	 * @param client
	 *            : Send JIRARESTClient instance.
	 * @return :Returns values of all specified fields as coma separated String.History from multiple changes as a list.
	 */
	public List<String> GetAllHistoryForSingleField(String key, String field, JiraRestClient client) {
		// System.out.println("Here GetAllHistoryForField");
		List<String> history = new ArrayList<String>();
		Expandos[] expandArr = new Expandos[] { Expandos.CHANGELOG };
		List<Expandos> expand = Arrays.asList(expandArr);
		final Issue issue = client.getIssueClient().getIssue(key, expand).claim();

		Iterable<ChangelogGroup> changeLogGroupList = issue.getChangelog();
		for (ChangelogGroup changelogGroup : changeLogGroupList) {
			// System.out.println(
			// "Outputting the changelog group created by '" + changelogGroup.getAuthor().getName() + "' at '" + changelogGroup.getCreated() + "'");
			Iterable<ChangelogItem> changelogItems = changelogGroup.getItems();
			String fieldValues = "";
			for (ChangelogItem changelogItem : changelogItems) {
				// Evaluate each changeitem.
				// for (String field : fields) {
				if (changelogItem.getField().equalsIgnoreCase(field)) {
					// System.out.println("Value :" + changelogItem.getToString());
					fieldValues += changelogItem.getToString().trim() + ",";
				}
				// }
			}
			// System.out.println("fieldValues :" + fieldValues);
			if (fieldValues.length() >= 1 && fieldValues.substring(0, fieldValues.lastIndexOf(',')).length() > 1) {
				// System.out.println("adding FieldValues:" + fieldValues.substring(0, fieldValues.lastIndexOf(',')));
				history.add(fieldValues.substring(0, fieldValues.lastIndexOf(',')));
			}
		}
		// System.out.println("Here GetAllHistoryForField done." + history.size());
		history.removeIf(Objects::isNull);
		return history;
	}
}
