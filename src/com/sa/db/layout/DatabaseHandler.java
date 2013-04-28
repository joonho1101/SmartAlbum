package com.sa.db.layout;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sa.db.layout.data.Photo;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "PhotoManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "photos";

	// Contacts Table Columns names
	private static final String ID = "id";
	private static final String COMMENT = "comment";
	private static final String ACTUAL_PHOTO = "actualPhoto";
	private static final String VOCAL_COMMENT = "vocalComment";
	private static final String LONGITUDE = "longitude";
	private static final String LATITUDE = "latitude";
	private static final String PLACE = "place";
	private static final String DATE = "date";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PHOTOS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + ID + " INTEGER PRIMARY KEY," + COMMENT + " TEXT," + ACTUAL_PHOTO + " BLOB," + VOCAL_COMMENT + "BLOB," + LONGITUDE + " REAL," + LATITUDE + " REAL," + PLACE + " TEXT," + DATE + " TEXT" + ")";

		db.execSQL(CREATE_PHOTOS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Given photo, it adds the photo to the data base
	 * 
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		SQLiteDatabase db = this.getReadableDatabase();

		ContentValues values = new ContentValues();

		values.put(COMMENT, photo.getComment());
		values.put(ACTUAL_PHOTO, photo.getActualPhoto());
		values.put(VOCAL_COMMENT, photo.getVocalComment());
		values.put(LONGITUDE, photo.getLongitude());
		values.put(LATITUDE, photo.getLatitude());
		values.put(PLACE, photo.getPlace());
		values.put(DATE, photo.getDate().getTime() + "");

		db.insert(DATABASE_NAME, null, values);
		db.close();
	}

	/**
	 * Gets photo given id
	 * 
	 * @param id
	 * @return photo
	 */
	public Photo getPhoto(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { ID, COMMENT, ACTUAL_PHOTO, VOCAL_COMMENT, LONGITUDE, LATITUDE, PLACE, DATE }, ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Photo photo = new Photo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getFloat(4), cursor.getFloat(5), cursor.getString(6), new Date(cursor.getString(7)));
		return photo;
	}

	/**
	 * Deletes the photo with given id
	 * 
	 * @param id
	 */
	public void deletePhoto(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, ID + " = ?", new String[] { String.valueOf(id) });
		db.close();
	}

	/*
	 * public List<Photo> getAllPhotos(){
	 * List<Photo> photos = new ArrayList<Photo>();
	 * // Select All Query
	 * String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	 * SQLiteDatabase db = this.getWritableDatabase();
	 * Cursor cursor = db.rawQuery(selectQuery, null);
	 * 
	 * // looping through all rows and adding to list
	 * if (cursor.moveToFirst()) {
	 * do {
	 * Photo contact = new Photo();
	 * // contact.setID(Integer.parseInt(cursor.getString(0)));
	 * // contact.setName(cursor.getString(1));
	 * //contact.setPhoneNumber(cursor.getString(2));
	 * // Adding contact to list
	 * //contactList.add(contact);
	 * } while (cursor.moveToNext());
	 * }
	 * }
	 */
}