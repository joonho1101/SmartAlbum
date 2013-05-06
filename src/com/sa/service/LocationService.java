package com.sa.service;

import com.sa.smartalbum.R;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class LocationService extends Service {

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	public static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
	public static final String NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER;

	// Acquire a reference to the system Location Manager
	private LocationManager locationManager;
	private Location lastLocation = null;

	public LocationService() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

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
		if (isGPSEnabled()) {
			return locationManager.getLastKnownLocation(GPS_PROVIDER);
		}
		else if (isNetworkLocationEnabled()) {
			return locationManager.getLastKnownLocation(NETWORK_PROVIDER);
		}
		else {
			showGPSDisabledAlertToUser();
			return null;
		}
	}

	/**
	 * Gets the last recent/known location
	 * 
	 * @return
	 */
	public Location getLastLocation() {
		if (lastLocation == null) {
			lastLocation = getLastKnownLocation();
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
				if (isBetterLocation(location, lastLocation)) {
					lastLocation = location;
				}
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
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
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

	/**
	 * Returns string resource
	 * 
	 * @param id
	 * @return string resource
	 */
	public String getStringResource(int id) {
		return getResources().getString(id);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
