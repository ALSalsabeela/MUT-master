package com.example.androidprogramminghelper;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class RouteDetails {
	protected PolylineOptions lineOptions = null;
	protected String distance="",duration="";
	protected	LatLng latLang1=null,latLang2=null;
	
	public void setLatLong1(LatLng latLng1)
	{
		this.latLang1=latLng1;
	}
	
	public void setLatLong2(LatLng latLng2)
	{
		this.latLang2=latLng2;
	}
	
	public String getDistance()
	{
		return distance;
	}
	
	public String getDuration()
	{
		return duration;
	}
	
	public PolylineOptions getLineOptions()
	{
		return lineOptions ;
	}
	
	
}
