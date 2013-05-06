/**
 * SmartAlbum --
 * author - Phillip Huh(phuh) , Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * improved version of album that uses internal database to store necessary components of photos
 * such as voice, text memo, location, actual photo, etc
 */
package com.sa.activity;

import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import com.sa.db.bean.Photo;
import com.sa.smartalbum.R;

/**
 * Detail Activity Class
 * 
 * @author Phillip Huh(phuh), Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * 
 */
public class ImageViewActivity extends BaseActivity {

	private static final String TEMP_FILENAME = "file://" + BASE_PATH + "/tmp.jpg";
	Photo photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPhotoFromIntent();
		initWebView();
	}

	private boolean savePhotoFile(String filename, Bitmap bmp) {
		try {
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filename));
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	private boolean savePhotoAsFile(Photo photo) {
		return savePhotoFile(TEMP_FILENAME, photo.getBitmap());
	}

	/**
	 * Gets photo id from intent
	 */
	public void getPhotoFromIntent() {
		photo = getPhotoById(getIntent().getIntExtra("id", -1));
	}

	/**
	 * Initilizes image view
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public void initWebView() {
		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		savePhotoAsFile(photo);
		webView.loadUrl(TEMP_FILENAME);
	}


	@Override
	int getMenuId() {
		return R.menu.image_view;
	}

	@Override
	int getLayoutId() {
		return R.layout.activity_image_view;
	}
}
