package com.vishnu.automation.ExecuteTasksOnJIRA.Utilities;

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
	public static final String logFilename = "ExeuteTasksOnJIRALog_" + currentTime + ".txt";
	
	public static Boolean logFileCreated = false;
	public static File file = null;
	
	public static void WriteLog(String log) {

		try {

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			if (!logFileCreated) {
				// logString.append(dateFormat.format(date) + ":" + log + "\n");
				String path = GetCurrentPath();
				//System.out.println("path:" + path);

				File logDir = new File(path + "\\Logs");
				file = new File(Paths.get(logDir.toString(), logFilename).toString());
				System.out.println("Log File path:" + file.getPath());

				if (!logDir.isDirectory()) {
					logDir.mkdir();
				}

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
					logFileCreated = true;
				}
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

	private static String[] removeAt(String[] arr, int k) {
		final int L = arr.length;
		String[] ret = new String[L - 1];
		System.arraycopy(arr, 0, ret, 0, k);
		System.arraycopy(arr, k + 1, ret, k, L - k - 1);
		return ret;
	}

	public static String GetCurrentPath() {
		String actualExecutionPath = "";

		try {
			URL location = Logger.class.getProtectionDomain().getCodeSource().getLocation();
			System.out.println("CurrentPath is :" + location.getPath());
			String path = location.getPath().replaceFirst("/", "");

			String[] pathSplit = path.split("/");
			if (pathSplit[pathSplit.length - 1].contains(".jar")) {
				//System.out.println("Path contains jar");
				pathSplit = removeAt(pathSplit, pathSplit.length - 1);
				actualExecutionPath = String.join("\\", pathSplit);
				//System.out.println("new path :" + actualExecutionPath);
			} else {
				actualExecutionPath = path.replace("/", "\\");
				//System.out.println("new path :" + actualExecutionPath);
			}
			//System.out.println("actualExecutionPath to be returned is:" + actualExecutionPath);
		} catch (Exception e) {
			String pathToReturn = Logger.class.getProtectionDomain().getCodeSource().getLocation().toString();
			//System.out.println("Path to be returned is:" + pathToReturn);
			return pathToReturn;
		}
		return actualExecutionPath;
	}
}
