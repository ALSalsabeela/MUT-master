package com.example.mosusedtools;
import com.example.androidprogramminghelper.MUT;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StartActivity extends Activity
{

	Intent intent ;	
	 MediaPlayer mp ;
	ImageView doorLeft,doorRight;
	Button start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent= new Intent(StartActivity.this, ActivityAnimations.class);
		setContentView(R.layout.activity_start);
		doorLeft=(ImageView)findViewById(R.id.door_left);
		doorRight=(ImageView)findViewById(R.id.door_right);
		start=(Button)findViewById(R.id.start);
        
	}
		public void start(View view)
		{

			mp= MediaPlayer.create(this, R.raw.music);
			mp.start();
			start.setVisibility(View.GONE);
			slideToLeft(doorLeft);
	        slideToRight(doorRight);
	        
		}
		
		public void webFiles(View view)
		{
			MUT.startActivity(this,ConnectToWebServices.class);	
		}
		
		public void animations(View view)
		{
			MUT.startActivity(this,AmazingAnimations.class);
		}
		
		public void startActivities(View view)
		{
			MUT.startActivity(this,StartActivities.class);
		}
		
		
		public void aboutUs(View view)
		{
			MUT.startActivity(this,AboutUs.class);
		}
		
		public void mostUsed(View view)
		{
			MUT.fastDialog(this,"Please Check MUT Class","Just Go to code after import our libarary then write MUT. then Ctrl+space you will find many methods :)" );
		}
		
		
		public void listView(View view)
		{
			MUT.startActivity(this,ListViewActivity.class);
		}
		
		
		public void tabsMenue(View view)
		{
			MUT.startActivity(this,DrawerParent.class);
		}
      
		public void imageDownload(View view)
		{
			MUT.startActivity(this,DownloadImage.class);
		}
		
		
		public void notifications(View view)
		{
			MUT.startActivity(this,Notifications.class);
		}
		
		public void map(View view)
		{
			MUT.startActivity(this,Map.class);
		}
		public void slideToRight(View view){
			TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
			animate.setDuration(3000);
			animate.setFillAfter(true);
			view.startAnimation(animate);
			view.setVisibility(View.GONE);
			}
			// To animate view slide out from right to left
			public void slideToLeft(View view){
			TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
			animate.setDuration(3000);
			animate.setFillAfter(true);
			view.startAnimation(animate);
			animate.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					
					mp.stop();
					mp= MediaPlayer.create(StartActivity.this, R.raw.start);
					mp.start();
					
				LinearLayout ll=(LinearLayout)findViewById(R.id.start_layout);
				ll.setVisibility(View.VISIBLE);
					
					
				}
			});
			view.setVisibility(View.GONE);
			}
}
