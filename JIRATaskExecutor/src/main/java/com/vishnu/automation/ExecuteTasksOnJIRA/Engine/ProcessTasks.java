package com.vishnu.automation.ExecuteTasksOnJIRA.Engine;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

import com.vishnu.automation.ExecuteTasksOnJIRA.Core.ParseAndProcessTasks;
import com.vishnu.automation.ExecuteTasksOnJIRA.Utilities.Logger;

public class ProcessTasks {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println("Args.length:" + args.length);

		Instant starts = Instant.now();
		Instant ends = null;

		if (args.length == 1) {
			File xmlConfigFile = new File(args[0]);
			if (xmlConfigFile.exists()) {
				Logger.WriteLog("Start processing JIRA task for input file :." + xmlConfigFile);
				System.out.println("Start processing JIRA task for input file :." + xmlConfigFile);
				ParseAndProcessTasks generateEmailObject = new ParseAndProcessTasks(args[0]);
				generateEmailObject.ProcessTasks();
				Logger.WriteLog("Completed processing JIRA tasks");
				System.out.println("Completed processing JIRA tasks");
			} else {
				System.out.println("No file exists at the location provided.Please re-check");
				Logger.WriteLog("No file exists at the location provided.Please re-check");
			}
		} else if (args.length == 2) {
			File xmlConfigFile = new File(args[0]);
			if (xmlConfigFile.exists()) {
				Logger.WriteLog("Start processing JIRA task for input file :." + xmlConfigFile + " and report :" + args[1]);
				System.out.println("Start processing JIRA task for input file :." + xmlConfigFile + " and report :" + args[1]);
				ParseAndProcessTasks generateEmailObject = new ParseAndProcessTasks(args[0], args[1]);
				generateEmailObject.ProcessTasks();
				Logger.WriteLog("Completed processing JIRA tasks");
				System.out.println("Completed processing JIRA tasks");
			}
		} else {
			System.out.println(
					"Invalid options passed.\nExample usage: jirataskexecutor.jar 'path of config xml OR \n jirataskexecutor.jar 'path of config xml' 'taskName'");
			Logger.WriteLog(
					"Invalid options passed.\nExample usage: jirataskexecutor.jar 'path of config xml OR \n jirataskexecutor.jar 'path of config xml' 'taskName'");
		}
		ends = Instant.now();
		System.out.println("Total execution time is :" + (Duration.between(starts, ends)));
		Logger.WriteLog("Total execution time is :" + (Duration.between(starts, ends)));
        System.exit(0);
	}

}
