package com.sa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sa.db.bean.Photo;
import com.sa.smartalbum.R;
import com.sa.uploader.PhotoUploader;

public class DetailActivity extends BaseActivity {

	private Photo photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getPhotoFromIntent();
		initImageView();
		initCaptionView();
		initLocationView();
	}

	public void getPhotoFromIntent() {
		photo = getPhotoById(getIntent().getIntExtra("id", 0));
	}

	public void initImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.image);
		imageView.setImageBitmap(photo.getBitmap());
	}

	public void initCaptionView() {
		TextView captionView = (TextView) findViewById(R.id.caption);
		captionView.setText(photo.getComment());
		captionView.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				editCaption();
			}
		});
	}

	public void initLocationView() {
		TextView locationView = (TextView) findViewById(R.id.location);
		locationView.setText(photo.getPlace());
		locationView.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				editLocation();
			}
		});
	}

	public void initShareButton() {
		Button shareButton = (Button) findViewById(R.id.share_button);
		shareButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startPhotoShareIntent();
			}
		});
	}

	public void startPhotoShareIntent() {
		PhotoUploader uploader = new PhotoUploader();
		Intent share = uploader.createShareIntent(photo);
		startActivity(Intent.createChooser(share, "Share via..."));
	}

	/*
	 * An intent to start ImageViewActivity
	 */
	public void startImageViewActivity(int id) {
		// TODO
	}

	public void editCaption() {
		// TODO
	}

	public void editLocation() {
		// TODO
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
