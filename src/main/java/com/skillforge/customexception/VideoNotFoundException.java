package com.skillforge.customexception;

public class VideoNotFoundException extends RuntimeException {
	public VideoNotFoundException(String msg) {
		super(msg);
	}
}
