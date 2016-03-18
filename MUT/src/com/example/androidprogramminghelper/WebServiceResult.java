package com.example.androidprogramminghelper;

import org.json.JSONArray;

public class WebServiceResult 
{
	
	private String string="",connectionResult="",jsonString="";
	private JSONArray jsonArray=null;
	
	protected void setJsonString(String jsonString) {
		this.jsonString= jsonString;
	}

	protected void setConnectionResult(String connectionResult) {
		this.connectionResult= connectionResult;
	}
	
	
	
	protected void setString(String string) {
		this.string = string;
	}
	
	protected void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray=jsonArray;
	}
	
	public String getJsonString() {
		return jsonString;
	}
	
	public String getConnectionResult() {
		return connectionResult;
	}
	
	public JSONArray getJson()
	{
		return jsonArray;
	}
	
	public String getString() {
		return string;
	}

}
