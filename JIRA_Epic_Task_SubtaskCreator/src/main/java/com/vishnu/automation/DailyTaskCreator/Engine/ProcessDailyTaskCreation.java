package com.vishnu.automation.DailyTaskCreator.Engine;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

import com.vishnu.automation.DailyTaskCreator.Core.DailyTaskCreator;
import com.vishnu.automation.DailyTaskCreator.Utilities.Logger;

public class ProcessDailyTaskCreation {

	public static void main(String[] args) {

		Instant starts = Instant.now();
		Instant ends = null;
		if (args.length == 1) {
			File xmlConfigFile = new File(args[0]);
			if (xmlConfigFile.exists()) {
				Logger.WriteLog("Start processing daily task creation for input file :."+xmlConfigFile);
				DailyTaskCreator dailytaskCreatorObject = new DailyTaskCreator(args[0]);
				dailytaskCreatorObject.ProcessDailyTaskCreation();
				Logger.WriteLog("Completed processing daily task creation.");
			} else {
				System.out.println("No file exists at the location provided.Please re-check");
				Logger.WriteLog("No file exists at the location provided.Please re-check");
			}
		} else {
			System.out.println("Invalid options passed.  Example usage -  dailytaskcreator.jar 'path of config xml' ");
			Logger.WriteLog("Invalid options passed.  Example usage -  dailytaskcreator.jar 'path of config xml' ");
		}

		ends = Instant.now();
		System.out.println("Total duration is :" + (Duration.between(starts, ends)));
		Logger.WriteLog("Total duration is :" + (Duration.between(starts, ends)));
	}
}
