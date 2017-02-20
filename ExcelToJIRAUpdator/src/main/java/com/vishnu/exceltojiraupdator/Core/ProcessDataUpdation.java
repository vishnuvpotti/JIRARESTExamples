package com.vishnu.exceltojiraupdator.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import com.vishnu.exceltojiraupdator.DataClass.IssueData;
import com.vishnu.exceltojiraupdator.Utilities.Logger;

public class ProcessDataUpdation {

	// public static HashMap<String, Integer> fieldsMap = new HashMap<String, Integer>();
	public static HashMap<String, String> field_JiraIDMap = new HashMap<String, String>();

	public void ProcessDataUpload(String fields, String excelPath) {
		System.out.println("Starting parsing of data from excel.");
		Logger.WriteLog("Starting parsing of data from excel.");

		List<IssueData> issueDataList = null;
		String[] fieldsWithKeys = null;
		try {
			fieldsWithKeys = GetFieldsToUpdate(fields);
			System.out.println("Fields length:" + fieldsWithKeys.length);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.WriteLog("Exception while getting fields to be updated." + e.getMessage());
		}

		System.out.println("Starting preparation of data from Excel.");
		Logger.WriteLog("Starting preparation of data from Excel.");
		ReadExcelData rd = new ReadExcelData();
		issueDataList = rd.GetFieldsDataFromExcel(fieldsWithKeys, field_JiraIDMap, excelPath);

		System.out.println("Prepared all data from Excel. Starting updation now.:");
		Logger.WriteLog("Prepared all data from Excel. Starting updation now.:");

		JIRATask jiraTaskObject = new JIRATask();
		jiraTaskObject.UpdateDataToJIRA(issueDataList);

		System.out.println("Completed updation of data into JIRA");
		Logger.WriteLog("Completed updation of data into JIRA");
	}

	private String[] GetFieldsToUpdate(String fields) {
		List<String> allFields = new ArrayList<String>();
		try {
			String actualFields = fields.replace("-fields:", "");
			// System.out.println("Modified fields :" + actualFields);
			String[] fieldsWithKeys = actualFields.trim().split(",");

			// To add keys by default.
			allFields.add(0, "Key"); // To get key from excel sheet.

			for (String string : fieldsWithKeys) {
				String[] field = string.split("#");
				// System.out.println("Actual field:" + field[0]);
				allFields.add(field[0]);
				// put excel field name as key and jira id as value into a Hashmap.

				field_JiraIDMap.put(field[0], field[1]);
			}
		} catch (Exception e) {
			Logger.WriteLog("Exception while parsing fields to update.Exception :" + e.getMessage());
			e.printStackTrace();
		}
		// System.out.println("Fields jira id length:" + field_JiraIDMap.values().size());
		return allFields.stream().toArray(String[]::new);

	}

}
