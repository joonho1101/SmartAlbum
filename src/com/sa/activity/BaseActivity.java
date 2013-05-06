/**
 * SmartAlbum --
 * author - Phillip Huh(phuh) , Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * improved version of album that uses internal database to store necessary components of photos
 * such as voice, text memo, location, actual photo, etc
 */
package com.sa.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.sa.db.bean.Photo;
import com.sa.db.dao.DatabaseHandler;
import com.sa.service.LocationService;
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

	public DatabaseHandler db;
	private LocationService ls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		db = new DatabaseHandler(getApplicationContext());
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

	private LocationService getLocationService() {
		if (ls == null) {
			ls = new LocationService();
		}
		return ls;
	}

	public Location getLastLocation() {
		return getLocationService().getLastLocation();
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

	abstract int getMenuId();

	abstract int getLayoutId();
}