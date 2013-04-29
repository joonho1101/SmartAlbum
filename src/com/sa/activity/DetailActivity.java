package com.sa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sa.db.bean.Photo;
import com.sa.smartalbum.R;
import com.sa.uploader.PhotoUploader;

public class DetailActivity extends BaseActivity {

	private Photo photo;
	private TextView captionView;
	private View caption_edit_wrapper;
	private TextView captionEditView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getPhotoFromIntent();
		initImageView();
		initCaptionView();
		initEditCaptionView();
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
		captionView = (TextView) findViewById(R.id.caption);
		String comment = photo.getComment();
		if (comment == null || comment.length() < 1) {
			comment = "Add comment...";
		}
		captionView.setText(comment);
	}

	private void initEditCaptionView() {
		caption_edit_wrapper = findViewById(R.id.caption_edit_wrapper);
		captionEditView = (TextView) findViewById(R.id.caption_edit);
	}

	private void showEditCaption() {
		captionView.setVisibility(View.GONE);
		caption_edit_wrapper.setVisibility(View.VISIBLE);
	}

	private void hideEditCaption() {
		captionView.setVisibility(View.VISIBLE);
		caption_edit_wrapper.setVisibility(View.GONE);
	}

	public void editCaption(View view) {
		showEditCaption();
		captionEditView.setText(photo.getComment());
	}

	public void saveEditCaption(View view) {
		hideEditCaption();
		String text = captionEditView.getText().toString();
		photo.setComment(text);
		savePhoto(photo);
		captionView.setText(text);
	}

	public void cancelEditCaption(View view) {
		hideEditCaption();
	}

	private void initLocationView() {
		TextView locationView = (TextView) findViewById(R.id.location);
		locationView.setText(photo.getPlace());
	}

	public void share(View view) {
		startPhotoShareIntent();
	}

	private void startPhotoShareIntent() {
		PhotoUploader uploader = new PhotoUploader();
		Intent share = uploader.createShareIntent(photo);
		startActivity(Intent.createChooser(share, "Share via..."));
	}

	/*
	 * An intent to start ImageViewActivity
	 */
	public void startImageViewActivity(View view) {
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
