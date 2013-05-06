/**
 * SmartAlbum --
 * author - Phillip Huh(phuh) , Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * improved version of album that uses internal database to store necessary components of photos
 * such as voice, text memo, location, actual photo, etc
 */
package com.sa.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Toast;

import com.sa.db.bean.Photo;
import com.sa.db.dao.DatabaseHandler;
import com.sa.smartalbum.R;

/**
 * Base Activity for SmartAlbum that contains commonly used methods and utility
 * functions.
 * 
 * @author Joon Ho Cho(joonhoc), Phillip Huh(phuh), Isaac Simha (isimha)
 * 
 */
public abstract class BaseActivity extends Activity {

	public static final int RESULT_DELETE = 17;
	public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath().toString();

	public DatabaseHandler db;

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	public static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
	public static final String NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER;

	// Acquire a reference to the system Location Manager
	private LocationManager locationManager;
	private Location lastLocation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		db = new DatabaseHandler(getApplicationContext());
		// locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(getMenuId(), menu);
		return true;
	}

	/**
	 * Gets photo by primary id
	 * 
	 * @param id
	 * @return
	 */
	public Photo getPhotoById(int id) {
		return db.getPhoto(id);
	}

	/**
	 * Wrapper method that saves photo. If photo exists, it updates existing photo. If photo doesn't exist, it makes new
	 * photo.
	 * 
	 * @param photo
	 * @return true if it is added/updated correctly. false if there is an exception.
	 */
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

	/**
	 * Wrapper method that deletes photo by primary key.
	 * 
	 * @param id
	 * @return true if it is deleted correctly. False if there is an exception.
	 */
	public boolean deletePhoto(int id) {
		try {
			return db.deletePhoto(id) > 0;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wrapper method that deletes photo by photo class
	 * 
	 * @param photo
	 * @return true if it is deleted correctly. False if there is an exception
	 */
	public boolean deletePhoto(Photo photo) {
		return deletePhoto(photo.getId());
	}

	/**
	 * Confirms if the user wants to really delete the photo
	 * 
	 * @param photoId
	 * @param activity
	 */
	public void confirmDelete(final int photoId, final DetailActivity activity) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(getStringResource(R.string.confirm_delete)).setCancelable(false)
				.setPositiveButton(getStringResource(R.string.yes), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						deletePhoto(photoId);
						if (activity != null) {
							Intent resultIntent = new Intent();
							resultIntent.putExtra("id", photoId);
							resultIntent.putExtra("position", activity.position);
							activity.setResult(RESULT_DELETE, resultIntent);
							activity.finish();
						}
					}
				});
		alertDialogBuilder.setNegativeButton(getStringResource(R.string.cancel), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
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
	 * Returns string resource
	 * 
	 * @param id
	 * @return string resource
	 */
	public String getStringResource(int id) {
		return getResources().getString(id);
	}

	/**
	 * Location methods
	 */

	/**
	 * Returns whether GPS is enabled on the device
	 * 
	 * @return true if GPS is enabled
	 */
	private boolean isGPSEnabled() {
		return isLocationProviderEnabled(GPS_PROVIDER);
	}

	/**
	 * Returns whether Network location is enabled on the device
	 * 
	 * @return true if Network location is enabled
	 */
	private boolean isNetworkLocationEnabled() {
		return isLocationProviderEnabled(NETWORK_PROVIDER);
	}

	private boolean isLocationProviderEnabled(String provider) {
		return locationManager.isProviderEnabled(provider);
	}

	/**
	 * Returns last known location
	 * 
	 * @return last know location
	 */
	public Location getLastKnownLocation() {
		Location location = null;

		if (isGPSEnabled()) {
			makeToast("gps enabled");
			location = locationManager.getLastKnownLocation(GPS_PROVIDER);
			if (location != null) {
				return location;
			}
		}

		if (isNetworkLocationEnabled()) {
			makeToast("network enabled");
			location = locationManager.getLastKnownLocation(NETWORK_PROVIDER);
			if (location != null) {
				return location;
			}
		}

		showGPSDisabledAlertToUser();
		return null;
	}

	/**
	 * Gets the last recent/known location
	 * 
	 * @return
	 */
	public Location getLastLocation() {
		if (lastLocation == null) {
			lastLocation = getLastKnownLocation();
			if (lastLocation == null) {
				makeToast("lastLocation null");
			}
		}
		return lastLocation;
	}

	/**
	 * Gets the location listener
	 * 
	 * @return
	 */
	private LocationListener getLocationListener() {
		// Define a listener that responds to location updates
		return new LocationListener() {

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
	}

	/**
	 * Requests location updates if either GPS or network location service is
	 * enabled. Otherwise, ask user to enable GPS
	 */
	public void requestLocationUpdates() {
		if (isGPSEnabled()) {
			requestLocationUpdatesSafe(GPS_PROVIDER);
		}
		else if (isNetworkLocationEnabled()) {
			requestLocationUpdatesSafe(NETWORK_PROVIDER);
		}
		else {
			showGPSDisabledAlertToUser();
		}
	}

	/**
	 * Requests location updates given provider
	 * 
	 * @param provider
	 */
	private void requestLocationUpdatesSafe(String provider) {
		locationManager.requestLocationUpdates(provider, 0, 0, getLocationListener());
	}

	/**
	 * Asks user to enable GPS
	 */
	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(getStringResource(R.string.enable_gps))
				.setCancelable(false).setPositiveButton(getStringResource(R.string.yes), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						Intent callGPSSettingIntent =
								new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(callGPSSettingIntent);
					}
				});
		alertDialogBuilder.setNegativeButton(getStringResource(R.string.cancel), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	private boolean isBetterLocation(Location location, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		}
		else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		}
		else if (isNewer && !isLessAccurate) {
			return true;
		}
		else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether two providers are the same
	 */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	abstract int getMenuId();

	abstract int getLayoutId();
}