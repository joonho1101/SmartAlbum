package com.sa.entities;

import java.io.File;
import java.util.Date;

import android.content.Intent;
import android.location.Location;

public class FileUploader implements Facebook {

	@Override
	public Intent upload(File file, String s, Date d, Location l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCaption(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addDate(Date d) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addLocation(Location l) {
		// TODO Auto-generated method stub
		return false;
	}

}
