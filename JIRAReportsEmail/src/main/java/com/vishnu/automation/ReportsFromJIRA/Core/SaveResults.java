package com.vishnu.automation.ReportsFromJIRA.Core;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vishnu.automation.ReportsFromJIRA.Dataclass.IssueData;
import com.vishnu.automation.ReportsFromJIRA.Utilities.Logger;

public class SaveResults {

	public String fileType = "";

	public SaveResults(String filetype) {
		// TODO Auto-generated method stub
		this.fileType = filetype;
	}

	/**
	 * Function to save the file in desired format
	 * 
	 * @param headerList
	 *            : Send the headerlist as String separated by comas.
	 * @param recordsList
	 *            : Send the list of Issuedata objects.
	 * @param fileName
	 *            : Send the filename for saving file.
	 */
	public void SaveFile(String headerList, List<IssueData> recordsList, String fileName) {

		switch (fileType) {
		case "xls":
		case "xlsx":
			Logger.WriteLog("Create xlsx for saving results.");
			WriteToExcelFile(headerList, recordsList, fileName);
			break;
		case "doc":
			System.out.println("Not yet implemented");
			Logger.WriteLog("Outputting to doc format not yet implemented");
			break;
		case "inline":
			System.out.println("Create HTML for saving results into variable");
			Logger.WriteLog("Create HTML for sending results into variable");
			EmailTasks.htmlData = SaveResultsAsHTML(headerList, recordsList, fileName);
			break;
		default:
			System.out.println("Invalid format");
			Logger.WriteLog("Invalid format");
			break;
		}
	}

	private void WriteToExcelFile(String headerList, List<IssueData> recordsList, String fileName) {
		String filePath = fileName + "." + fileType;
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(fileName);

		int rowCount = 0;
		Row row = sheet.createRow(rowCount);

		CreateHeaders(workbook, row, headerList);

		for (IssueData issue : recordsList) {
			row = sheet.createRow(++rowCount);
			//System.out.println("Enter data on row" + row.getRowNum() + " for key " + issue.getDataList().get(0).get("Key"));
			WriteRowsToExcel(row, issue);
		}

		autoSizeColumns(workbook, sheet);

		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void CreateHeaders(Workbook wb, Row row, String headers) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBold(true);
		style.setFont(font);
		String[] headersArray = headers.split(",");
		Cell cell = null;
		int i = -1;
		for (String header : headersArray) {
			cell = row.createCell(++i);
			cell.setCellValue(header);
			cell.setCellStyle(style);

		}

	}

	private void autoSizeColumns(Workbook workbook, XSSFSheet sheet) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			// Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Row row = sheet.getRow(0);
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}

	private void WriteRowsToExcel(Row row, IssueData issue) {
		int i = -1;
		for (Map<String, String> issuedatamap : issue.getDataList()) {

			Cell cell = null;
			for (Map.Entry<String, String> e : issuedatamap.entrySet()) {
				cell = row.createCell(++i);
				Logger.WriteLog("Writing Key: " + e.getKey() + "; Value: " + e.getValue() + " on cell number :" + i);
				cell.setCellValue(e.getValue());

			}
		}
	}

	private String SaveResultsAsHTML(String headerList, List<IssueData> recordsList, String reportName) {
		StringBuilder htmlSB = new StringBuilder();
		htmlSB.append("<html><head>Report from JIRA</head><title>Report from JIRA</title><body><table border=4><caption><h2>" + reportName
				+ "</h2><h4> Total Issues - " + recordsList.size() + "</h4></caption>");
		htmlSB.append(CreateHTMLHeader(headerList));
		for (IssueData issueData : recordsList) {
			htmlSB.append(WriteDataAsHTML(issueData));
		}
		htmlSB.append("</table></body></html>");

		// Logger.WriteLog("******************HTML *******************");
		// Logger.WriteLog(htmlSB.toString());
		// Logger.WriteLog("******************HTML *******************");
		return htmlSB.toString();
	}

	private String CreateHTMLHeader(String headers) {
		StringBuilder headerSB = new StringBuilder();
		try {
			String[] headersArray = headers.split(",");
			headerSB.append("<tr>");
			for (String header : headersArray) {
				headerSB.append("<th>" + header + "</th>");
			}
			headerSB.append("</tr>");
			//System.out.println("header is:" + headerSB.toString());
		} catch (Exception e) {
		}
		return headerSB.toString();
	}

	private String WriteDataAsHTML(IssueData issue) {
		// System.out.println("WriteDataasHTML for :" + issue.getDataList().get(0).get("Key"));

		StringBuilder htmlDataSB = new StringBuilder();
		try {
			int i = 1;
			htmlDataSB.append("<tr>");
			for (Map<String, String> issuedatamap : issue.getDataList()) {
				for (Map.Entry<String, String> e : issuedatamap.entrySet()) {
					Logger.WriteLog("Writing Key: " + e.getKey() + "; Value: " + e.getValue() + " on cell number :" + i);
					htmlDataSB.append("<td>" + e.getValue() + "</td>");
					i++;
				}
			}
			htmlDataSB.append("</tr>");
		} catch (Exception e) {
		}
		return htmlDataSB.toString();
	}

}
