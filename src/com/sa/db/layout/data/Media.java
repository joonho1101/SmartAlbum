package com.sa.db.layout.data;

import java.util.Date;

import android.location.Location;

public interface Media {
	public static final int TYPE_PHOTO = 1;
	public static final int TYPE_VIDEO = 2;

	public int getType();

	public String getComment();

	/**
	 * Sets comment
	 * 
	 * @param comment
	 */
	public void setComment(String comment);

	/**
	 * Gets date
	 * 
	 * @return
	 */
	public Date getDate();

	/**
	 * Sets date
	 * 
	 * @param date
	 */
	public void setDate(Date date);

	/**
	 * Returns location stored on the photo.
	 * 
	 * @return location
	 */
	public Location getLocation();

	/**
	 * Sets location for a photo
	 * 
	 * @param location
	 */
	public void setLocation(Location location);

}
