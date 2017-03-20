package com.vishnu.automation.exceltojiraupdator.DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueData {

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	private List<Map<String, String>> fieldValueList = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getFieldValueList() {
		return fieldValueList;
	}

	public void setFieldValueList(List<Map<String, String>> fieldValueList) {
		this.fieldValueList = fieldValueList;
	}
	
	
}
