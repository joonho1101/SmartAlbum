package com.sa.smartalbum;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		attachCameraListener(R.id.take_photo_button);
	}

	/**
	 * Finds a button and attach a listener for taking a photo.
	 */
	public Button attachCameraListener(int viewId) {
		Button button = (Button) findViewById(viewId);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				takePhoto();
			}
		});
		return button;
	}

	/**
	 * Creates an intent to take a photo using Camera API.
	 */
	public void takePhoto() {
		// TODO
		final int PICTURE_TAKEN = 1;

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
		// File(filename)));
		startActivityForResult(intent, PICTURE_TAKEN);
	}

	/**
	 * An intent to start detail view for an image
	 */
	public void startDetailViewActivity(int id) {
		// TODO
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
