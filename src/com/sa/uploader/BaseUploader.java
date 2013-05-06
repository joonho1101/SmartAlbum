/**
 * SmartAlbum --
 * author - Phillip Huh(phuh) , Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * improved version of album that uses internal database to store necessary components of photos
 * such as voice, text memo, location, actual photo, etc
 */
package com.sa.uploader;

import java.util.Date;

import com.sa.db.bean.Media;

import android.content.Intent;
import android.location.Location;

/**
 * BaseUploader class that uploads to Facebook or any place that user wants to
 * 
 * @author Phillip Huh(phuh), Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * 
 */
public abstract class BaseUploader implements Facebook {

	protected Intent sharingIntent;

	public void addInfo(Media media) {
		addCaption(media.getComment());
		addDate(media.getDate());
		addLocation(media.getLocation());
	}

	/**
	 * Adds caption
	 * @param s
	 * @return
	 */
	public boolean addCaption(String s) {
		if (s == null) {
			return false;
		}
		String text = sharingIntent.getStringExtra(Intent.EXTRA_TEXT);
		String fulltext = "";
		if (text != null) {
			fulltext += text;
		}
		fulltext += "\n" + s;
		sharingIntent.putExtra(Intent.EXTRA_TEXT, fulltext);
		return true;
	}

	/**
	 * Adds date
	 * @param d
	 * @return
	 */
	public boolean addDate(Date d) {
		if (d == null) {
			return false;
		}
		String text = sharingIntent.getStringExtra(Intent.EXTRA_TEXT);
		String fulltext = "";
		if (text != null) {
			fulltext += text;
		}
		fulltext += "\n" + d.toString();
		sharingIntent.putExtra(Intent.EXTRA_TEXT, fulltext);

		return true;
	}

	/**
	 * Adds locations
	 * @param l
	 * @return
	 */
	public boolean addLocation(Location l) {
		if (l == null) {
			return false;
		}
		String text = sharingIntent.getStringExtra(Intent.EXTRA_TEXT);
		String fulltext = "";
		if (text != null) {
			fulltext += text;
		}
		fulltext += "\n(" + l.getLatitude() + ", " + l.getLongitude() + ")";
		sharingIntent.putExtra(Intent.EXTRA_TEXT, fulltext);

		return true;
	}
}
