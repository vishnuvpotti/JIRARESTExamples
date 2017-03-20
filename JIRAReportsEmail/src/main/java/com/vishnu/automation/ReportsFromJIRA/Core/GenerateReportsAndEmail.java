package com.vishnu.automation.ReportsFromJIRA.Core;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.vishnu.automation.ReportsFromJIRA.Dataclass.EmailReports;
import com.vishnu.automation.ReportsFromJIRA.Dataclass.Report;
import com.vishnu.automation.ReportsFromJIRA.Utilities.Logger;

/**
 * Hello world!
 */
public class GenerateReportsAndEmail {
	public static String filePath = "";
	public static String reportToGenerate = "";

	public GenerateReportsAndEmail(String file) {
		filePath = file;
	}

	public GenerateReportsAndEmail(String file, String reportName) {
		filePath = file;
		reportToGenerate = reportName;
	}

	public void ProcessTasks() {
		JAXBContext jc = null;
		Unmarshaller unmarshaller = null;
		EmailReports emailReports = null;

		try {
			jc = JAXBContext.newInstance(EmailReports.class);
			unmarshaller = jc.createUnmarshaller();
			System.out.println("Filepath:" + filePath);
			emailReports = (EmailReports) unmarshaller.unmarshal(new File(filePath));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.WriteLog("Exception while parsing xml. Please check detailed exception info." + e.getMessage());
			System.out.println("Exception while parsing xml. Please check detailed exception info." + e.getMessage());
			Logger.WriteLog("Exception stacktrace:" + e.getStackTrace());
		}

		try {
			System.out.println("Get Reports Count:" + emailReports.getReports().size());
			Logger.WriteLog("Get total Reports Count:" + emailReports.getReports().size());

			for (Report reports : emailReports.getReports()) {
				EmailTasks.htmlData = "";
				Logger.WriteLog("Start Processing Report :" + reports.getReportName());
				System.out.println("Start Processing Report : " + reports.getReportName());
				System.out.println("JIRA JQL for Report  is: " + reports.getJiraObject().getJql());
				Logger.WriteLog("JIRA JQL for Report is: " + reports.getJiraObject().getJql());
				if (!reportToGenerate.isEmpty()) {
					if (reports.getReportName().equalsIgnoreCase(reportToGenerate)) {
						ProcessReports(reports);
					}
				} else {
					// Generate all reports
					ProcessReports(reports);
				}
			}
		} catch (Exception e) {
			Logger.WriteLog("Exception occured while processing Report : " + e.getMessage());
			Logger.WriteLog("Exception stacktrace : " + e.getStackTrace());
			System.out.println("Exception occured while processing Report : " + e.getMessage());
			System.out.println("Exception stacktrace : " + e.getStackTrace());
		}
	}

	private void ProcessReports(Report report) {
		// Perform JIRA Operation
		Logger.WriteLog("Process JIRA tasks for report :" + report.getReportName());
		JIRATasks jiraTaskObject = new JIRATasks();
		try {
			jiraTaskObject.ProcessJIRATasks(report.getJiraObject());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (JIRATasks.totalRecordsCountFromSearch > 0 && report.getEmailObject() != null) {
			Logger.WriteLog("Process Email tasks for report :" + report.getReportName());
			// Perform Email operation
			EmailTasks emailTaskObject = new EmailTasks();
			emailTaskObject.ProcessEmailReports(report.getEmailObject());
		} else {
			Logger.WriteLog("Not processing Email task because No results from this JQL or Email task is not defined for this report.");
			System.out.println("Not processing Email task because No results from this JQL or Email task is not defined for this report.");
		}
	}

}
