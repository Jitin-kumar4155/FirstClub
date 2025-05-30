package com.firstclub.core.repository;

import com.firstclub.core.dto.SubscriptionDTO;
import com.firstclub.core.entity.Subscription;
import com.firstclub.core.enums.Status;
import org.mapstruct.Mapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    Optional<Subscription> findByUserIdAndStatus(String userId, Status status);

    Optional<Subscription> findByUserId( String userId );

    List<Subscription> findAllByUserId(String userId);

    Optional<Subscription> findTopByUserIdAndStatusInOrderByExpiryDateDesc(String userId, List<Status> statuses);

}
