/**
 * SmartAlbum --
 * author - Phillip Huh(phuh) , Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * improved version of album that uses internal database to store necessary components of photos
 * such as voice, text memo, location, actual photo, etc
 */
package com.sa.activity;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.media.MediaPlayer;
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

/**
 * Detail Activity Class
 * 
 * @author Phillip Huh(phuh), Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * 
 */
public class DetailActivity extends BaseActivity {

	private Photo photo;
	private TextView captionView;
	private View caption_edit_wrapper;
	private TextView captionEditView;
	public int position;

	private MediaRecorder mr;
	private static final String TEMP_FILENAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp";
	boolean isRecordingVoice = false;

	private MediaPlayer mp;
	boolean mStartPlaying = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getPhotoFromIntent();
		initImageView();
		initCaptionView();
		initEditCaptionView();
		initLocationView();
	}


	/**
	 * Voice related methods
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */

	private boolean startRecording() {
		try {
			mr = new MediaRecorder();
			mr.setAudioSource(MediaRecorder.AudioSource.MIC);
			mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mr.setOutputFile(TEMP_FILENAME);
			mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mr.prepare();
			mr.start();
			return true;
		}
		catch (Exception e) {
			makeToast(getStringResource(R.string.audio_record_fail));
			return false;
		}
	}

	/**
	 * Stops recording
	 */
	private void stopRecording() {
		mr.stop();
		mr.release();
		mr = null;
	}

	/**
	 * Records click
	 * 
	 * @param v
	 */
	public void recordClick(View v) {
		if (isRecordingVoice) {
			stopRecording();
			isRecordingVoice = false;

			byte[] b = getBytesFromFile(TEMP_FILENAME);
			photo.setVocalComment(b);

			if (savePhoto(photo)) {
				makeToast(getStringResource(R.string.audio_save_success));
			}
			else {
				makeToast(getStringResource(R.string.audio_save_fail));
			}
		}
		else {
			isRecordingVoice = startRecording();
		}
		updateRecordButtonText(isRecordingVoice);
	}

	/**
	 * Updates record button
	 * 
	 * @param recording
	 */
	public void updateRecordButtonText(boolean recording) {
		Button button = (Button) findViewById(R.id.record_button);
		int id = recording ? R.string.stop_recording : R.string.start_recording;
		button.setText(getStringResource(id));
	}

	/**
	 * Gets array of byte from voice file since sqlite can only hold byte array.
	 * 
	 * @param filename
	 * @return
	 */
	public byte[] getBytesFromFile(String filename) {
		int bytesRead;
		try {
			FileInputStream is = new FileInputStream(filename);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			while ((bytesRead = is.read(b)) != -1) {
				bos.write(b, 0, bytesRead);
			}
			return bos.toByteArray();
		}
		catch (Exception e) {
			makeToast(getStringResource(R.string.audio_conversion_fail));
			return null;
		}
	}

	/**
	 * Gets voice file from byte array since we need to onvert in order to play
	 * 
	 * @param filename
	 * @param b
	 */
	public void getFileFromBytes(String filename, byte[] b) {
		try {
			FileOutputStream os = new FileOutputStream(filename);
			os.write(b);
			os.close();
		}
		catch (Exception e) {
			makeToast(getStringResource(R.string.audio_byte2audio_fail));
		}
	}

	/**
	 * Plays vocal comment
	 * 
	 * @param v
	 */
	public void playClick(View v) {
		int stringId;
		if (mStartPlaying) {
			startPlaying();
			stringId = R.string.stop_voice_memo;
		}
		else {
			stopPlaying();
			stringId = R.string.play_voice_memo;
		}
		((Button) findViewById(R.id.play_button)).setText(getStringResource(stringId));
		mStartPlaying = !mStartPlaying;
	}

	/**
	 * Starts playing the vocal sound
	 */
	private void startPlaying() {
		getFileFromBytes(TEMP_FILENAME, photo.getVocalComment());
		mp = new MediaPlayer();
		try {
			mp.setDataSource(TEMP_FILENAME);
			mp.prepare();
			mp.start();
		}
		catch (IOException e) {
			makeToast(getStringResource(R.string.play_audio_fail));
		}
	}

	/**
	 * Stops playing
	 */
	private void stopPlaying() {
		mp.release();
		mp = null;
	}

	/**
	 * Photo related methods
	 */

	/**
	 * Gets photo id from intent
	 */
	public void getPhotoFromIntent() {
		photo = getPhotoById(getIntent().getIntExtra("id", -1));
		position = getIntent().getIntExtra("position", -1);
	}

	/**
	 * Initilizes image view
	 */
	public void initImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.image);
		imageView.setImageBitmap(photo.getBitmap());
	}

	/**
	 * Initializes caption view
	 */
	public void initCaptionView() {
		captionView = (TextView) findViewById(R.id.caption);
		String comment = photo.getComment();
		if (comment == null || comment.length() < 1) {
			comment = getStringResource(R.string.caption_placeholder);
		}
		captionView.setText(comment);
	}

	/**
	 * Initializes edit caption view
	 */
	private void initEditCaptionView() {
		caption_edit_wrapper = findViewById(R.id.caption_edit_wrapper);
		captionEditView = (TextView) findViewById(R.id.caption_edit);
	}

	/**
	 * Shows edit caption
	 */
	private void showEditCaption() {
		captionView.setVisibility(View.GONE);
		caption_edit_wrapper.setVisibility(View.VISIBLE);
	}

	/**
	 * Hides edit caption when user does not want to edit caption
	 */
	private void hideEditCaption() {
		captionView.setVisibility(View.VISIBLE);
		caption_edit_wrapper.setVisibility(View.GONE);
	}

	/**
	 * Edits caption
	 * 
	 * @param view
	 */
	public void editCaption(View view) {
		showEditCaption();
		captionEditView.setText(photo.getComment());
	}

	/**
	 * Saves edit caption
	 * 
	 * @param view
	 */
	public void saveEditCaption(View view) {
		hideEditCaption();
		String text = captionEditView.getText().toString();
		photo.setComment(text);
		if (savePhoto(photo)) {
			captionView.setText(text);
			makeToast(getStringResource(R.string.comment_save_success));
		}
		else {
			makeToast(getStringResource(R.string.comment_save_fail));
		}
	}

	/**
	 * Cancels edit caption when user wants to cancel editing caption
	 * 
	 * @param view
	 */
	public void cancelEditCaption(View view) {
		hideEditCaption();
	}

	private void initLocationView() {
		TextView locationView = (TextView) findViewById(R.id.location);
		locationView.setText(photo.getPlace());
	}

	/**
	 * Share intent in order for users to share the photo
	 * 
	 * @param view
	 */
	public void share(View view) {
		startPhotoShareIntent();
	}

	/**
	 * Starts photo share intent in order to share the photo
	 */
	private void startPhotoShareIntent() {
		PhotoUploader uploader = new PhotoUploader();
		Intent share = uploader.createShareIntent(photo);
		startActivity(Intent.createChooser(share, getStringResource(R.string.share_via)));
	}

	/**
	 * Deletes the photo
	 * 
	 * @param view
	 */
	public void delete(View view) {
		confirmDelete(photo.getId(), this);
	}

	/*
	 * An intent to start ImageViewActivity
	 */
	public void startImageViewActivity(View view) {
		Intent intent = new Intent(this, ImageViewActivity.class);
		intent.putExtra("id", photo.getId());
		startActivity(intent);
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
