package com.chris.security.exceptions;

import java.sql.Timestamp;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -7946423438666396082L;
	public static final Logger log = LoggerFactory.getLogger(BadRequestException.class.getName());

	Timestamp time;
	String message;

	public BadRequestException(String message) {
		super();
		this.time = Timestamp.from(Instant.now());
		this.message = message;
		log.debug(this.time + " : " + message, getStackTrace(), null);
	}

}
