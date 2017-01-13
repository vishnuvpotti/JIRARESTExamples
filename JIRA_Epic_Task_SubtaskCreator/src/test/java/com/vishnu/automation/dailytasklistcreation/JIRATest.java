package com.vishnu.automation.dailytasklistcreation;

import org.junit.Assert;
import org.junit.Test;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.vishnu.automation.DailyTaskCreator.Utilities.JIRAHelper;




/**
 * Unit test for simple App.
 */
public class JIRATest

{
	@Test
	public void VerifyIssueExist() {
		String key = "PSIT-486";
		JIRAHelper jirahelperObject = new JIRAHelper();
        Assert.assertTrue(jirahelperObject.VerifyIfRecordExistInJIRA(key));
	}
	@Test
	public void VerifyIssueNotExist() {
		String key = "ODT-481";
		JIRAHelper jirahelperObject = new JIRAHelper();
		Assert.assertFalse(jirahelperObject.VerifyIfRecordExistInJIRA(key));
	}

}
