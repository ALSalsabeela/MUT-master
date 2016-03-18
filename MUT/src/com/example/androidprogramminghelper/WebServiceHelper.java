package com.example.androidprogramminghelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;

public class WebServiceHelper  extends AsyncTask<JSONObject,Integer,WebServiceResult>
{
	public static String defaultUrl="";
	
	public interface ServieceAsyncResponse {
        void processFinish(WebServiceResult output);
    }

	  public ServieceAsyncResponse delegate = null;
	  
	  public WebServiceHelper(ServieceAsyncResponse delegate){
	        this.delegate = delegate;
	    }

	  @Override
		protected WebServiceResult doInBackground(JSONObject... params) 
	    {
			return connectServer(defaultUrl,params[0]);
		}
	  
	
	  @Override
	    public void  onPostExecute(WebServiceResult result) {
	        
	    	delegate.processFinish(result);

	        
	        
	        }

	 protected WebServiceResult connectServer(String url ,JSONObject sentData)
	 {
		 
		 WebServiceResult result=new WebServiceResult();
		 StringBuilder json = null,string=null;
			try {
				URL object=new URL(url);
				HttpURLConnection con = (HttpURLConnection) object.openConnection();
				con.setDoOutput(true);
				con.setDoInput(true);
				con.setChunkedStreamingMode(1024);
				con.setRequestProperty("Accept", "application/json");
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				if(sentData!=null)
				{
				OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
				wr.write(sentData.toString());
				wr.flush();
				}
				con.connect();
				json = new StringBuilder();  
				string =new StringBuilder();
				int httpResult = con.getResponseCode(); 
				if(httpResult == HttpURLConnection.HTTP_OK)
				{
				    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));  
				    String line = null;  
				    while ((line = br.readLine()) != null) 
				    { 
				        json.append(line+"\n");
				        string.append(line);
				    }  
				    br.close();
				    
				    
				    try
				    {
				    	
				  result.setJsonArray(new JSONArray(json.substring(1, json.toString().length()-1)));
				 
				    }
				    catch(Exception e){
				    	e.printStackTrace();
				    	
				    }
				  result.setString(string.substring(2, string.toString().length()));
				  result.setConnectionResult("HTTP_OK");
				}
				else
				{
					result.setConnectionResult("Connection_Error");
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		    return result;
	 }
	 
	
}
