package com.vishnu.automation.JIRAReportsEmail.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {

	public static String GetCustomFieldValueFromJSON(String inputJSON, String key) {
		// System.out.println("In GetCustomFieldValueFromJSON - " + inputJSON + "with key "+key);
		// Logger.WriteLog("In GetCustomFieldValueFromJSON - " + inputJSON);

		String value = "";
		try {
			if (inputJSON == null) {
				Logger.WriteLog("In GetCustomFieldValueFromJSON - Input null");
				return value;
			}
			if (isJSONValid(inputJSON) && !isJSONArray(inputJSON)) {
				try {
					// System.out.println("input is "+inputJSON);
					JSONObject jsonObj = new JSONObject(inputJSON);
					value = jsonObj.get(key).toString();
					// System.out.println("Value is "+value);
				} catch (Exception e) {
					Logger.WriteLog("Exception while processing valid input json:" + inputJSON);
				}
			} else if (isJSONArray(inputJSON)) {
				value = GetValueFromJSONArray(inputJSON);
			} else {
				value = inputJSON;
			}
		} catch (Exception e) {
			System.out.println("Exception while getting value from String");
			Logger.WriteLog("Exception while getting value from String");
		}
		// System.out.println("Returning value as :"+value);
		return value;
	}

	private static boolean isJSONValid(String jsonString) {
		try {
			new JSONObject(jsonString);
		} catch (JSONException ex) {
			// edited, to include @Arthur's comment
			// e.g. in case JSONArray is valid as well...
			try {
				new JSONArray(jsonString);
			} catch (JSONException ex1) {
				return false;
			}
		}
		// System.out.println("Valid JSON");
		return true;
	}

	private static boolean isJSONArray(String jsonString) {
		// System.out.println("isJSONArray" + jsonString);
		Boolean returnValue = true;
		JSONArray arr = null;
		try {
			arr = new JSONArray(jsonString);
			// System.out.println("array : " + arr.length());
		} catch (JSONException ex) {
			// ex.printStackTrace();
			// System.out.println("JSON is not an array");
			returnValue = false;
		}
		// System.out.println("isJSONArray ?" + returnValue);
		return returnValue;
	}

	private static String GetValueFromJSONArray(String jsonString) {
		String returnValue = "";
		JSONArray arr = null;
		try {
			arr = new JSONArray(jsonString);
			// System.out.println("array : " + arr.length());
		} catch (JSONException ex) {
			// System.out.println("JSON is not an array");
		}

		String values = "";
		List<String> valuesList = new ArrayList<String>();
		for (int i = 0; i < arr.length(); i++) {
			// System.out.println("Values:"+values + " i ="+i);
			valuesList.add(arr.getJSONObject(i).getString("value"));
		}
		// values += values.substring(0, values.lastIndexOf(','));
		values = String.join(",", valuesList);

		returnValue = values;
		// System.out.println("Value from JSON Array:"+returnValue);
		return returnValue;
	}
}
