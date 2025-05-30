package com.firstclub.core.service;

import com.firstclub.core.dto.BenefitDTO;
import com.firstclub.core.entity.Benefit;
import com.firstclub.core.mapper.BenefitMapper;
import com.firstclub.core.repository.BenefitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@AllArgsConstructor
@Service
public class BenefitService
{

    BenefitRepository benefitRepository;

    MongoTemplate mongoTemplate;

    public Benefit findById(String id, String... includes){

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        if ( ObjectUtils.isNotEmpty(includes)) {
            Arrays.stream(includes).forEach(s -> query.fields().include(s));
        }

        return mongoTemplate.findOne( query, Benefit.class );
    }


    public void addBenefit( BenefitDTO benefitDTO )
    {
        try{
            log.debug( "Received request to add new benefit" );
            var benefit = BenefitMapper.INSTANCE.toBenefitEntity(  benefitDTO );

            if(log.isTraceEnabled()){
                log.trace( "Adding new benefit as: {}", benefit );
            }
            benefitRepository.insert( benefit );
        }
        catch (Exception e){
            log.error( "Failed to add new benefit with error :{}", ExceptionUtils.getStackTrace(e) );
        }

    }


    public Set<BenefitDTO> getAllBenefits()
    {
        try{
            log.debug( "Received request to find all benefits" );

            var benefits = benefitRepository.findAll();

            return BenefitMapper.INSTANCE.toBenefitDTOSet(  benefits );

        }
        catch (Exception e){
            log.error( "Failed to find all with error :{}", ExceptionUtils.getStackTrace(e) );
            return null;
        }
    }
}
