package com.sa.db.layout.data;

import java.io.ByteArrayOutputStream;
import java.util.Date;

<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;

public class Photo implements Media {
=======
public class Photo {
>>>>>>> DatabaseHandler, and Photo are added or modified.

	private int id;
	private String comment;
	private byte[] actualPhoto;
	private byte[] vocalComment;
	private float longitude;
	private float altitude;
	private String place;
	private Date date;
<<<<<<< HEAD
	private String path;
=======

	public Photo(int id, String comment, byte[] actualPhoto,
			byte[] vocalComment, float longitude, float altitude, String place,
			Date date) {
		this.id = id;
		this.comment = comment;
		this.actualPhoto = actualPhoto;
		this.longitude = longitude;
		this.altitude = altitude;
		this.place = place;
		this.date = date;
	}
>>>>>>> DatabaseHandler, and Photo are added or modified.

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
	 * Gets longtitude
	 * 
	 * @return
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Sets longtitude
	 * 
	 * @param longitude
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
<<<<<<< HEAD
	 * Returns image byte array
	 * 
	 * @return
	 */
	public byte[] getActualPhoto() {
		return actualPhoto;
	}

	/**
	 * Sets image byte array
=======
	 * Gets altitude
	 * 
	 * @return
	 */
	public float getAltitude() {
		return altitude;
	}

	/**
	 * Sets altitude
>>>>>>> DatabaseHandler, and Photo are added or modified.
	 * 
	 * @param altitude
	 */
	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	/**
<<<<<<< HEAD
	 * Returns bitmap from byte array.
	 * 
	 * @return bitmap
	 */
	public Bitmap getBitmap() {
		return BitmapFactory.decodeByteArray(actualPhoto, 0, actualPhoto.length);
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
=======
	 * Gets place
>>>>>>> DatabaseHandler, and Photo are added or modified.
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
}