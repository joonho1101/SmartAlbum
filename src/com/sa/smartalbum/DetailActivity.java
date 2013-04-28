package com.sa.smartalbum;

import java.io.File;
import java.util.Date;

import com.sa.entities.PhotoUploader;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DetailActivity extends BaseActivity {

	private File photo_file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent i = getIntent();
		String path = i.getStringExtra("photo");
		ImageView iv = (ImageView) findViewById(R.id.image);

		photo_file = new File(path);
		if (photo_file.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(photo_file.getAbsolutePath());
			iv.setImageBitmap(myBitmap);
		}
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
	public void share(View button) {
		if (photo_file == null || !photo_file.isFile()) {
			Toast.makeText(getApplicationContext(), "Not photos available to share", Toast.LENGTH_SHORT).show();
			return;
		}
		PhotoUploader up = new PhotoUploader();
		Date d = new Date();

		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		Intent share = up.upload(photo_file, "sample text", d, loc);
		startActivity(Intent.createChooser(share, "Share via..."));
	}

	public void storeLocation(Location location) {
		// TODO
	}

	public void getLocation() {
		// Acquire a reference to the system Location Manager
		LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// get last known location
		Location location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null) {
			storeLocation(location);
		}

		// Define a listener that responds to location updates
		LocationListener locListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				storeLocation(location);
			}

			public void onProviderDisabled(String provider) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
	}

	@Override
	int getMenuId() {
		return R.menu.detail;
	}

	@Override
	int getLayoutId() {
		return R.layout.activity_detail;
	}

}
