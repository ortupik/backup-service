package com.chris.security.modal;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("texts")
public class Texts implements Serializable {

	@Id
	private String id;
	private String deviceId;
	private List<TextThread> textThreads;

	public String getDeviceId() {
		return deviceId;
	}

	public String getId() {
		return id;
	}

	public List<TextThread> getTextThreads() {
		return textThreads;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTextThreads(List<TextThread> textThreads) {
		this.textThreads = textThreads;
	}

}
