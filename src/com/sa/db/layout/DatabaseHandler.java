package com.sa.db.layout;

import android.content.ContentValues;
import android.content.Context;
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
	private static final String PHOTO_NUMBER = "photoNumber";
	private static final String COMMENT = "comment";
	private static final String ACTUAL_PHOTO = "actualPhoto";
	private static final String VOCAL_COMMENT = "vocalComment";
	private static final String DATE = "date";

	/*
	 * private int photoNumber; private String comment; private Byte[]
	 * actualPhoto; private Byte[] vocalComment; private Date date;
	 */
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PHOTOS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ PHOTO_NUMBER + " INTEGER PRIMARY KEY," + COMMENT + " TEXT,"
				+ ACTUAL_PHOTO + " BLOB," + VOCAL_COMMENT + "BLOB," + DATE
				+ " TEXT" + ")";

		db.execSQL(CREATE_PHOTOS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}
	
	
	public void addPhoto(Photo photo){
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(COMMENT, photo.getComment());
		values.put(ACTUAL_PHOTO, photo.getActualPhoto());
		values.put(VOCAL_COMMENT,photo.getVocalComment());
		values.put(DATE, photo.getDate().getTime()+"");
		db.insert(DATABASE_NAME, null, values);
		db.close();
	}
}