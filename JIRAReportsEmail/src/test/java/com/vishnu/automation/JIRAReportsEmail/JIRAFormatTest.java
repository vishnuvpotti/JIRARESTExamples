package com.vishnu.automation.JIRAReportsEmail;

import org.junit.Test;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.vishnu.automation.JIRAReportsEmail.Utilities.JIRAHelper;

/**
 * Unit test for JIRA Functions.
 */
public class JIRAFormatTest

{
	@Test
	public void RetrieveSystem() {
		String key = "SAMPLE-3655";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "System", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);

		System.out.println("Value for System is :" + value);

	}
	@Test
	public void RetrieveStatus() {
		String key = "SAMPLE-1475";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Status", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for System is :" + value);
		
	}
	
	@Test
	public void RetrieveRemedyID() {
		String key = "SAMPLE-4009";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Remedy ID", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Remedy ID is :" + value);
		
	}

	
	@Test
	public void RetrieveSecurityLevel() {
		String key = "SAMPLE-1458";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Security Level", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Security Level is :" + value);
		
	}
	@Test
	public void RetrieveIssueType() {
		String key = "SAMPLE-1458";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Issue Type", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Issue Type is :" + value);
		
	}
	@Test
	public void RetrieveReporter() {
		String key = "SAMPLE-1971";
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
