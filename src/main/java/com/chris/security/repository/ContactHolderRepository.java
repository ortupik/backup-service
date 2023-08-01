package com.chris.security.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chris.security.modal.ContactHolder;

@Repository
public interface ContactHolderRepository extends MongoRepository<ContactHolder, String> {
	Optional<ContactHolder> findContactHolderByDeviceId(String deviceId);

	void deleteByDeviceId(String deviceId);
}
