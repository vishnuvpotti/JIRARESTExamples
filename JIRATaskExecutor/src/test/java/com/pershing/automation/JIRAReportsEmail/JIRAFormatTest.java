package com.pershing.automation.JIRAReportsEmail;

import org.junit.Test;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.vishnu.automation.ExecuteTasksOnJIRA.Utilities.JIRAHelper;

/**
 * Unit test for simple App.
 */
public class JIRAFormatTest

{
	@Test
	public void RetrieveSystem() {
		String key = "SUMMIT-3655";
//		String key = "BR-517";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "System", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);

		System.out.println("Value for System is :" + value);

	}
	@Test
	public void RetrieveStatus() {
		String key = "PSDFTC-1475";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Status", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for System is :" + value);
		
	}
	
	@Test
	public void RetrieveRemedyID() {
//		String key = "SUMMIT-3655";
		String key = "SUMMIT-4009";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Remedy ID", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Remedy ID is :" + value);
		
	}
	@Test
	public void RetrieveBroadridgeDepartment() {
		String key = "PSDFTC-1458";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Broadridge Department", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Broadridge Department is :" + value);
		
	}
	
	@Test
	public void RetrieveSecurityLevel() {
		String key = "PSDFTC-1458";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Security Level", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Security Level is :" + value);
		
	}
	@Test
	public void RetrieveIssueType() {
		String key = "PSDFTC-1458";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Issue Type", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Issue Type is :" + value);
		
	}
	@Test
	public void RetrieveReporter() {
		String key = "PSDFTC-1971";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Reporter", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Reporter is :" + value);		
	}
	@Test
	public void RetrieveUpdatedDate() {
		String key = "PSDFTC-1973";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Updated", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Reporter is :" + value);		
	}
	@Test
	public void RetrieveAssignee() {
		String key = "PSDFTC-1997";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Assignee", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Assignee is :" + value);		
	}
	
	@Test
	public void RetrieveSummary() {
		String key = "PSDFTC-1971";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Summary", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Summary is :" + value);		
	}
	
	@Test
	public void RetrieveAgilityKey() {
		String key = "PSDFTC-2215";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Agility Key", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Agility Key is :" + value);		
	}
	@Test
	public void RetrieveLabels() {
		String key = "PSDFTC-1308";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Labels", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Labels is :" + value);		
	}
	@Test
	public void RetrieveLinkedIssues() {
		String key = "TSB-680";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Linked Issues", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Linked Issues is :" + value);		
	}
	@Test
	public void RetrieveFixVersions() {
		String key = "PSDFTC-1470";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Fix Version/s", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Fix Versions is :" + value);		
	}
	
}
