package com.sa.smartalbum;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sa.db.layout.data.Photo;
import com.sa.entities.PhotoUploader;

public class DetailActivity extends BaseActivity {

	private Photo photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initImageView();
	}

	public Photo getPhotoFromIntent() {
		Intent i = getIntent();
		int id = i.getIntExtra("id", 0);
		// need to modify
		// return Photo.load(id);
		return null;
	}

	public void initImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.image);
		imageView.setImageBitmap(getPhotoFromIntent().getBitmap());
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
		PhotoUploader uploader = new PhotoUploader();
		Intent share = uploader.createShareIntent(photo);
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
			@Override
			public void onLocationChanged(Location location) {
				storeLocation(location);
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
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
