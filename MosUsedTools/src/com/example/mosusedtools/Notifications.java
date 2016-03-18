package com.example.mosusedtools;

import com.example.androidprogramminghelper.MUT;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;



public class Notifications extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications);
	}
	
	public void notSound(View view)
	{
		Intent intent = new Intent(this, StartActivity.class);
		PendingIntent contentIntent = 
		   		  PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
		  NotificationManager mNotificationManager;
		  NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
         .setContentTitle("Content Title")
         .setContentText("Content Text")
         .setContentInfo("Content Info")
         .setSubText("Sub Text").setSmallIcon(R.drawable.icon)
         .setSound( Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ninja))
         .setStyle(new NotificationCompat.BigTextStyle()
         .setBigContentTitle("Big Content Title")
         .bigText("Big Text").setBigContentTitle("Big Title").setSummaryText("Text Summary")).setContentIntent(contentIntent).setAutoCancel(true);
          mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
          mNotificationManager.notify(150, mBuilder.build());
	}
	
	public void bigImage(View view)
	{ 
		  
		  NotificationManager mNotificationManager;
		  NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
         .setContentTitle("Big Image Content Title")
         .setContentText("Big Image Content Text")
         .setContentInfo("Big Image Content Info")
         .
         setSubText("Big Image Sub Text").setSmallIcon(R.drawable.icon)
         .setStyle(new NotificationCompat.BigPictureStyle()
         .setBigContentTitle("Big Image Big Content Title").bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.notpic)).bigLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon))
         .setBigContentTitle("Big Image Big Title").setSummaryText("Big Image Text Summary")).setAutoCancel(true);
          mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
          mNotificationManager.notify(155, mBuilder.build());
	}
	
	public void notButton(View view)
	{
		String ns = Context.NOTIFICATION_SERVICE;
	    NotificationManager mNotificationManager=(NotificationManager) getSystemService(ns);
	    RemoteViews notificationView = new RemoteViews(getPackageName(),R.layout.notification_with_button);
	    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
	    .setContent(notificationView).setContentTitle("Title").setSmallIcon(R.drawable.icon);
	    Intent switchIntent = new Intent(this, switchButtonListener.class);
	    PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this,0,switchIntent, 0);
	    notificationView.setOnClickPendingIntent(R.id.not_button,pendingSwitchIntent);
	    mNotificationManager.notify(1, mBuilder.build());
		
	}
	

	
	
	public static class switchButtonListener extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	MUT.lToast(context,"Welcome To You");  
	    }
	}
	
}
