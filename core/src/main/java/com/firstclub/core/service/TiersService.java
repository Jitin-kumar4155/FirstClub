package com.firstclub.core.service;

import com.firstclub.core.dto.MembershipTierDTO;
import com.firstclub.core.exception.ResourceNotFoundException;
import com.firstclub.core.mapper.TiersMapper;
import com.firstclub.core.repository.TiersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class TiersService
{

    TiersRepository tiersRepository;

    public void addTier( MembershipTierDTO membershipTierDTO )
    {
        try{
            log.debug( "Received request to add tiers and benefits" );

            var tier = TiersMapper.INSTANCE.toEntity( membershipTierDTO );
            tiersRepository.insert( tier );
            log.info( "Successfully inserted new tier" );
        }
        catch (Exception e)
        {
            log.error( "Error while adding tier: {} with message:{}", membershipTierDTO, ExceptionUtils.getStackTrace(e) );
        }
    }

    public List<MembershipTierDTO> getAllTiers(){
        try{
            log.debug(  "Received request to get all tiers " );
            var tiers = tiersRepository.findAll();

            if( CollectionUtils.isEmpty( tiers ) ){
                throw new ResourceNotFoundException( "No Tiers found" );
            }

            return TiersMapper.INSTANCE.toMembershipTierDTOList( tiers );

        }catch (Exception e){
            log.error("Error while fetching all tiers: {}", ExceptionUtils.getStackTrace(e));
            return List.of();
        }
    }
}
