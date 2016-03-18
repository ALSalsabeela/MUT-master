package com.example.mosusedtools;




import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class TabsParent extends   Fragment  {

	 FragmentTabHost mTabHost;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		
		 rootView = inflater.inflate(R.layout.activity_tabs_parent, container, false);		
		
		
		
         mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);

         mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		  
		  mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("TabOne"),TabMap.class, null);
	      setTabIcon(mTabHost, 0, R.drawable.drawer3);
	      setTabColor(mTabHost);
		 
		
  	  mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("TabTwo"),MainFragment.class, null);
	   
     setTabIcon(mTabHost, 1, R.drawable.drawer2);
     changetextcolor(mTabHost);

      mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

          @Override
          public void onTabChanged(String arg0) {

              changetextcolor(mTabHost);
          }
           });
     

      
     
		return rootView;
	}
	

   	
   
	
	public View createTabIndicator(LayoutInflater inflater, TabHost tabHost, int textResource, int iconResource) 
   {
       View tabIndicator = inflater.inflate(R.layout.tab_indicator, tabHost.getTabWidget(), false);
       ((TextView) tabIndicator.findViewById(android.R.id.title)).setText(textResource);
       ((ImageView) tabIndicator.findViewById(android.R.id.icon)).setImageResource(iconResource);
       
       return tabIndicator;
   }
	
	public void changetextcolor(TabHost tabhost) 
   { 
		TextView textView ;
         
       for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
       	textView = (TextView) tabhost.getTabWidget().getChildTabViewAt(i).findViewById(android.R.id.title);
       	textView.setTextColor(Color.parseColor("#FFFFFF"));
       	tabhost.getTabWidget().getChildTabViewAt(i).setBackgroundColor(Color.parseColor("#000000"));
       	
       }
       
       textView = (TextView) tabhost.getTabWidget().getChildTabViewAt(tabhost.getCurrentTab()).findViewById(android.R.id.title);
       textView .setTextColor(Color.parseColor("#000000"));
       tabhost.getTabWidget().getChildTabViewAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#7F8708"));
   }
   
   public void setTabIcon(TabHost tabHost, int tabIndex, int iconResource) {
       ImageView tabImageView = (ImageView) tabHost.getTabWidget().getChildTabViewAt(tabIndex).findViewById(android.R.id.icon);
       tabImageView.setVisibility(View.VISIBLE);
       tabImageView.setImageResource(iconResource);
   }
   
   
   
	public static void setTabColor(TabHost tabhost) {
	    for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
	       
		    tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#CFCFCF")); 
	    
	      
	    }
	   
	}
	
	
	
	@Override
	public void onDestroyView() {
		try
		{
			 mTabHost.setCurrentTab(0);
		
	    
	   
		}
		catch(Exception e){}
	    super.onDestroyView();        
	}
}
