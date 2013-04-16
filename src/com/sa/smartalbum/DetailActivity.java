package com.sa.smartalbum;

import java.io.File;
import java.util.Date;

import com.sa.db.layout.data.Photo;
import com.sa.entities.PhotoUploader;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

public class DetailActivity extends Activity {

	private File photo_file;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Intent i = getIntent();
		String path = i.getStringExtra("photo");
		ImageView iv = (ImageView) findViewById(R.id.image);
		
		photo_file = new  File(path);
		if(photo_file.exists()){
		    Bitmap myBitmap = BitmapFactory.decodeFile(photo_file.getAbsolutePath());
		    iv.setImageBitmap(myBitmap);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	/*
	 * An intent to start ImageViewActivity
	 */
	public void startImageViewActivity(int id) {
		// TODO
	}
	
	/*
	 * An intent to share
	 */
	public void share(View button){
		if(photo_file==null || !photo_file.isFile()){
			Toast.makeText(getApplicationContext(), "Not photos available to share", Toast.LENGTH_SHORT).show();
			return;
		}
		PhotoUploader up = new PhotoUploader();
		Date d = new Date();
		
		LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		Intent share = up.upload(photo_file, "sample text", d, loc);
		startActivity(Intent.createChooser(share , "Share via...")); 	
	}
	
	
}
