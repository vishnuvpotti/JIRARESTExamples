package com.vishnu.exceltojiraupdator.Core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.vishnu.exceltojiraupdator.DataClass.*;
import com.vishnu.exceltojiraupdator.Utilities.JIRAHelper;
import com.vishnu.exceltojiraupdator.Utilities.Logger;

public class JIRATask {

	public void UpdateDataToJIRA(List<IssueData> issueDataList) {
		System.out.println("Start processing all issues in the list");
		JIRAHelper jiraHelperObject = null;
		JiraRestClient client = null;
		try {

			jiraHelperObject = new JIRAHelper();
			client = jiraHelperObject.GetJIRAclient();
			for (IssueData issueData : issueDataList) {
				Logger.WriteLog("Perform updation for key:" + issueData.getKey());
				System.out.println("Perform updation for key:" + issueData.getKey());
				PerformUpdation(issueData, jiraHelperObject, client);
				Logger.WriteLog("Done updating data for key:" + issueData.getKey());
				System.out.println("Done updating data for key:" + issueData.getKey());
			}
		} catch (Exception e) {
			System.out.println("Exception while updating issues.Exception :" + e.getMessage());
			Logger.WriteLog("Exception while updating issues.Exception :" + e.getMessage());
			e.printStackTrace();
			Logger.WriteLog("Exception: " + e.getMessage());
		} finally {
			jiraHelperObject.DestroyJIRAInstance(client);
		}
	}

	private void PerformUpdation(IssueData issue, JIRAHelper jiraHelperObject, JiraRestClient client) {
		System.out.println("------------------Start Updation for Issue:"+issue.getKey() + "------------------");
		Logger.WriteLog("------------------Start Updation for Issue:"+issue.getKey() + "------------------");
		Map<String, FieldInput> fieldsmap = new HashMap<String, FieldInput>();

		for (Map<String, String> mapList : issue.getFieldValueList()) {
			for (Entry<String, String> keyValuePair : mapList.entrySet()) {
				try {
					System.out.println("Adding field:" + keyValuePair.getKey() + " to fieldmap for updation");
					FieldInput field = jiraHelperObject.CreateFieldValue(keyValuePair.getKey(), keyValuePair.getValue(), "simple", true);
					fieldsmap.put(keyValuePair.getKey(), field);
					Logger.WriteLog("Updated for field : "+keyValuePair.getKey() +" with value : \"" + keyValuePair.getValue() +"\" for updation");
				} catch (Exception e) {
					Logger.WriteLog("Exception while preparing Data inputs for JIRA updation for key - " + issue.getKey());
				}
			}
		}
		Logger.WriteLog("*********Data prepared for updating*********");
		// Update the issue.
		try {
			IssueInput input = new IssueInput(fieldsmap);
			client.getIssueClient().updateIssue(issue.getKey(), input).claim();
		} catch (Exception e) {
			System.out.println("Exception when performing updation for issue." + issue.getKey());
			
			Logger.WriteLog("Exception when performing updation for issue." + issue.getKey());
			Logger.WriteLog("Exception : "+e.getMessage()); 
		}
	}
}
