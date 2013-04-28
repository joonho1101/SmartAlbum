package com.sa.db.layout.data;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;

public class Photo implements Media {

	private int photoNumber;
	private String comment;
	private byte[] actualPhoto;
	private byte[] vocalComment;
	private Location location;
	private Date date;
	private String path;

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
		return photoNumber;
	}

	/**
	 * Sets photo number
	 * 
	 * @param photoNumber
	 */
	public void setPhotoNumber(int photoNumber) {
		this.photoNumber = photoNumber;
	}

	/**
	 * Sets path
	 * 
	 * @param s
	 */
	public void setPath(String s) {
		this.path = s;
	}

	/**
	 * Gets path
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns location stored on the photo.
	 * 
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets location for a photo
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Save or update photo to persistent storage, database. Returns a boolean
	 * value indicating successful transaction.
	 * 
	 * @return whether successfully saved or not
	 */
	public boolean save() {
		return true;
	}

	/**
	 * Loads a photo instance from persistent storage, database.
	 * 
	 * @return
	 */
	public static Photo load(int id) {
		return new Photo();
	}

	/**
	 * Returns image byte array
	 * 
	 * @return
	 */
	public byte[] getActualPhoto() {
		return actualPhoto;
	}

	/**
	 * Sets image byte array
	 * 
	 * @param actualPhoto
	 */
	public void setActualPhoto(byte[] actualPhoto) {
		this.actualPhoto = actualPhoto;
	}

	/**
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
}