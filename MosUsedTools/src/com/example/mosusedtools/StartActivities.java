package com.example.mosusedtools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class StartActivities extends Activity {
	
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		   setContentView(R.layout.activity_start_activities);
			
			intent=new Intent(this,Tools.class);	
	}
		
		
	public void leftRight(View view)
	{
		intent.putExtra("animation", 1);
		startActivity(intent);
	}
	
	public void topDown(View view)
	{
		intent.putExtra("animation", 2);
		startActivity(intent);
	}
	
	public void diagonal(View view)
	{
		intent.putExtra("animation", 3);
		startActivity(intent);
	}
	
	public void customAnim(View view)
	{
		intent.putExtra("animation", 4);
		startActivity(intent);
	}
	
	public void noAnimation(View view)
	{
		intent.putExtra("animation",5);
		startActivity(intent);
	}
	
	
	
	
	
	}
