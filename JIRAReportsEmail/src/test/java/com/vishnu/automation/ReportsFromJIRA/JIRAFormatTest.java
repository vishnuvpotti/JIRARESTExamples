package com.vishnu.automation.ReportsFromJIRA;

import org.junit.Test;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.vishnu.automation.ReportsFromJIRA.Utilities.JIRAHelper;

import junit.framework.Assert;

/**
 * Unit test for simple App.
 */
public class JIRAFormatTest

{
	@Test
	public void RetrieveSystem() {
		String key = "";

		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "System", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);

		System.out.println("Value for System is :" + value);

	}
	@Test
	public void RetrieveStatus() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Status", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for System is :" + value);
		
	}
	
	@Test
	public void RetrieveRemedyID() {

		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Remedy ID", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Remedy ID is :" + value);
		
	}
	@Test
	public void RetrieveBroadridgeDepartment() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Broadridge Department", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Broadridge Department is :" + value);
		
	}
	
	@Test
	public void RetrieveSecurityLevel() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Security Level", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Security Level is :" + value);
		
	}
	@Test
	public void RetrieveIssueType() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Issue Type", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Issue Type is :" + value);
		
	}
	@Test
	public void RetrieveReporter() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Reporter", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Reporter is :" + value);		
	}
	@Test
	public void RetrieveUpdatedDate() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Updated", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Reporter is :" + value);		
	}
	@Test
	public void RetrieveAssignee() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Assignee", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Assignee is :" + value);		
	}
	
	@Test
	public void RetrieveSummary() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Summary", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Summary is :" + value);		
	}
	
	@Test
	public void RetrieveAgilityKey() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Agility Key", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Agility Key is :" + value);		
	}
	@Test
	public void RetrieveLabels() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Labels", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Labels is :" + value);		
	}
	@Test
	public void RetrieveLinkedIssues() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Linked Issues", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Linked Issues is :" + value);		
	}
	@Test
	public void RetrieveFixVersions() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Fix Version/s", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Fix Versions is :" + value);		
	}
	
	@Test
	public void GetFieldID() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Fix Version/s", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Fix Versions is :" + value);		
	}
	@Test
	public void GetFieldIDInJIRA() {
	
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetIDOfJIRAField("CHESS Rejection Message", client);
		System.out.println("Field ID is : "+value);
		Assert.assertTrue(!value.isEmpty());
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Fix Versions is :" + value);		
	}
	
	@Test
	public void RetrieveSecurityCode() {
		String key = "";
		JIRAHelper jirahelperObject = new JIRAHelper();
		JiraRestClient client = jirahelperObject.GetJIRAclient();
		String value = jirahelperObject.GetDataFromJIRAForField(key, "Security code", "name", client);
		jirahelperObject.DestroyJIRAInstance(client);
		
		System.out.println("Value for Security Code is :" + value);		
	}
	
	
}
