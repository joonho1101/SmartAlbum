package com.sa.entities;

import java.io.File;
import java.util.Date;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;

public class PhotoUploader implements Facebook {

	private Intent sharingIntent;

	@Override
	public Intent upload(File file, String s, Date d, Location l) {
		sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("image/png");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		addCaption(s);
		addDate(d);
		addLocation(l);
		// startActivity(Intent.createChooser(sharingIntent , "Share via..."));
		return sharingIntent;
	}

	@Override
	public boolean addCaption(String s) {
		String text = sharingIntent.getStringExtra(Intent.EXTRA_TEXT);
		String fulltext = "";
		if (text != null) {
			fulltext += text;
		}
		fulltext += "\n" + s;
		sharingIntent.putExtra(Intent.EXTRA_TEXT, fulltext);
		return true;
	}

	@Override
	public boolean addDate(Date d) {
		String text = sharingIntent.getStringExtra(Intent.EXTRA_TEXT);
		String fulltext = "";
		if (text != null) {
			fulltext += text;
		}
		fulltext += "\n" + d.toString();
		sharingIntent.putExtra(Intent.EXTRA_TEXT, fulltext);

		return true;
	}

	@Override
	public boolean addLocation(Location l) {
		String text = sharingIntent.getStringExtra(Intent.EXTRA_TEXT);
		String fulltext = "";
		if (text != null) {
			fulltext += text;
		}
		fulltext += "\n" + l.toString();
		sharingIntent.putExtra(Intent.EXTRA_TEXT, fulltext);

		return true;
	}

}
