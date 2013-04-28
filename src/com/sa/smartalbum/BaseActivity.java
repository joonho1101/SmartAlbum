package com.sa.smartalbum;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.sa.db.layout.DatabaseHandler;
import com.sa.db.layout.data.Photo;

/**
 * Base Activity for SmartAlbum that contains commonly used methods and utility
 * functions.
 * 
 * @author Joon
 * 
 */
public abstract class BaseActivity extends Activity {

	public Location lastLocation = null;

	public DatabaseHandler db = new DatabaseHandler(getApplicationContext());

	// Acquire a reference to the system Location Manager
	public LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(getMenuId(), menu);
		return true;
	}

	public Photo getPhotoById(int id) {
		return db.getPhoto(id);
	}

	public boolean savePhoto(Photo photo) {
		try {
			if (photo.getId() == 0) {
				return db.addPhoto(photo) != -1;
			}
			else {
				return db.updatePhoto(photo) > 0;
			}
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean deletePhoto(int id) {
		try {
			return db.deletePhoto(id) > 0;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean deletePhoto(Photo photo) {
		return deletePhoto(photo.getId());
	}

	/**
	 * Display a toast message
	 * 
	 * @param msg
	 *            a message to display
	 */
	public void makeToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Returns last known location
	 * 
	 * @return last know location
	 */
	public Location getLastKnownLocation() {
		return locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	/**
	 * Requests location updatess
	 */
	public void requestLocationUpdates() {
		// Define a listener that responds to location updates
		LocationListener locListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				lastLocation = location;
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

	abstract int getMenuId();

	abstract int getLayoutId();

}