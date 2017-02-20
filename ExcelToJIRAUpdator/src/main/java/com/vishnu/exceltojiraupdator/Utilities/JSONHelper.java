package com.vishnu.exceltojiraupdator.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {

	
	/** Function to get custom field value from JSON.
	 * @param inputJSON : Send the input JSON
	 * @param key : Send the key to be looked for extraction from JSON.
	 * @return extracted value as String
	 */
	public static String GetCustomFieldValueFromJSON(String inputJSON, String key) {
		// System.out.println("In GetCustomFieldValueFromJSON - " + inputJSON);
		// Logger.WriteLog("In GetCustomFieldValueFromJSON - " + inputJSON);

		String value = "";
		try {
			if (inputJSON == null) {
				Logger.WriteLog("In GetCustomFieldValueFromJSON - Input null");
				return value;
			}
			if (isJSONValid(inputJSON)) {
				try {
					// System.out.println("input is "+inputJSON);

					JSONObject jsonObj = new JSONObject(inputJSON);
					value = jsonObj.get(key).toString();
					// System.out.println("Value is "+value);

				} catch (Exception e) {
					Logger.WriteLog("Exception while processing input json:" + inputJSON);
				}
			} else {
				value = inputJSON;
			}
		} catch (Exception e) {
			System.out.println("Exception while getting value from String");
			Logger.WriteLog("Exception while getting value from String");
		}
		return value;
	}

	
	/** Function to check whether JSON is valid or not.
	 * @param jsonString : Send the JSON String to be verified.
	 * @return true, if JSON is valid. else false.
	 */
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
		return true;
	}

}
