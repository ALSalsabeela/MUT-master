package com.example.mosusedtools;

import android.app.Activity;
import android.os.Bundle;

public class ActivityAnimations extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getIntExtra("trans",0)==1)
		overridePendingTransition(R.anim.right,R.anim.left);
		else if(getIntent().getIntExtra("trans",0)==2)
		overridePendingTransition(R.anim.top,R.anim.bottom);
		else if(getIntent().getIntExtra("trans",0)==3)
        overridePendingTransition(R.anim.diagonal_star,R.anim.digonal_bottom);
		else if(getIntent().getIntExtra("trans",0)==4)
	        overridePendingTransition(R.anim.custom_animation,R.anim.left);
		
		
		//second parameter the current activity
		setContentView(R.layout.activity_activity_animations);	
	}
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		if(getIntent().getIntExtra("trans",0)==1)
		overridePendingTransition(R.anim.left_back,R.anim.right_back);
		else if(getIntent().getIntExtra("trans",0)==2)
		overridePendingTransition(R.anim.bottom_back,R.anim.top_back);
		else if(getIntent().getIntExtra("trans",0)==3)
	    overridePendingTransition(R.anim.digonal_bottom_back,R.anim.diagonal_star_back);
	}
	
}
