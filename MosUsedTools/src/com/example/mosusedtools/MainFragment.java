package com.example.mosusedtools;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;





public class MainFragment extends Fragment 
{
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

	    rootView = inflater.inflate(R.layout.activity_main_fragment, container, false);
		
		
		return rootView;
	}
	

	@Override
	public void onDestroyView() 
	{	
	 super.onDestroyView();
	}
	
		
}