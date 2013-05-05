package com.sa.uploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;

import com.sa.db.bean.Media;
import com.sa.db.bean.Photo;

public class PhotoUploader extends BaseUploader {

	@Override
	public Intent createShareIntent(Media media) {
		return createPhotoShareIntent((Photo) media);
	}

	public Intent createPhotoShareIntent(Photo photo) {		
	    Bitmap bmp = photo.getBitmap();

		File storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    File photo_file = new File(storagePath, Long.toString(System.currentTimeMillis()) + ".png");
		
	    FileOutputStream fos;
		try {
			fos = new FileOutputStream(photo_file);
			bmp.compress(CompressFormat.PNG, 100, fos);
	    	fos.flush();
	    	fos.close();
		}
		catch (FileNotFoundException e) {
		}
		catch (IOException e) {
		}

		sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("image/png");

		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photo_file));
		
		try{
			addInfo(photo);
		}
		catch(Exception e){
			//Toast.makeText(context, "add info failed", Toast.LENGTH_SHORT).show();
		}
			
		return sharingIntent;	
	}
}
