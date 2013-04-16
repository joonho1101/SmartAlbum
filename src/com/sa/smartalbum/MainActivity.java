package com.sa.smartalbum;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private Integer[] mThumbIds = { R.drawable.ic_launcher };
	private Button button;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeButton(R.id.take_photo_button);
		initGridView(R.id.gridview);
	}

	/**
	 * Initializes a button and attach a listener for taking a photo.
	 */
	public void initializeButton(int viewId) {
		button = (Button) findViewById(viewId);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startTakePhotoActivity();
			}
		});
	}

	/**
	 * Initializes a thumbnail grid view.
	 * 
	 * @param viewId
	 *            grid view id
	 */
	public void initGridView(int viewId) {
		gridView = (GridView) findViewById(viewId);
		gridView.setAdapter(new ImageAdapter(this));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startViewPhotoActivity(position);
			}
		});
	}

	/**
	 * Creates an intent to take a photo using Camera API.
	 */
	public void startTakePhotoActivity() {
		Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// takePhotoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		// takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
		// File(filename)));
		startActivityForResult(takePhotoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	/**
	 * Creates an intent to view photo in detail.
	 */
	public void startViewPhotoActivity(int position) {
		Intent viewPhotoIntent = new Intent(MainActivity.this, DetailActivity.class);
		Log.e("intent : ", "" + position);
		viewPhotoIntent.putExtra("position", position);
		startActivity(viewPhotoIntent);
	}

	/**
	 * Callback for when activity result is returned
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				makeToast("Image saved to:\n" + data.getData());
			}
			else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			}
			else {
				// Image capture failed, advise user
				makeToast("Oops. Something went wrong. Failed to save a photo.\n");
			}
		}
	}

	/**
	 * This class loads the image gallery in grid view.
	 */
	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				// if it's not recycled, initialize some attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			}
			else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(mThumbIds[position]);
			return imageView;
		}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
