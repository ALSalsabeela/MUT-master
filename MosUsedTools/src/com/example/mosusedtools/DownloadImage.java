package com.example.mosusedtools;

import com.example.androidprogramminghelper.MUT;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class DownloadImage extends Activity {
	 DisplayImageOptions options;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_loader);
		
		MUT.fastDialog(this, "Image Loader","Thanks so much to ImageLoaderLibarary this images will only download 1 time then will be saved :D");
		
		/* 
		 I used imageLoader libarary to do this it's the best of the best 
		 more details about it GO here >>> https://github.com/nostra13/Android-Universal-Image-Loader
		 if you rich donate for this person please :D he really saved our time :)
		 this code you can download the image and cache it with only 5 lines of code :) 
		 */
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this) .build();
        ImageLoader.getInstance().init(config);
		ImageView circleImage=(ImageView)findViewById(R.id.circle_image);
		ImageView borderImage=(ImageView)findViewById(R.id.border_image);
		
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_launcher).showImageOnFail(R.drawable.ic_launcher).cacheInMemory().cacheOnDisc().displayer(new RoundedBitmapDisplayer(30)).build();
		 new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		 ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage("https://farm3.staticflickr.com/2670/3723392833_257bc70574.jpg",borderImage,options);
		
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_launcher).showImageOnFail(R.drawable.ic_launcher).cacheInMemory().cacheOnDisc().displayer(new RoundedBitmapDisplayer(360)).build();
		
		imageLoader.displayImage("http://djdesignerlab.com/wp-content/uploads/2010/march/amazing_3d_character_designs/amazing_3d_character_designs_6.jpg",circleImage,options);
		
			
	}
}
