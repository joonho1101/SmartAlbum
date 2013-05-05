package com.sa.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
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
	public int position;
	
	private MediaRecorder mr;
	private String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp";
    boolean mStartRecording = true;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getPhotoFromIntent();
		initImageView();
		initCaptionView();
		initEditCaptionView();
		initLocationView();
	}

	

	private void onRecord(boolean start) {
	      if (start) {
	          startRecording();
	      } 
	      else {
	          stopRecording();
	      }
	}
	 

	private void startRecording() {
		mr = new MediaRecorder();
		try{
			mr.setAudioSource(MediaRecorder.AudioSource.MIC);
		}
		catch(Exception e){
		}
		try{
			mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		}
		catch(Exception e){
		}

        try{
        	mr.setOutputFile(filename);
        }
		catch(Exception e){
		}
		try{
			mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		}
		catch(Exception e){
		}
		try {
			mr.prepare();
		}
		catch (IllegalStateException e) {
		}
		catch (IOException e) {
		}
        try{
        	mr.start();
        }
        catch(Exception e){
        }
	}
	
    private void stopRecording() {
        mr.stop();
        mr.release();
        mr = null;
        
        byte[] b = getBytesFromFile(filename);
        photo.setVocalComment(b);
        try{
        	db.updatePhoto(photo);
        }
        catch(Exception e){
        	makeToast("Could not update photo");
        }
        
    }
    
    public void recordClick(View v) {
        onRecord(mStartRecording);
        Button b = (Button)findViewById(R.id.record_button);
        if (mStartRecording) {
            b.setText("Stop recording");
        } else {
            b.setText("Start recording");
        }
        mStartRecording = !mStartRecording;
    }

	public byte[] getBytesFromFile(String filename){
		int bytesRead;
		   try {
               FileInputStream is = new FileInputStream(filename);
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               byte[] b = new byte[1024];
               while ((bytesRead = is.read(b)) != -1) {
                   bos.write(b, 0, bytesRead);
               }
               return b;
           }
		   catch(Exception e){
			   makeToast("Could not convert audio file to byte array");
			   return null;
		   }
	}
	
	public void getFileFromBytes(String filename, byte[] b){
		int bytesRead;
		   try {
            FileOutputStream os = new FileOutputStream(filename);
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            while ((bytesRead = bis.read()) != -1) {
                os.write(b, 0, bytesRead);
            }
         }
		   catch(Exception e){
			   makeToast("Could not convert byte array to audio file");
		   }
	}
	
	
	public void getPhotoFromIntent() {
		photo = getPhotoById(getIntent().getIntExtra("id", -1));
		position = getIntent().getIntExtra("position", -1);
	}

	public void initImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.image);
		imageView.setImageBitmap(photo.getBitmap());
	}

	public void initCaptionView() {
		captionView = (TextView) findViewById(R.id.caption);
		String comment = photo.getComment();
		if (comment == null || comment.length() < 1) {
			comment = getStringResource(R.string.caption_placeholder);
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

	public void delete(View view) {
		confirmDelete(photo.getId(), this);
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
