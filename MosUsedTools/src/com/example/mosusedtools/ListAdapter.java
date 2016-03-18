package com.example.mosusedtools;

import java.util.ArrayList;

import com.example.androidprogramminghelper.MUT;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.view.View.OnClickListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	private static ArrayList<ListItem> items;
	
	private LayoutInflater inflatere;
    Context context;
    private DisplayImageOptions options;
	
	public ListAdapter(Context context, ArrayList<ListItem> results) {
		items = results;
		inflatere = LayoutInflater.from(context);
		this.context=context;
		options = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.man)
        .showImageOnFail(R.drawable.man)
        .cacheInMemory(true)
        .cacheOnDisk(true)
        .displayer(new RoundedBitmapDisplayer(20))
        .build();
		
	}

	public int getCount() {
		
		try
		{
		return items.size();
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	
	@SuppressLint("InflateParams")
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
	
		
		if (convertView == null) {
			convertView = inflatere.inflate(R.layout.list_item_details, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.list_text);
			holder.image= (ImageView) convertView.findViewById(R.id.list_image);
			holder.like = (Button)convertView.findViewById(R.id.like);
			holder.delete = (Button)convertView.findViewById(R.id.delete);
			holder.progressBar=(ProgressBar)convertView.findViewById(R.id.progressbar);
			convertView.setTag(holder);
		} 
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
        
		holder.like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MUT.sToast(context,"I like "+items.get(position).title);
			}
		});
		
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				MUT.sToast(context,items.get(position).title+" has been deleted");
				items.remove(position);
				notifyDataSetChanged();
			}
		});
		
		holder.text.setText(items.get(position).title);
		 ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(items.get(position).imageurl,holder.image,options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				holder.progressBar.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				holder.progressBar.setVisibility(View.GONE);
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				holder.progressBar.setVisibility(View.GONE);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				holder.progressBar.setVisibility(View.GONE);
			}
		});
		
		return convertView;
	}
	
	


	static class ViewHolder {
		TextView text;
		ImageView image;
		Button like,delete;
		ProgressBar progressBar;
		
	}
}
