package com.sa.db.layout.data;

import java.io.Serializable;
import java.sql.Date;

/*
 import org.genericdao.PrimaryKey;

 @PrimaryKey("photoNumber")
 */
public class Photo implements Serializable {
	private static final long serialVersionUID = -1896979024004367149L;
	
	private int photoNumber;
	private String comment;
	private Byte[] actualPhoto;
	private Byte[] vocalComment;
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
	 * Gets Actual Photo
	 * 
	 * @return
	 */
	public Byte[] getActualPhoto() {
		return actualPhoto;
	}

	/**
	 * Sets Actual Photo
	 * 
	 * @param actualPhoto
	 */
	public void setActualPhoto(Byte[] actualPhoto) {
		this.actualPhoto = actualPhoto;
	}

	/**
	 * Gets Vocal Comment
	 * 
	 * @return
	 */
	public Byte[] getVocalComment() {
		return vocalComment;
	}

	/**
	 * Sets Vocal Comment
	 * 
	 * @param vocalComment
	 */
	public void setVocalComment(Byte[] vocalComment) {
		this.vocalComment = vocalComment;
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

	public void setPath(String s) {
		this.path = s;
	}

	public String getPath() {
		return path;
	}

}