package com.vishnu.automation.exceltojiraupdator.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.vishnu.automation.exceltojiraupdator.DataClass.IssueData;
import com.vishnu.automation.exceltojiraupdator.Utilities.Logger;

import org.apache.poi.ss.usermodel.Row;

public class ReadExcelData {

	public int GetCellIndexOfColumn(String field, Sheet sheet) {
		int columnNumber = 0;
		int totalRows = sheet.getPhysicalNumberOfRows();
		if (totalRows > 0) {
			int noOfColumns = sheet.getRow(0).getLastCellNum();
			Row row = sheet.getRow(0);
			for (int i = 0; i < noOfColumns; i++) {
				// System.out.println("Column name" + row.getCell(i));
				if (field.equalsIgnoreCase(row.getCell(i).getStringCellValue())) {
					columnNumber = i;
					break;
				}
			}
		}
		return columnNumber;
	}

	public List<IssueData> GetFieldsDataFromExcel(String[] Fields, HashMap<String, String> field_JiraIDMap, String excelSheet) {

		List<IssueData> issueDataList = new ArrayList<IssueData>();
		HashMap<String, Integer> fieldsMap = new HashMap<String, Integer>(); // To store field column number pairs.

		Workbook workBook = null;
		try {
			workBook = WorkbookFactory.create(new FileInputStream(new File(excelSheet)));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet = workBook.getSheetAt(0);

		// Get field column mapping.
		fieldsMap = GetFieldColumnMappingFromExcel(Fields, sheet);

		issueDataList = GetAllDataFromExcel(fieldsMap, field_JiraIDMap, sheet);
		return issueDataList;

	}

	private HashMap<String, Integer> GetFieldColumnMappingFromExcel(String[] Fields, Sheet sheet) {
		HashMap<String, Integer> fieldsMap = new HashMap<String, Integer>();

		for (String field : Fields) {
			// System.out.println("Process field : "+field );
			int columNumber = GetCellIndexOfColumn(field, sheet);
			System.out.println("Column Number for field : " + columNumber);
			fieldsMap.put(field, columNumber);
		}

		return fieldsMap;
	}

	private List<IssueData> GetAllDataFromExcel(HashMap<String, Integer> fieldsMap, HashMap<String, String> field_JiraIDMap, Sheet sheet) {

		List<IssueData> issueDataList = new ArrayList<IssueData>();

		int totalRows = sheet.getPhysicalNumberOfRows();

		if (totalRows > 0) {
			for (int row = 1; row < totalRows; row++) {
				Row rowData = sheet.getRow(row);
				IssueData issueDataObject = new IssueData();
				List<Map<String, String>> jiraID_ValueMapList = new ArrayList<Map<String, String>>();
				String keyValue = rowData.getCell(fieldsMap.get("Key")).getStringCellValue();
				issueDataObject.setKey(keyValue);
				// System.out.println("Key value : " + keyValue);
				issueDataList.add(issueDataObject);

				for (Entry<String, String> e : field_JiraIDMap.entrySet()) {
					try {
						// Logger.WriteLog("Getting Key: " + e.getKey() + "; Value: " + e.getValue());
						// System.out.println("Getting Key: " + e.getKey() + "; Value: " + e.getValue());
						String keyValueforField = rowData.getCell(fieldsMap.get(e.getKey())).getStringCellValue();
						System.out.println("Value for Key: " + e.getValue() + "; Value: " + keyValueforField);
						if (!keyValueforField.isEmpty()) {
							Map<String, String> jiraID_valueMap = new HashMap<String, String>();
							jiraID_valueMap.put(e.getValue(), keyValueforField);

							jiraID_ValueMapList.add(jiraID_valueMap);
						}
					} catch (Exception ex) {
						//ex.printStackTrace();
						Logger.WriteLog("Exception while parsing data" + ex.getMessage());
					}
				}
				issueDataObject.setFieldValueList(jiraID_ValueMapList);
				System.out.println("total field length in map" + jiraID_ValueMapList.size());
			}

		}
		return issueDataList;
	}

}
