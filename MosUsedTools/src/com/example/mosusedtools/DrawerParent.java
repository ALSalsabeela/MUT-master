package com.example.mosusedtools;

import java.util.ArrayList;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

@SuppressLint({ "NewApi", "InflateParams" })
public class DrawerParent extends FragmentActivity 
{
	/* 
	 first thanks to this example http://www.androidhive.info/2013/11/android-sliding-menu-using-navigation-drawer/
	 
	 */
	
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	// nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_parent);
		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		 mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		 for(int i=0; i<3; i++)
		 { 
		 navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
		 }
	navMenuIcons.recycle();
	
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

	    View header = LayoutInflater.from(this).inflate(R.layout.header, null);
	    mDrawerList.addHeaderView(header);
		
		adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
		

		mDrawerList.setAdapter(adapter);
		 // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        if (savedInstanceState == null) {
            /* you can pass the fragment index which you want to show instead of 0
            you can use this for some thing like if you come from notification
        	you can pass intent.getvalue with the value you want */
        	displayView(1);
        }

	}	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
private class SlideMenuClickListener implements
ListView.OnItemClickListener {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
    {
	displayView(position);
   }
}



	private void displayView(int position) {
		
		
		if(position==1||position==0)
    	{
			if(FragmentNoAdded("mainf"))
			getSupportFragmentManager().beginTransaction()
	        .replace(R.id.frame_container,new MainFragment()).addToBackStack("mainf").commit();
    	}
		else if(position==2)
		{
			if(FragmentNoAdded("mapf"))
			{
				getSupportFragmentManager().beginTransaction()
		        .replace(R.id.frame_container,new MapFragment()).addToBackStack("mapf").commit();
			}
		}
		else if(position==3)
		{	
			if(FragmentNoAdded("tabparent"))
			getSupportFragmentManager().beginTransaction()
	        .replace(R.id.frame_container,new TabsParent()).addToBackStack("tabparent").commit();
		}
		
		 mDrawerList.setItemChecked(position, true);
		
		 position--;
		
		if(position>-1)
		{
            // update selected item and title, then close the drawer
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
           
		}
		 mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	
	 public Boolean FragmentNoAdded(String name)
	 {
		 
		 try
		 {
		 if(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getName().equals(name))
	        return false;
		 return true;
		 }
		 catch(Exception e)
		 {
			 return true;
		 }
	}
}
