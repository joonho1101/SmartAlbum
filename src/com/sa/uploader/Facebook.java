/**
 * SmartAlbum --
 * author - Phillip Huh(phuh) , Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * improved version of album that uses internal database to store necessary components of photos
 * such as voice, text memo, location, actual photo, etc
 */
package com.sa.uploader;

import com.sa.db.bean.Media;

import android.content.Intent;

/**
 * Facebook interface that uploads, adds caption, dates, and location to
 * facebook
 * 
 * @author Phillip Huh(phuh), Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * 
 */
public interface Facebook {

	// Uploads photo/video/audio to facebook. If success return true
	Intent createShareIntent(Media media);

}