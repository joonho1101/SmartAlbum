package com.sa.db.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.sa.db.bean.Photo;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "PhotoManager";

	// Contacts table name
	private static final String TABLE_PHOTOS = "photos";

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
		String CREATE_PHOTOS_TABLE =
				"CREATE TABLE " + TABLE_PHOTOS + "(" + ID + " INTEGER PRIMARY KEY," + COMMENT + " TEXT," + ACTUAL_PHOTO
						+ " BLOB," + VOCAL_COMMENT + " BLOB," + LONGITUDE + " REAL," + LATITUDE + " REAL," + PLACE
						+ " TEXT," + DATE + " TEXT" + ")";
		
		db.execSQL(CREATE_PHOTOS_TABLE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Adds photo to the database.
	 * 
	 * @param photo
	 * @return row id or -1 if there is an error.
	 */
	public long addPhoto(Photo photo) {
		SQLiteDatabase db = this.getReadableDatabase();

		ContentValues values = new ContentValues();

		values.put(COMMENT, photo.getComment());
		values.put(ACTUAL_PHOTO, photo.getActualPhoto());
		values.put(VOCAL_COMMENT, photo.getVocalComment());
		values.put(LONGITUDE, photo.getLongitude());
		values.put(LATITUDE, photo.getLatitude());
		values.put(PLACE, photo.getPlace());
		values.put(DATE, photo.getDate().getTime() + "");

		long insertedPos = db.insert(TABLE_PHOTOS, null, values);
		db.close();
		return insertedPos;
	}

	/**
	 * Updates Photo given photo
	 * 
	 * @param photo
	 * @return numbers of rows effected
	 */
	public long updatePhoto(Photo photo) {
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues values = new ContentValues();

		values.put(COMMENT, photo.getComment());
		values.put(ACTUAL_PHOTO, photo.getActualPhoto());
		values.put(VOCAL_COMMENT, photo.getVocalComment());
		values.put(LONGITUDE, photo.getLongitude());
		values.put(LATITUDE, photo.getLatitude());
		values.put(PLACE, photo.getPlace());
		values.put(DATE, photo.getDate().getTime() + "");
		return db.update(TABLE_PHOTOS, values, ID + " = ?", new String[] { String.valueOf(photo.getId()) });
	}

	/**
	 * Gets photo given id
	 * 
	 * @param id
	 * @return photo
	 */
	public Photo getPhoto(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =
				db.query(TABLE_PHOTOS, new String[] { ID, COMMENT, ACTUAL_PHOTO, VOCAL_COMMENT, LONGITUDE, LATITUDE,
						PLACE, DATE }, ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Photo photo =
				new Photo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getBlob(2),
						cursor.getBlob(3), cursor.getFloat(4), cursor.getFloat(5), cursor.getString(6), new Date(
								cursor.getString(7)));
		return photo;
	}

	/**
	 * Deletes photo given id
	 * 
	 * @param id
	 * @return number of rows effected
	 */
	public long deletePhoto(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		long rowsAffected = db.delete(TABLE_PHOTOS, ID + " = ?", new String[] { String.valueOf(id) });
		db.close();
		return rowsAffected;
	}

	/**
	 * Gets all of photos from the database
	 * 
	 * @return
	 */
	public List<Photo> getAllPhotos() {
		List<Photo> photos = new ArrayList<Photo>();
		String selectQuery = "SELECT  * FROM " + TABLE_PHOTOS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Photo photo =
						new Photo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getBlob(2),
								cursor.getBlob(3), cursor.getFloat(4), cursor.getFloat(5), cursor.getString(6),
								new Date(cursor.getString(7)));
				photos.add(photo);
			}
			while (cursor.moveToNext());
		}
		return photos;
	}
}