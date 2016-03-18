package com.example.mosusedtools;

import com.example.androidprogramminghelper.MUT;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabMap extends  Fragment {

	
	GoogleMap map;

	
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 	
		
		if (rootView != null) {
	        ViewGroup parent = (ViewGroup) rootView.getParent();
	        if (parent != null)
	            parent.removeView(rootView);
	    }
		
		try
				 {
				rootView = inflater.inflate(R.layout.activity_tab_map, container, false);
			
				 }
				 catch(Exception e)
				 {
					 
					 e.printStackTrace();
			     }	    
		setUpMapIfNeeded();
		
		return rootView;
	}
	

	@Override
	public void onDestroyView() 
	{	
		try
		{
			 
		Fragment fragment = (getActivity().getSupportFragmentManager().findFragmentById(R.id.tabmap));  
		if(fragment!= null)
			getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
	    map.clear();
		map=null;
	    
	   
		}
		catch(Exception e){}
	 super.onDestroyView();
	}
	
	
	
	


	private GoogleMap.OnMarkerClickListener onMarkerClickedListener = new GoogleMap.OnMarkerClickListener() {
	    @Override
		public boolean onMarkerClick(Marker marker)
	   {
	    	MUT.lToast(getActivity(),"Marker "+marker.getTitle()+" Pressed");
	        return true;
		}
	};
	
	

	Marker marker=null;

		private void setUpMapIfNeeded() {
		
			if (map == null) {
				map = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.tabmap))
                .getMap() ;
				map.setMyLocationEnabled(true);
	            map.setOnMarkerClickListener(onMarkerClickedListener);
	            if (map != null) {
	            	map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
	           @Override
	           public void onMyLocationChange(Location location) { 
	        	  
	        	   if(marker!=null)marker.remove();
	        	   
	        	   marker= map.addMarker(new MarkerOptions()
	   		    .position( new LatLng(location.getLatitude(),location.getLongitude()) )
			    .title("MainMarker"));
	               CameraPosition INIT =
	        	            new CameraPosition.Builder()
	        	            .target(new LatLng(location.getLatitude(),location.getLongitude()))
	        	            .zoom(17.5F)
	        	            .build(); 
	        	            // use map to move camera into position
	        	            map.animateCamera( CameraUpdateFactory.newCameraPosition(INIT) );
	        	   
	           }
	        
	           
	          });
	            	
	            }
	        }
	        
		}
		





		
	
		
}