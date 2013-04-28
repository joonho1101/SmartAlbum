package com.sa.smartalbum;

import android.content.Intent;
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
		Photo photo = getPhotoById(i.getIntExtra("id", 0));
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

	@Override
	int getMenuId() {
		return R.menu.detail;
	}

	@Override
	int getLayoutId() {
		return R.layout.activity_detail;
	}

}
