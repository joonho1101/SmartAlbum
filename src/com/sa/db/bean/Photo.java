package com.sa.db.bean;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;

/**
 * Photo bean class that handles all of photo information
 * 
 * @author phillip
 * 
 */
public class Photo implements Media {

	private int id = 0;
	private String comment;
	private byte[] actualPhoto;
	private byte[] vocalComment;
	private float longitude;
	private float latitude;
	private String place;
	private Date date = new Date();

	/**
	 * Default constructor
	 */
	public Photo() {

	}

	public Photo(int id, String comment, byte[] actualPhoto,
			byte[] vocalComment, float longitude, float latitude, String place,
			Date date) {
		this.id = id;
		this.comment = comment;
		this.actualPhoto = actualPhoto;
		this.longitude = longitude;
		this.latitude = latitude;
		this.place = place;
		this.date = date;
		this.vocalComment = vocalComment;
	}

	/**
	 * Gets type of the media type (Photo or Video)
	 */
	@Override
	public int getType() {
		return TYPE_PHOTO;
	}

	/**
	 * Gets comment
	 * 
	 * @return
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * Sets comment
	 * 
	 * @param comment
	 */
	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets date
	 * 
	 * @return
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * Sets date
	 * 
	 * @param date
	 */
	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets photo number
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets photo number
	 * 
	 * @param photoNumber
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets actual photo
	 * 
	 * @return
	 */
	public byte[] getActualPhoto() {
		return actualPhoto;
	}

	/**
	 * Sets Actual photo
	 * 
	 * @param actualPhoto
	 */
	public void setActualPhoto(byte[] actualPhoto) {
		this.actualPhoto = actualPhoto;
	}

	/**
	 * Get vocal comment
	 * 
	 * @return
	 */
	public byte[] getVocalComment() {
		return vocalComment;
	}

	/**
	 * Sets vocal comment
	 * 
	 * @param vocalComment
	 */
	public void setVocalComment(byte[] vocalComment) {
		this.vocalComment = vocalComment;
	}

	/**
	 * Gets longitude
	 * 
	 * @return
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Sets longitude
	 * 
	 * @param longitude
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets latitude
	 * 
	 * @return
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Sets latitude
	 * 
	 * @param latitude
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * 
	 * 
	 * @return bitmap
	 */
	public Bitmap getBitmap() {
		return BitmapFactory
				.decodeByteArray(actualPhoto, 0, actualPhoto.length);
	}

	/**
	 * Sets byte array from bitmap
	 * 
	 * @param bitmap
	 */
	public void setBitmap(Bitmap bitmap) {
		actualPhoto = compressBitmap(bitmap);
	}

	/**
	 * Get vocal comment
	 * 
	 * @return
	 */
	public String getPlace() {
		return place == null ? getGeocodeString() : place;
	}

	public String getGeocodeString() {
		return "(" + latitude + "," + longitude + ")";
	}

	/**
	 * Sets place
	 * 
	 * @param place
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public Location getLocation() {
		Location loc = new Location(LocationManager.GPS_PROVIDER);
		loc.setLatitude(latitude);
		loc.setLongitude(getLongitude());
		return loc;
	}

	@Override
	public void setLocation(Location location) {
		if (location == null) {
			longitude = 0;
			latitude = 0;
		}
		else {
			longitude = (float) location.getLongitude();
			latitude = (float) location.getLatitude();
		}
	}

	public byte[] compressBitmap(Bitmap bmp) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}
}