package com.example.mosusedtools;
import com.example.androidprogramminghelper.Check;
import com.example.androidprogramminghelper.RouteInformations;
import com.example.androidprogramminghelper.RouteInformations.AsyncResponse;
import com.example.androidprogramminghelper.RouteDetails;
import com.example.androidprogramminghelper.MUT;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import android.support.v4.app.FragmentActivity;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class Map  extends FragmentActivity implements OnMapLongClickListener,OnMapClickListener,OnMarkerDragListener, OnMyLocationButtonClickListener, OnMyLocationChangeListener {
	GoogleMap map;
	Marker myLocMarker=null;
    Double lat=0.0,lng=0.0;
    
	Boolean addLine=false,mainMarker=false;
	Marker from=null,to=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);
		if(!Check.isLocationEnabled(this))
	     MUT.settingsDialog(this,android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS,"Enable Location Please","You want go enable it ?");
		InitializeMap();
		
	}
	

	private void InitializeMap() {
		if (map == null) 
		{
			map  = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map))
			           .getMap() ;
			map.setMyLocationEnabled(true);
			map.setOnMarkerDragListener(this);
            map.setOnMapLongClickListener(this);
            map.setOnMapClickListener(this);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationChangeListener(this);
            
		   
		    
        }  
	}
	
	
	public void moveCamera(View view)
	{
		CameraPosition INIT =
	            new CameraPosition.Builder()
	            .target(new LatLng(lat,lng))
	            .zoom(17.5F)
	            .bearing(300F) // orientation
	            .tilt( 50F) // viewing angle
	            .build(); 
	            // use map to move camera into position
	            map.animateCamera( CameraUpdateFactory.newCameraPosition(INIT) );
	}

	public void drawLine(View view)
	{
		addLine=true;
		flag=true;
		if(from!=null)from.remove();
		if(to!=null)to.remove();
		MUT.fastDialog(this,"Draw PolyLine","Add two Markers by long click on map 2 times ");
	}
	
	@SuppressLint("InflateParams")
	public void customMarker(View view)
	{
		 View markerView = getLayoutInflater().inflate(R.layout.custom_marker, null);
		MarkerOptions moptions=new MarkerOptions();
 	   moptions.position(new LatLng(lat,lng)).title("Iam Here!").icon(BitmapDescriptorFactory.fromBitmap(MUT.createDrawableFromView(this, markerView))).getPosition();
       map.addMarker(moptions);
       CameraPosition INIT =
	            new CameraPosition.Builder()
	            .target(new LatLng(lat,lng))
	            .zoom(17.5F)
	            .build(); 
	            // use map to move camera into position
	            map.animateCamera( CameraUpdateFactory.newCameraPosition(INIT) );
 	   
	            
	            
	            
 	   map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
	            //Use default infoWindow frame
	            @Override
	            public View getInfoWindow(Marker marker) {
	                return null;
	            }

	            @Override
	            public View getInfoContents(Marker marker) {
	              //here you can get what you need from the marker 
	            	
	            	
	            	 View sView = getLayoutInflater().inflate(R.layout.custom_snip, null);
	              
	                TextView text=( TextView)sView.findViewById(R.id.data);
	                text.setText("Tefa Alhwa");
	                

	                return sView;
	            }
	        });
		
		
	}
	
	 @Override
	    public void onMarkerDrag(Marker arg0) {
	        // TODO Auto-generated method stub
	         
	    }
	 
	    @Override
	    public void onMarkerDragEnd(Marker arg0) {
	        // TODO Auto-generated method stub
	        LatLng dragPosition = arg0.getPosition();
	        
	        double dragLat = dragPosition.latitude;
	        double dragLong = dragPosition.longitude;
	        MUT.fastDialog(this,"Latitude : "+dragLat+"\nLongitude : "+dragLong,"Drag End");
	    }
	 
	    @Override
	    public void onMarkerDragStart(Marker arg0) 
	    {
	        // TODO Auto-generated method stub
	         
	    }
	 
	    @Override
	    public void onMapClick(LatLng arg0) {
	        // TODO Auto-generated method stub
	    	MUT.lToast(this,"Map Clicked");
	        
	    }
	 
	 Boolean flag=true;
	    @Override
	    public void onMapLongClick(LatLng arg0) {
	        // TODO Auto-generated method stub
	    	
	     
         if(addLine)
         {
        	 if(flag)
        	 {
        	 from=addMarker(arg0,"From Marker","From Marker",BitmapDescriptorFactory.HUE_GREEN);
        	 flag=false;
        	 }
        	 else
        	 {
        		 to=addMarker(arg0,"To Marker","To Marker",BitmapDescriptorFactory.HUE_YELLOW);
        		
        		 flag=true;
        		 addLine=false;
        		 
        		 RouteInformations poly = new RouteInformations(new AsyncResponse() {
					
					@Override
					public void processFinish(RouteDetails arg0) {
						// TODO Auto-generated method stub
						try
						{
						  map.addPolyline(arg0.getLineOptions());
						  animateMarker(from,to.getPosition(),false);
						  //here you can get distance , duration it will return like you drive a car 
						  MUT.fastDialog(Map.this,"Time and Distance","Distance : "+arg0.getDistance()+"\nDuration : "+arg0.getDuration());
					  }
						catch(Exception e)
						{
							MUT.lToast(Map.this,"Can't draw line Try Again");
						}
						}
				});
        		 
        		//you should pass the 2 lat and lang which you want to draw a aline or get distance or duration between them 
        		 RouteDetails ll=new RouteDetails();
         		ll.setLatLong1(from.getPosition());
         		ll.setLatLong2(to.getPosition());
        		
        		poly.execute(ll);
        	 }
         }
         else
         {
        	 myLocMarker= addMarker(arg0,"Marker Added","You Added New Marker",BitmapDescriptorFactory.HUE_ROSE); 
             myLocMarker.showInfoWindow();
        	 
         }
         
         

	       
	    }
	    
		@Override
		public boolean onMyLocationButtonClick() {
			// TODO Auto-generated method stub
			MUT.fastDialog(this,"My Location Button Pressed","Location Button");
			return false;
		}
		
		@Override
		public void onMyLocationChange(Location arg0) {
			 //create initial marker
			lat=arg0.getLatitude();
			lng=arg0.getLongitude();
			if(!mainMarker&&(lat>0.0||lng>0.0))
			{
			 map.addMarker( new MarkerOptions()
			    .position( new LatLng(lat,lng) )
			    .title("MainMarker").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
			    .snippet("Long Click to Drag Me")).showInfoWindow();
			    CameraPosition INIT = new CameraPosition.Builder().target(new LatLng(lat,lng)).zoom(17.5F).build();
			    map.animateCamera( CameraUpdateFactory.newCameraPosition(INIT));
			
			mainMarker=true;
			}
			if(myLocMarker!=null)myLocMarker.remove();
			myLocMarker=  map.addMarker( new MarkerOptions()
               .position( new LatLng(arg0.getLatitude(),arg0.getLongitude()) )
               .title("My Location Marker")
               .snippet(arg0.getLatitude()+"\n"+arg0.getLongitude()));
           
			
		}   
		
		public Marker addMarker(LatLng latLng,String title,String snippet,float color)
		{
			return map.addMarker( new MarkerOptions()
            .position(latLng)
            .title(title).icon(BitmapDescriptorFactory.defaultMarker(color))
            .snippet(snippet));
			
		}
		
		   public void animateMarker(final Marker marker, final LatLng toPosition,
		            final boolean hideMarker) {
		        final Handler handler = new Handler();
		        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
		        final long start = SystemClock.uptimeMillis();
		        Projection proj = map.getProjection();
		        Point startPoint = proj.toScreenLocation(marker.getPosition());
		        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		        final long duration = 3500;
		        final Interpolator interpolator = new LinearInterpolator();
		        handler.post(new Runnable() {
		            @Override
		            public void run() {
		                long elapsed = SystemClock.uptimeMillis() - start;
		                float t = interpolator.getInterpolation((float) elapsed
		                        / duration);
		                double lng = t * toPosition.longitude + (1 - t)
		                        * startLatLng.longitude;
		                double lat = t * toPosition.latitude + (1 - t)
		                        * startLatLng.latitude;
		                marker.setPosition(new LatLng(lat, lng));
		                if (t < 1.0) {
		                    // Post again 16ms later.
		                    handler.postDelayed(this, 16);
		                } else {
		                    if (hideMarker) {
		                        marker.setVisible(false);
		                    } else {
		                        marker.setVisible(true);
		                    }
		                }
		            }
		        });
		    }
	
}
