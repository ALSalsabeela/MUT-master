package com.example.mosusedtools;

import java.util.ArrayList;




import com.example.androidprogramminghelper.MUT;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewActivity extends Activity {
	ArrayList<ListItem> items;
	ListAdapter adapter;
	String imageUrls[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this) .build();
        ImageLoader.getInstance().init(config);
        
		imageUrls=new String[8];
		imageUrls[0]="http://www.mygoogoo.com/wp-content/uploads/2015/12/Amazing-Photos-16.jpg";
		imageUrls[1]="http://1.bp.blogspot.com/_T7JhE9zEJlA/TFhxGYblWbI/AAAAAAAAG9g/E3SIA5pRboY/s1600/!cid_700F1530C8514AA0A9C20434AC2C00AE@BrianPC.jpg";
		imageUrls[2]="http://www.theamazingpics.com/wp-content/uploads/2014/03/Amazing-Photo-of-Bigar-Waterfall-in-Romania.jpg";
		imageUrls[3]="http://wowpics.in/wp-content/uploads/2011/07/amazing-animals-friendship-11.jpg";
		imageUrls[4]="http://demilked2.uuuploads.com/amazing-places/amazing-places-1.jpg";
		imageUrls[5]="http://createapk.com/project/2014/05/kep161289/amazing-spider-man-wallpaper-android-apps/image/3609-amazing-spider-man-wallpaper-android-apps.jpg";
	    imageUrls[6]="http://cdn3.spicytricks.com/wp-content/uploads/2013/06/White-Tiger-Blue-Eye-Amazing-Android-Wallpaper-HD.jpg";				
	    imageUrls[7]="http://wallpapercave.com/wp/4Eg4frU.jpg";
	    createlist();
	}
	
	 public void createlist()
		{
		 getdata();
			adapter =new ListAdapter(this,items);
			
			ListView list= (ListView)findViewById(R.id.list);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> a, View v, int position, long id)
				{ 
				MUT.sToast(ListViewActivity.this,"You pressed on "+items.get(position)+" item");
				}  
			});		
		}
	
	public void getdata()
	{
		items =new ArrayList<ListItem>();
		int o=0;
		for(int i=0; i<20; i++)
		{
			ListItem item=new ListItem();
			item.like=true;
			item.title="Item Number"+(i+1);
			item.imageurl=imageUrls[o];
			o++;
			if(o>7)o=0;
			items.add(item);
		}
	}
	
	
}
