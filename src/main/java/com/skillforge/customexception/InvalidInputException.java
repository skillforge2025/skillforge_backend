package com.skillforge.customexception;

public class InvalidInputException extends RuntimeException {
	public InvalidInputException(String msg) {
		super(msg);
	}
}
