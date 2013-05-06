/**
 * SmartAlbum --
 * author - Phillip Huh(phuh) , Joon Ho Cho(joonhoc), Isaac Simha(isimha)
 * improved version of album that uses internal database to store necessary components of photos
 * such as voice, text memo, location, actual photo, etc
 */
package com.sa.db.bean;

import java.util.Date;

import android.location.Location;

/**
 * Media interface that handles media related parts
 * @author phillip Huh(phuh), Joon Ho Cho (joonhoc), Isaac Simha(isimha)
 *
 */
public interface Media {
	public static final int TYPE_PHOTO = 1;

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