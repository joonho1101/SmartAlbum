package com.sa.entities;

import java.util.Date;
import com.sa.db.layout.data.Media;
import android.content.Intent;
import android.location.Location;

public abstract class BaseUploader implements Facebook {

	private Intent sharingIntent;

	public void addInfo(Media media) {
		addCaption(media.getComment());
		addDate(media.getDate());
		addLocation(media.getLocation());
	}

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
