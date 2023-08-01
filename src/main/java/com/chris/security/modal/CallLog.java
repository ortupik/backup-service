package com.chris.security.modal;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("callLogs")
public class CallLog implements Serializable {

	private Long id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String callStatus;
	private String callDirection;
	private String timeCalled;
	private Long callDuration;

	public String getCallDirection() {
		return callDirection;
	}

	public Long getCallDuration() {
		return callDuration;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getTimeCalled() {
		return timeCalled;
	}

	public void setCallDirection(String callDirection) {
		this.callDirection = callDirection;
	}

	public void setCallDuration(Long callDuration) {
		this.callDuration = callDuration;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setTimeCalled(String timeCalled) {
		this.timeCalled = timeCalled;
	}

}
