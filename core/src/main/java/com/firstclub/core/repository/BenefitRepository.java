package com.firstclub.core.repository;

import com.firstclub.core.entity.Benefit;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BenefitRepository extends MongoRepository<Benefit, String>
{
}
