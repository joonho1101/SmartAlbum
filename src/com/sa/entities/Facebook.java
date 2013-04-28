package com.sa.entities;

import com.sa.db.layout.data.Media;
import android.content.Intent;

/**
 * Facebook interface that uploads, adds caption, dates, and location to
 * facebook
 * 
 * @author phillip
 * 
 */
public interface Facebook {

	// Uploads photo/video/audio to facebook. If success return true
	Intent createShareIntent(Media media);

}