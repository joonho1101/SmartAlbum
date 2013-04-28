package com.sa.uploader;

import com.sa.db.bean.Media;
import com.sa.db.bean.Photo;

import android.content.Intent;

public class PhotoUploader extends BaseUploader {

	private Intent sharingIntent;

	@Override
	public Intent createShareIntent(Media media) {
		return createPhotoShareIntent((Photo) media);
	}

	public Intent createPhotoShareIntent(Photo photo) {
		sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("image/png");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, photo.getActualPhoto());
		addInfo(photo);
		return sharingIntent;
	}
}
