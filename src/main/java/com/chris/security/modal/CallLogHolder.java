package com.chris.security.modal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("callLogHolder")
public class CallLogHolder implements Serializable {

	@Id
	private String id;
	private String deviceId;
	private Date lastBackup;
	private List<CallLog> callLogs;

	public List<CallLog> getCallLogs() {
		return callLogs;
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

	public void setCallLogs(List<CallLog> callLogs) {
		this.callLogs = callLogs;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastBackup(Date lastBackup) {
		this.lastBackup = lastBackup;
	}

}
