package com.sa.db.layout.data;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;

public class Photo implements Media {

	private int id;
	private String comment;
	private byte[] actualPhoto;
	private byte[] vocalComment;
	private float longitude;
	private float latitude;
	private String place;
	private Date date;

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
	}

	public int getType() {
		return TYPE_PHOTO;
	}

	/**
	 * Gets comment
	 * 
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets comment
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets date
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets date
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets photo number
	 * 
	 * @return
	 */
	public int getPhotoNumber() {
		return id;
	}

	/**
	 * Sets photo number
	 * 
	 * @param photoNumber
	 */
	public void setPhotoNumber(int photoNumber) {
		this.id = photoNumber;
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
		ByteArrayOutputStream blob = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, blob);
		actualPhoto = blob.toByteArray();
	}

	/**
	 * Get vocal comment
	 * 
	 * @return
	 */
	public String getPlace() {
		return place;
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
		longitude = (float) location.getLongitude();
		latitude = (float) location.getLatitude();
	}

}