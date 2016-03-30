package com.me.Activity.util;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadListener implements ProgressListener {

	private long red;
	private long length = -1;
	private boolean known = false;
	private double percent = 0.0;

	public void update(long pBytesRead, long pContentLength, int pItems) {
		// TODO Auto-generated method stub
		if (pContentLength > -1)
			known = true;
		red = pBytesRead;
		length = pContentLength;

		if (known && length != 0) {
			percent = (red + 0.0) / length;
		}

	}

	public double getPercent() {
		return percent;
	}

	public double getPercentDone() {
		return percent * 100;
	}

}
