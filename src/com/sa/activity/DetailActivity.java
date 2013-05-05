package com.sa.activity;

import java.io.IOException;

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
		makeToast("start recording");
		mr = new MediaRecorder();
		makeToast("mr created");
		try{
			mr.setAudioSource(MediaRecorder.AudioSource.MIC);
		}
		catch(Exception e){
			makeToast("1");
		}
		try{
			mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		}
		catch(Exception e){
			makeToast("2");
		}

        try{
        	mr.setOutputFile(filename);
        }
		catch(Exception e){
			makeToast("3  " + e.getMessage());
		}
		try{
			mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		}
		catch(Exception e){
			makeToast("4  " + e.getMessage());
		}
		try {
			mr.prepare();
		}
		catch (IllegalStateException e) {
			makeToast("5  " + e.getMessage());
		}
		catch (IOException e) {
			makeToast("6  " + e.getMessage());
		}

        try{
        	mr.start();
        }
        catch(Exception e){
			makeToast("7  " + e.getMessage());
        }
	}
	
    private void stopRecording() {
        mr.stop();
        mr.release();
        mr = null;
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
