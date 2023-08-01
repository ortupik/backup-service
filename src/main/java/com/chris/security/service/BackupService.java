package com.chris.security.service;

import java.util.List;
import java.util.Map;

public interface BackupService {
	Object findBackup(String resource, String deviceId);

	void update(String resource, String deviceId, List<Map<Object, Object>> data);

	void deleteAll(String resource, String deviceId);

	void delete(String resource, String deviceId, Long id);
}
