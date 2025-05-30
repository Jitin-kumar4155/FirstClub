package com.firstclub.core.repository;

import com.firstclub.core.entity.Tiers;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TiersRepository extends MongoRepository<Tiers, String>
{
}
