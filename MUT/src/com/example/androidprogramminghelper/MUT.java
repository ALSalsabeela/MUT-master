package com.example.androidprogramminghelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;


public class MUT 
{
	
	 public static void settingsDialog(final Activity activity,final String action , String title, String message )
		    {
		        final AlertDialog.Builder builder =
		            new AlertDialog.Builder(activity);
		        
		        builder.setTitle(title);
		        builder.setMessage(message)
		            .setPositiveButton("NO",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface d, int id) {
		                        
		                    }
		            })
		            .setNegativeButton("YES",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface d, int id) {
		                    	activity.startActivity(new Intent(action));
		                        d.dismiss();
		                      //  alertDialog.cancel();
		                    }
		            });
		       builder.create().show();
		    }
	 
	 //<uses-permission android:name="android.permission.CALL_PHONE" />
	 public static void makeCall(Context context,String number)
	 {
		 Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		 context.startActivity(intent);
	 }
	 
	 
	 public static void fastDialog(final Activity activity , String title , String message)
	    {
	        final AlertDialog.Builder builder =
	        new AlertDialog.Builder(activity);
	        builder.setTitle(title);
	        builder.setMessage(message).setPositiveButton("OK",new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface d, int id) {
	                    }
	            });
	       builder.create().show();
	    }
	 
	 
	 public static void lToast(Context context,String text)
	 {
		 Toast.makeText(context,text,Toast.LENGTH_LONG).show();
	 }
	 
	 public static void sToast(Context context,String text)
	 {
		 Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
	 }
	 
	 
	 public static void dToast(Context context,String text,int duration)
	 {
		 
	Toast.makeText(context, text, duration).show();
	
	 }
	 
	 public static void startActivity(Context context,Class<?> activity)
	 {
		 Intent intent = new Intent(context,activity);
		 context.startActivity(intent);
		 
	 }
	 

		@SuppressLint("NewApi")
		public static Bitmap createDrawableFromView(Context context, View view) {
			DisplayMetrics displayMetrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
			view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
			view.buildDrawingCache();
			Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			view.draw(canvas);
	 
			return bitmap;
		}

}
