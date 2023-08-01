package com.chris.security.modal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("textThreads")
public class TextThread implements Serializable {

	@Id
	private int id;
	private Date timeCreated;
	private Date lastBackup;
	private String receiverName;
	private String receiverPhone;
	private List<TextMessage> messages;

	public int getId() {
		return id;
	}

	public Date getLastBackup() {
		return lastBackup;
	}

	public List<TextMessage> getMessages() {
		return messages;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastBackup(Date lastBackup) {
		this.lastBackup = lastBackup;
	}

	public void setMessages(List<TextMessage> messages) {
		this.messages = messages;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

}
