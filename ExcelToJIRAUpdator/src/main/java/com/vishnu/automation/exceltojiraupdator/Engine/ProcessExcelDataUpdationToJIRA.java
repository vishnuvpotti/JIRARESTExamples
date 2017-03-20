package com.vishnu.automation.exceltojiraupdator.Engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.vishnu.automation.exceltojiraupdator.Core.ProcessDataUpdation;
import com.vishnu.automation.exceltojiraupdator.Core.ReadExcelData;
import com.vishnu.automation.exceltojiraupdator.Utilities.Logger;

public class ProcessExcelDataUpdationToJIRA {

	public static void main(String[] args) {

		String inputExcelPath = "";
		String fieldsList = "";
		Instant starts = Instant.now();
		Instant ends = null;
		System.out.println("Begin updating values from Excel to JIRA");
		Logger.WriteLog("Begin updating values from Excel to JIRA");
		// First read command arguments
		if (args.length == 1 && args[0].equalsIgnoreCase("/?")) {
			System.out.println(
					"Example usage : java -jar exceltojiraupdator.jar [excelpath] [-fields:fieldname1#jiraIDofFieldname1,fieldname2#jiraIDofFieldname2]\n"
							+ "eg - java -jar exceltojiraupdator.jar \"H:\\testdata\" \"Chess number\"#custom_100909,CHESSMSG#custom_10933");
		} else if (args.length < 2) {
			System.out.println("Invalid options passed. Run jar file with /? option to view example usage.");
			Logger.WriteLog("Invalid options passed.  Run jar file with /? option to view example usage.");
		} else if (args.length == 2) {
			File inputFile = new File(args[0]);
			if (inputFile.exists()) {
				if (args[1].contains("-fields")) {
					inputExcelPath = args[0];
					fieldsList = args[1];
					System.out.println("Input excel :" + inputExcelPath);
					System.out.println("Input fields :" + fieldsList);
					Logger.WriteLog("Input excel :" + inputExcelPath);
					Logger.WriteLog("Input fields :" + fieldsList);
					// Read Excel

					ProcessDataUpdation pd = new ProcessDataUpdation();
					pd.ProcessDataUpload(args[1], inputExcelPath);
				} else {
					System.out.println("Fields information not passed correctly" + args[1]);
					Logger.WriteLog("Fields information not passed correctly" + args[1]);
				}
			} else {
				System.out.println("No input sheet found at location" + args[0]);
				Logger.WriteLog("No input sheet found at location" + args[0]);
			}
		} else {
			System.out.println("Invalid options passed. Run jar file with /? option to view example usage.");
			Logger.WriteLog("Invalid options passed.  Run jar file with /? option to view example usage.");
		}

		ends = Instant.now();
		System.out.println("Total duration is :" + (Duration.between(starts, ends)));
		Logger.WriteLog("Total duration is :" + (Duration.between(starts, ends)));
	}

}
