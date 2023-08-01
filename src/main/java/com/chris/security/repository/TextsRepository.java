package com.chris.security.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chris.security.modal.Texts;

@Repository
public interface TextsRepository extends MongoRepository<Texts, String> {
	Optional<Texts> findTextsByDeviceId(String deviceId);

	void deleteByDeviceId(String deviceId);
}
