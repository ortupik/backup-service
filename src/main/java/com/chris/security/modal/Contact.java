package com.chris.security.modal;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("contacts")
public class Contact implements Serializable {

	private Long id;
	private String firstName;
	private String lastName;
	private String phoneNumber;

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

}
