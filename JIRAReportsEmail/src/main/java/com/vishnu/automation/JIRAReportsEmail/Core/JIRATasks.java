package com.vishnu.automation.JIRAReportsEmail.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;
import com.vishnu.automation.JIRAReportsEmail.Dataclass.IssueData;
import com.vishnu.automation.JIRAReportsEmail.Dataclass.JIRAOperations;
import com.vishnu.automation.JIRAReportsEmail.Utilities.JIRAHelper;
import com.vishnu.automation.JIRAReportsEmail.Utilities.Logger;

public class JIRATasks {
	
	public static ArrayList<IssueData> recordsList = null;
	
	public void ProcessJIRATasks(JIRAOperations jiraobject) {
		recordsList = new ArrayList<IssueData>();
		Logger.WriteLog("Start processing JIRA tasks");
		JIRAHelper jiraHelperObject = new JIRAHelper();
		JiraRestClient client = jiraHelperObject.GetJIRAclient();
		Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql(jiraobject.getJql(), 300, 0, null);

		System.out.println("Total records after executing JQL:" + searchJqlPromise.claim().getTotal());
		Logger.WriteLog("Total records after executing JQL :" + searchJqlPromise.claim().getTotal());

		try {
			for (Issue issue : searchJqlPromise.claim().getIssues()) {
				IssueData dataOfIssue = new IssueData();
				List<Map<String, String>> fieldValueList = new ArrayList<Map<String, String>>();
				System.out.println("Get value for these fields list:" + jiraobject.getFields());
				Logger.WriteLog("Get value for these fields list:" + jiraobject.getFields());

				String[] fieldArray = jiraobject.getFields().split(",");
				for (String field : fieldArray) {
					System.out.println("Get value for field :" + field);
					Logger.WriteLog("Get value for field :" + field);

					Map<String, String> fieldDictionary = new HashMap<>();
					fieldDictionary.put(field, jiraHelperObject.GetDataFromJIRAForField(issue, field, "name", client));
					fieldValueList.add(fieldDictionary);
				}
				dataOfIssue.setDataList(fieldValueList);
				// System.out.println("Data fields count : " + issueDataList.size());
				recordsList.add(dataOfIssue);
			}
		} catch (Exception e) {
			Logger.WriteLog("Exception occured while retrieving values for field. Exception : " + e.getMessage());
			Logger.WriteLog("Exception stacktrace : " + e.getStackTrace());
			System.out.println("Exception occured while retrieving values for field. Exception : " + e.getMessage());
			System.out.println("Exception stacktrace : " + e.getStackTrace());

		} finally {
			jiraHelperObject.DestroyJIRAInstance(client);
		}

		// Write to word / excel document
		Logger.WriteLog("Save the results in " + jiraobject.getFileType() + "format");
		System.out.println("Save the results in " + jiraobject.getFileType() + "format");

		SaveResults saveResultsObject = new SaveResults(jiraobject.getFileType());
		saveResultsObject.SaveFile(jiraobject.getFields(), recordsList, jiraobject.getFileName());
	}
}
