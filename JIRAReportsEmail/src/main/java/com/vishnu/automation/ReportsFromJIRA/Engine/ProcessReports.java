package com.vishnu.automation.ReportsFromJIRA.Engine;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

import com.vishnu.automation.ReportsFromJIRA.Core.GenerateReportsAndEmail;
import com.vishnu.automation.ReportsFromJIRA.Utilities.Logger;

public class ProcessReports {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println("Args.length:" + args.length);

		Instant starts = Instant.now();
		Instant ends = null;

		if (args.length == 1) {
			File xmlConfigFile = new File(args[0]);
			if (xmlConfigFile.exists()) {
				Logger.WriteLog("Start processing Reporting task for input file :." + xmlConfigFile);
				System.out.println("Start processing Reporting task for input file :." + xmlConfigFile);
				GenerateReportsAndEmail generateEmailObject = new GenerateReportsAndEmail(args[0]);
				generateEmailObject.ProcessTasks();
				Logger.WriteLog("Completed processing reports");
				System.out.println("Completed processing reports");
			} else {
				System.out.println("No file exists at the location provided.Please re-check");
				Logger.WriteLog("No file exists at the location provided.Please re-check");
			}
		} else if (args.length == 2) {
			File xmlConfigFile = new File(args[0]);
			if (xmlConfigFile.exists()) {
				Logger.WriteLog("Start processing Reporting task for input file :." + xmlConfigFile + " and report :" + args[1]);
				System.out.println("Start processing Reporting task for input file :." + xmlConfigFile + " and report :" + args[1]);
				GenerateReportsAndEmail generateEmailObject = new GenerateReportsAndEmail(args[0], args[1]);
				generateEmailObject.ProcessTasks();
				Logger.WriteLog("Completed processing reporting task");
				System.out.println("Completed processing reporting task");
			}
		} else {
			System.out.println(
					"Invalid options passed.\nExample usage: jirareports.jar 'path of config xml OR \n jirareports.jar 'path of config xml' 'reportName'");
			Logger.WriteLog(
					"Invalid options passed.\nExample usage: jirareports.jar 'path of config xml OR \n jirareports.jar 'path of config xml' 'reportName'");
		}
		ends = Instant.now();
		System.out.println("Total execution time is :" + (Duration.between(starts, ends)));
		Logger.WriteLog("Total execution time is :" + (Duration.between(starts, ends)));
		System.exit(0);

	}

}
