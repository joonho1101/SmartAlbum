package com.sa.smartalbum;

import android.app.Activity;
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

	public DatabaseHandler db = new DatabaseHandler(getApplicationContext());

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

	abstract int getMenuId();

	abstract int getLayoutId();

}