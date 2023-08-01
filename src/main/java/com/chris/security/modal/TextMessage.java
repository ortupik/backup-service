package com.chris.security.modal;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("textMessages")
public class TextMessage implements Serializable {

	private Long id;
	private String message;
	private String inBound;
	private Date timeSent;

	public Long getId() {
		return id;
	}

	public String getInBound() {
		return inBound;
	}

	public String getMessage() {
		return message;
	}

	public Date getTimeSent() {
		return timeSent;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInBound(String inBound) {
		this.inBound = inBound;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}

}
