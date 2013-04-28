package com.sa.smartalbum;

import java.util.LinkedList;
import com.sa.db.layout.data.Photo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends BaseActivity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	// private Integer[] mThumbIds = { R.drawable.ic_launcher };
	private Button button;
	private GridView gridView;
	private LinkedList<Photo> photos = new LinkedList<Photo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initializeButton();
		initGridView();
	}

	/**
	 * Initializes a button and attach a listener for taking a photo.
	 */
	public void initializeButton() {
		button = (Button) findViewById(R.id.take_photo_button);
		button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startTakePhotoActivity();
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

		// launch camera intent with photo id
		startActivityForResult(takePhotoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE + photos.size());
	}

	/**
	 * Initializes a thumbnail grid view.
	 */
	public void initGridView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startViewPhotoActivity(position, id);
			}
		});
	}

	/**
	 * Creates an intent to view photo in detail.
	 */
	public void startViewPhotoActivity(int position, long id) {
		Intent viewPhotoIntent = new Intent(MainActivity.this, DetailActivity.class);
		Log.e("intent : ", "" + position);
		viewPhotoIntent.putExtra("position", position);
		viewPhotoIntent.putExtra("id", id);

		startActivity(viewPhotoIntent);
	}

	/**
	 * Callback for when activity result is returned
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode >= CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Photo p = new Photo();
				p.setBitmap((Bitmap) data.getExtras().get("data"));
				p.setLocation(lastLocation);

				if (savePhoto(p)) {
					makeToast("Image saved");
					photos.push(p);
					updateGrid();
				}
				else {
					makeToast("Oops. Something went wrong. Failed to save a photo.\n");
				}
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

		@Override
		public int getCount() {
			// return mThumbIds.length;
			return photos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				// if it's not recycled, initialize some attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(4, 60, 4, 60);
			}
			else {
				imageView = (ImageView) convertView;
			}

			Photo p = photos.get(position);
			imageView.setImageBitmap(p.getBitmap());

			return imageView;
		}
	}

	public void updateGrid() {
		gridView.invalidateViews();
		gridView.setAdapter(new ImageAdapter(this));
	}

	@Override
	int getMenuId() {
		return R.menu.main;
	}

	@Override
	int getLayoutId() {
		return R.layout.activity_main;
	}

}