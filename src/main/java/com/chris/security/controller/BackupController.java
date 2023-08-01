package com.chris.security.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.security.service.BackupService;

@RestController
@RequestMapping(value = "/backup")
public class BackupController {

	@Autowired
	private BackupService backupService;

	@GetMapping(value = "/{resource}/{deviceId}")
	public ResponseEntity<Object> findBackup(@PathVariable String resource, @PathVariable String deviceId) {
		return new ResponseEntity<>(backupService.findBackup(resource, deviceId), HttpStatus.OK);
	}

	@PostMapping(value = "/{resource}/{deviceId}")
	public ResponseEntity<Void> update(@PathVariable String resource, @PathVariable String deviceId,
			@RequestBody List<Map<Object, Object>> data) {
		backupService.update(resource, deviceId, data);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping(value = "/{resource}/{deviceId}")
	public ResponseEntity<Void> deleteAll(@PathVariable String resource, @PathVariable String deviceId) {
		backupService.deleteAll(resource, deviceId);
		return ResponseEntity.status(HttpStatus.GONE).build();
	}

	@DeleteMapping(value = "/{resource}/{deviceId}/{id}")
	public ResponseEntity<Void> delete(@PathVariable String resource, @PathVariable String deviceId,
			@PathVariable Long id) {
		backupService.delete(resource, deviceId, id);
		return ResponseEntity.status(HttpStatus.GONE).build();
	}
}