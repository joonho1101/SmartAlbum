package com.sa.db.layout.data;

import java.util.Date;

<<<<<<< HEAD
import android.location.Location;

/*
 * import org.genericdao.PrimaryKey;
 * 
 * @PrimaryKey("photoNumber")
 */
public class Photo implements Serializable {
	private static final long serialVersionUID = -1896979024004367149L;

	private int photoNumber;
	private String comment;
	private Byte[] actualPhoto;
	private Byte[] vocalComment;
	private Location location;
=======
public class Photo{
	
	private int photoNumber;
	private String comment;
	private byte[] actualPhoto;
	private byte[] vocalComment;
>>>>>>> Added Database of Photo (need to add more methods)
	private Date date;

	private String path;

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
	 * @param s
	 */
	public void setPath(String s) {
		this.path = s;
	}

	/**
	 * Gets path
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
<<<<<<< HEAD
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
	 * Save or update photo to persistent storage, database.
	 * Returns a boolean value indicating successful transaction.
	 * 
	 * @return whether successfully saved or not
	 */
	public boolean save() {
		// TODO
		return true;
	}

	/**
	 * Loads a photo instance from persistent storage, database.
	 * 
	 * @return
	 */
	public static Photo load(int id) {
		// TODO
		return new Photo();
=======
	 * Gets actual photo
	 * @return
	 */
	public byte[] getActualPhoto() {
		return actualPhoto;
	}

	/**
	 * Sets Actual photo
	 * @param actualPhoto
	 */
	public void setActualPhoto(byte[] actualPhoto) {
		this.actualPhoto = actualPhoto;
	}

	/**
	 * Get vocal comment
	 * @return
	 */
	public byte[] getVocalComment() {
		return vocalComment;
	}

	/**
	 * Sets vocal comment
	 * @param vocalComment
	 */
	public void setVocalComment(byte[] vocalComment) {
		this.vocalComment = vocalComment;
>>>>>>> Added Database of Photo (need to add more methods)
	}
}