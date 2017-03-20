package com.vishnu.automation.ExecuteTasksOnJIRA.Core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.util.concurrent.Promise;
import com.vishnu.automation.ExecuteTasksOnJIRA.Dataclass.Action;
import com.vishnu.automation.ExecuteTasksOnJIRA.Utilities.JIRAHelper;
import com.vishnu.automation.ExecuteTasksOnJIRA.Utilities.Logger;

public class JIRATasks {

	/**
	 * Function to process the JIRA task.
	 * 
	 * @param jiraobject
	 *            : Pass the JIRAOperations data from xml.
	 */
	public void ProcessJIRATasks(String jql, List<Action> actionList) {

		// Get results from JQL
		Logger.WriteLog("Start processing JIRA tasks");
		JIRAHelper jiraHelperObject = new JIRAHelper();
		JiraRestClient client = jiraHelperObject.GetJIRAclient();
		Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql(jql, 300, 0, null);

		System.out.println("Total records after executing JQL:" + searchJqlPromise.claim().getTotal());
		Logger.WriteLog("Total records after executing JQL :" + searchJqlPromise.claim().getTotal());
		try {
			for (Issue issue : searchJqlPromise.claim().getIssues()) {
				System.out.println("Processing issue :" + issue.getKey());
				// System.out.println("Epic link value:" + issue.getFieldByName(actionList.get(0).getParameterList().getField()).getId());
				PerformTasksForIssues(issue, actionList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jiraHelperObject.DestroyJIRAInstance(client);
		}
	}

	public void PerformTasksForIssues(Issue issue, List<Action> actions) {
		JIRAHelper jiraHelperObject = new JIRAHelper();
		JiraRestClient client = jiraHelperObject.GetJIRAclient();
		try {
			Map<String, FieldInput> fieldsmap = new HashMap<String, FieldInput>();
			for (Action action : actions) {

				switch (action.getParameterList().getField()) {
				case "Epic Link":
				case "epic link":
					String epicNumber = "";
					if (action.getParameterList().getFieldlookup().equalsIgnoreCase("true")) {
						// Get Epic number.
						epicNumber = GetValueFromLookupKey(action.getParameterList().getLookupfieldName(), action.getParameterList().getValue());
					}

					// System.out.println("epicNumber"+epicNumber);
					FieldInput epicLinkField = jiraHelperObject.CreateFieldValue(GetFieldIDFromJIRA(issue, action.getParameterList().getField()), epicNumber,
							"simple", true, "value");
					fieldsmap.put(action.getParameterList().getField(), epicLinkField);
					break;

				case "comment":
					System.out.println("Adding comments not implemented yet");
					break;
				case "link":
					System.out.println(" Creating links not implemented yet");
					break;
				default:
					System.out.println("Creating for field:" + action.getParameterList().getField());
					System.out.println("Creating for value:" + action.getParameterList().getValue());
					String paramID = GetFieldIDFromJIRA(issue, action.getParameterList().getField());
					System.out.println("Param ID:" + paramID);
					String paramValue = action.getParameterList().getValue();
					String paramType = action.getParameterList().getFieldtype();
					FieldInput customField = jiraHelperObject.CreateFieldValue(paramID, paramValue, paramType, true, "value");
					fieldsmap.put(action.getParameterList().getField(), customField);
					System.out.println("Creating for field :" + action.getParameterList().getField() + " is done");
				}
			}
			IssueInput input = new IssueInput(fieldsmap);
			client.getIssueClient().updateIssue(issue.getKey(), input).claim();
			Logger.WriteLog("Done updating for record :" + issue.getKey());
			System.out.println("Done updating for record :" + issue.getKey());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured with message :" + e.getMessage());
			Logger.WriteLog("Exception:" + e.getMessage());
		} finally {
			jiraHelperObject.DestroyJIRAInstance(client);
		}
	}

	public String GetFieldIDFromJIRA(Issue issue, String fieldName) {
		String fieldID = "";
		try {
			switch (fieldName) {
			case "summary":
				fieldID = "summary";
				break;
			case "comment":
				fieldID = "comment";
				break;
			case "link":
				fieldID = "link";
				break;
			}
			fieldID = issue.getFieldByName(fieldName).getId();
		} catch (Exception e) {
		}
		return fieldID;
	}

	public String GetValueFromLookupKey(String lookupfieldname, String pattern) {
		String lookupValue = "";

		JIRAHelper jiraHelperObject = new JIRAHelper();
		JiraRestClient client = jiraHelperObject.GetJIRAclient();

		// Below replacement needed if we have reserved characters / symbols identified by atlassian in our pattern.
		String jql = FormatInputValue(pattern).replace("-", "//-");
		jql = "\"" + lookupfieldname + "\" ~ \"" + jql + "\"";
		// System.out.println("NEw JQL is "+jql);
		Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql(jql);
		if (searchJqlPromise.claim().getTotal() >= 1) {
			// Get first key.
			lookupValue = searchJqlPromise.claim().getIssues().iterator().next().getKey();
		}
		System.out.println("Lookup value is " + lookupValue);
		return lookupValue;

	}

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
						// System.out.println("Formatted date from xml " + df.format(dateobj));
						processedValue = inputString.replace(stringWithinsymbol, df.format(dateobj));
						Logger.WriteLog("Data after formatting is " + processedValue);

					} else if (dateFormat.length == 1) {
						Logger.WriteLog("Defaulting date format.");
						DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
						Date dateobj = new Date();
						// System.out.println("Default Formatted date " + df.format(dateobj));
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
		// System.out.println("processedValue."+processedValue);
		return processedValue;
	}
}
