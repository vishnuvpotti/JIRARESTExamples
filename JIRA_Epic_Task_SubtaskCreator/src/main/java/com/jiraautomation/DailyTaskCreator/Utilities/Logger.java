package com.jiraautomation.DailyTaskCreator.Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Logger {
	
	static String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	public static final String logFilename = "CTPProcessingLog_" + currentTime + ".txt";
	
	
	public static void WriteLog(String log)  {

		try {

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			// logString.append(dateFormat.format(date) + ":" + log + "\n");
			URL location = Logger.class.getProtectionDomain().getCodeSource().getLocation();
			String path = location.getPath().replaceFirst("/", "");
			File logDir = new File(path + "\\Logs");
			File file = new File(Paths.get(logDir.toString(), logFilename).toString());
			// System.out.println("path is " + Paths.get(path,
			// logFilename).toString());

			
			if (!logDir.isDirectory())
			{
              logDir.mkdir();
			}
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dateFormat.format(date) + ":" + log);
			bw.newLine();
			bw.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
