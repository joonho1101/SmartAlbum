package com.sa.entities;

import java.io.File;
import java.util.Date;

import android.content.Intent;
import android.location.Location;

/**
 * Facebook interface that uploads, adds caption, dates, and location to
 * facebook
 * 
 * @author phillip
 * 
 */
public interface Facebook {

	Intent upload(File file, String s, Date d, Location l);

	// Uploads photo/video/audio to facebook. If success return true
	boolean addCaption(String s);

	// Uploads caption to facebook. If success return true

	boolean addDate(Date d);

	// Uploads date to facebook. If success return true

	boolean addLocation(Location l);
	// Uploads location to facebook. If success return true

}