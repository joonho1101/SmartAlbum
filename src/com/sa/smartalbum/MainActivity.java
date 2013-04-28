package com.sa.smartalbum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import com.sa.db.layout.data.Photo;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
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

	private File photo_file; // ///

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	// private Integer[] mThumbIds = { R.drawable.ic_launcher };
	private Button button;
	private GridView gridView;
	private LinkedList<Photo> photos = new LinkedList<Photo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initializeButton(R.id.take_photo_button);
		initGridView(R.id.gridview);
	}

	/**
	 * Initializes a button and attach a listener for taking a photo.
	 */
	public void initializeButton(int viewId) {
		button = (Button) findViewById(viewId);
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
	 * 
	 * @param viewId
	 *            grid view id
	 */
	public void initGridView(int viewId) {
		gridView = (GridView) findViewById(viewId);
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
		// int id = requestCode - CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;
		if (requestCode >= CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				// makeToast("Image saved to:\n" + data.getData());

				// ////// Temporary code to allow sharing of last photo taken
				try {
					Bitmap bmp = (Bitmap) data.getExtras().get("data");

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] bytes = stream.toByteArray();

					byte[] byteArray = new byte[bytes.length];
					for (int i = 0; i < bytes.length; i++) {
						byteArray[i] = new Byte(bytes[i]);
					}

					Photo p = new Photo();
					p.setActualPhoto(byteArray);

					updateGrid();

					File storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

					photo_file = new File(storagePath, Long.toString(System.currentTimeMillis()) + ".png");

					final FileOutputStream fos = new FileOutputStream(photo_file);
					bmp.compress(CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
					makeToast("Image saved to:\n" + photo_file.getAbsolutePath().toString());

					p.setPath(photo_file.getAbsolutePath().toString());
					photos.push(p);

				}
				catch (Exception e) {
					makeToast("Oops. Something went wrong. Failed to save a photo.\n");
				}

				// /////////////////////////////////////////////

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

			// imageView.setImageResource(mThumbIds[position]);
			Photo p = photos.get(position);
			byte[] bytes = p.getActualPhoto();
			byte[] b = new byte[bytes.length];

			int j = 0;
			for (Byte a : bytes)
				b[j++] = a.byteValue();

			Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
			imageView.setImageBitmap(bitmap);

			return imageView;
		}
	}

	/*
	 * public void share(View button){ if(photo_file==null ||
	 * !photo_file.isFile()){ makeToast("Not photos available to share");
	 * return; } PhotoUploader up = new PhotoUploader(); Date d = new Date();
	 * 
	 * LocationManager lm =
	 * (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
	 * Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	 * 
	 * Intent share = up.upload(photo_file, "sample text", d, loc);
	 * startActivity(Intent.createChooser(share , "Share via...")); }
	 */

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