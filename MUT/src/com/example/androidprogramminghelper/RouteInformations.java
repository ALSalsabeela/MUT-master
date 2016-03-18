package com.example.androidprogramminghelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class RouteInformations extends AsyncTask<RouteDetails, Integer, RouteDetails >
{

	public interface AsyncResponse {
        void processFinish(RouteDetails output);
    }

	  public AsyncResponse delegate = null;
	  
	  public RouteInformations(AsyncResponse delegate){
	        this.delegate = delegate;
	    }
	   
    // Parsing the data in non-ui thread
    @Override
    public RouteDetails doInBackground(RouteDetails... latLong) {
    	RouteDetails routeDetails=new RouteDetails();
    	
    	
        List<List<HashMap<String, String>>> routes = null;

        try{
            // Starts parsing data
            routes = parse(latLong[0].latLang1.latitude,latLong[0].latLang1.longitude,latLong[0].latLang2.latitude,latLong[0].latLang2.longitude);
        }catch(Exception e){
            e.printStackTrace();
        }
    	
    	ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
       
        List<HashMap<String, String>> path = routes.get(0);
        HashMap<String,String> disDur = path.get(0);
    	// Traversing through all the routes
        for(int i=1;i<routes.size();i++){
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
             path = routes.get(i);

            // Fetching all the points in i-th route
            for(int j=0;j<path.size();j++){
                HashMap<String,String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
        		
            }
            // Adding all the points in the route to LineOptions
            
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.BLUE);
            routeDetails.lineOptions=lineOptions;
        }
        routeDetails.distance=disDur.get("distance");
        routeDetails.duration=disDur.get("duration");
        
        return routeDetails;
    }

    // Executes in UI thread, after the parsing process
    @Override
    public void  onPostExecute(RouteDetails result) {
        
    	delegate.processFinish(result);

        
        
        }
    
    private HashMap<String,String> parse(JSONArray jsonArray)
    {
 	   HashMap<String, String> disDur = new HashMap<String, String>();
    	
 	      try{
 	        JSONObject jsonObject,jsonTemp;
 	    	jsonObject = jsonArray.getJSONObject(0);
 	    	jsonArray  = jsonObject.getJSONArray("legs");
 	    	jsonObject = jsonArray.getJSONObject(0);
 	    	jsonTemp   = jsonObject.getJSONObject("distance");
 	    	disDur.put("distance", jsonTemp.getString("text"));
 	    	jsonTemp   = jsonObject.getJSONObject("duration");
 	    	disDur.put("duration", jsonTemp.getString("text"));
 	      }
 		  catch(Exception e)
 		  	    {e.printStackTrace();
 		  	    }  
 	      return disDur;
    }
    
    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    private List<List<HashMap<String,String>>> parse(double latitude, double longitude,double prelatitute, double prelongitude)
    {
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
 
        try {
 
            jRoutes = getRouteInformation(latitude, longitude, prelatitute, prelongitude);
            List<HashMap<String, String>> disDur = new ArrayList<HashMap<String, String>>(); 
            disDur.add(getDistanceAndTime(jRoutes));
            routes.add(disDur);
            
            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();
 
                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");
 
                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);
 
                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                routes.add(path);
            }
        }
           
 
    } catch (JSONException e) {
        e.printStackTrace();
    }catch (Exception e){
    }
        
    return routes;
    }
    
    /**
    * Method to decode polyline points
    * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
    * */
    
    
    private List<LatLng> decodePoly(String encoded) {
 
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
 
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
 
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
 
            LatLng p = new LatLng((((double) lat / 1E5)),
                (((double) lng / 1E5)));
            poly.add(p);
        }
 
        return poly;
    }
    
    HashMap<String, String> getDistanceAndTime(double latitude, double longitude,double prelatitute, double prelongitude)
    {
    	JSONArray jsonArray=null;
	      try{
	    	jsonArray = getRouteInformation(latitude, longitude, prelatitute, prelongitude);
	       }
		  catch(Exception e)
		  	    {e.printStackTrace();
		  	    }  
	      return parse(jsonArray);
		}
   
   public HashMap<String, String> getDistanceAndTime(JSONArray jsonArray )
    {
	      return parse(jsonArray);
	}
   
  
    private  JSONArray getRouteInformation(double latitude, double longitude,double prelatitute, double prelongitude) 
    {
  		
  		JSONArray jsonArray =null;
  		 String url = "http://maps.google.com/maps/api/directions/json?origin="
  		            + latitude + "," + longitude + "&destination=" + prelatitute
  		            + "," + prelongitude + "&mode=driving&sensor=false&units=metric";
  		 
  		
  		 String jsonOutput="";
		try {
			jsonOutput = downloadUrl(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
  		 
  		 try{
  			JSONObject jsonObject = new JSONObject(jsonOutput);
  	    	 jsonArray = jsonObject.getJSONArray("routes");
  	      }
  		  catch(Exception e)
  		  	    {e.printStackTrace();
  		  	    }  
  	      return jsonArray;
  		}
    

    
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}

