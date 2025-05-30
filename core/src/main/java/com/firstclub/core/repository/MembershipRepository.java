package com.firstclub.core.repository;

import com.firstclub.core.entity.Membership;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MembershipRepository extends MongoRepository<Membership, String>
{
}
