package com.chris.security.modal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("contactHolder")
public class ContactHolder implements Serializable {

	@Id
	private String id;
	private String deviceId;
	private Date lastBackup;
	private List<Contact> contacts;

	public List<Contact> getContacts() {
		return contacts;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getId() {
		return id;
	}

	public Date getLastBackup() {
		return lastBackup;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastBackup(Date date) {
		this.lastBackup = date;
	}

}
