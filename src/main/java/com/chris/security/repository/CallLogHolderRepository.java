package com.chris.security.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chris.security.modal.CallLogHolder;

@Repository
public interface CallLogHolderRepository extends MongoRepository<CallLogHolder, String> {
	Optional<CallLogHolder> findCallLogHolderByDeviceId(String deviceId);

	void deleteByDeviceId(String deviceId);
}
