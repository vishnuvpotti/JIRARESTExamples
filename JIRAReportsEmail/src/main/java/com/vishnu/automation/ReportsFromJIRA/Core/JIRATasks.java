package com.vishnu.automation.ReportsFromJIRA.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;
import com.vishnu.automation.ReportsFromJIRA.Dataclass.IssueData;
import com.vishnu.automation.ReportsFromJIRA.Dataclass.JIRAOperations;
import com.vishnu.automation.ReportsFromJIRA.Utilities.JIRAHelper;
import com.vishnu.automation.ReportsFromJIRA.Utilities.Logger;

public class JIRATasks {

	public static ArrayList<IssueData> recordsList = null;
	public static int totalRecordsCountFromSearch = 0;

	/**
	 * Function to process the JIRA task.
	 * 
	 * @param jiraobject
	 *            : Pass the JIRAOperations data from xml.
	 */
	public void ProcessJIRATasks(JIRAOperations jiraobject) {
		recordsList = new ArrayList<IssueData>();
		Logger.WriteLog("Start processing JIRA tasks");
		JIRAHelper jiraHelperObject = new JIRAHelper();
		JiraRestClient client = jiraHelperObject.GetJIRAclient();
		String[] fieldArray = jiraobject.getFields().split(",");
		Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql(jiraobject.getJql(), 500, 0,
				jiraHelperObject.GetSetOfRequestedJIRAFields(fieldArray, client));

		System.out.println("Total records after executing JQL:" + searchJqlPromise.claim().getTotal());
		Logger.WriteLog("Total records after executing JQL :" + searchJqlPromise.claim().getTotal());
		totalRecordsCountFromSearch = searchJqlPromise.claim().getTotal();

		try {
			System.out.println("Get value for these fields list:" + jiraobject.getFields());
			Logger.WriteLog("Get value for these fields list:" + jiraobject.getFields());
			for (Issue issue : searchJqlPromise.claim().getIssues()) {
				IssueData dataOfIssue = new IssueData();
				List<Map<String, String>> fieldValueList = new ArrayList<Map<String, String>>();

				for (String field : fieldArray) {
					// System.out.println("Get value for field :" + field);
					// Logger.WriteLog("Get value for field :" + field);

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
