package com.firstclub.core.controller;

import com.firstclub.core.dto.BenefitDTO;
import com.firstclub.core.dto.MembershipPlanDTO;
import com.firstclub.core.dto.MembershipTierDTO;
import com.firstclub.core.dto.SubscriptionDTO;
import com.firstclub.core.dto.subscriptionresponse.SubscriptionResponseDTO;
import com.firstclub.core.service.BenefitService;
import com.firstclub.core.service.MembershipService;
import com.firstclub.core.service.SubscriptionService;
import com.firstclub.core.service.TiersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.firstclub.core.constant.Constants.USER_ID;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping ("/v1/membership")
public class MembershipController implements MembershipControllerApi
{
    /*
    Please Note that since we didn't have login mechanism in place so we are generation UUID for users
    once login is there then we can take it from session and use for requests
     */

    BenefitService benefitService;

    TiersService tiersService;

    MembershipService membershipService;

    SubscriptionService subscriptionService;

    @Override
    public ResponseEntity<List<MembershipPlanDTO>> getAvailablePlans()
    {
        log.info("Received request to get all available plans");
        List<MembershipPlanDTO> plans = membershipService.getAllPlans();
        return ResponseEntity.ok(plans);
    }


    @Override
    public ResponseEntity<MembershipPlanDTO> addMembershipPlan( MembershipPlanDTO membershipPlanDTO )
    {
        log.info( "Received request to add membership plan" );

        var membershipDto = membershipService.addPlan( membershipPlanDTO );
        return ResponseEntity.status( HttpStatus.OK ).body( membershipDto );
    }


    @Override
    public ResponseEntity<List<MembershipTierDTO>> getAvailableTiers()
    {
        log.info( " Received request to fetch all available tiers " );

        var tiers = tiersService.getAllTiers( );
        return ResponseEntity.status( HttpStatus.OK ).body( tiers );

    }

    @Override
    public ResponseEntity<Void> addMembershipTier(MembershipTierDTO membershipTierDTO)
    {
        log.info( "Received request to add tiers and benefits" );
        tiersService.addTier( membershipTierDTO );
        return ResponseEntity.status( HttpStatus.OK  ).build();
    }


    @Override
    public ResponseEntity<SubscriptionResponseDTO> getUserSubscription( String userId )
    {
        MDC.put(USER_ID, userId);
        log.info("Controller: Received request to get subscription for user ");
        SubscriptionResponseDTO subscription = subscriptionService.getUserSubscription(userId);
        MDC.remove(USER_ID);
        return ResponseEntity.ok(subscription);
    }


    @Override
    public ResponseEntity<SubscriptionDTO> subscribeUser( SubscriptionDTO subscribeRequestDTO )
    {
        log.info("Controller: Received request to subscribe to plan ID: {}", subscribeRequestDTO.getMembershipPlan());

        SubscriptionDTO newSubscription = subscriptionService.subscribeUser(subscribeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSubscription);
    }


    @Override
    public ResponseEntity<SubscriptionDTO> changeSubscription( String userId, SubscriptionDTO subscribeRequestDTO )
    {
        MDC.put(USER_ID, userId);
        if (subscribeRequestDTO.getMembershipPlan() == null || subscribeRequestDTO.getMembershipPlan().isBlank()) {
            log.warn("Controller: New Plan ID is missing in the request DTO for user");
        }
        String newPlanId = subscribeRequestDTO.getMembershipPlan();
        log.info("Controller: Received request to change subscription for user to new plan ID: {}", newPlanId);

        SubscriptionDTO updatedSubscription = subscriptionService.changeSubscription(userId, newPlanId);
        MDC.remove( USER_ID );
        return ResponseEntity.ok(updatedSubscription);
    }


    @Override
    public ResponseEntity<Void> cancelSubscription( String userId )
    {
        MDC.put(USER_ID, userId);
        log.info("Controller: Received request to cancel subscription for user ID: {}", userId);
        subscriptionService.cancelSubscription(userId);
        MDC.remove( USER_ID );
        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<Set<BenefitDTO>> getUserBenefits( Long userId )
    {
        log.warn("getUserBenefits not yet implemented in controller for user: {}", userId);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


    @Override
    public ResponseEntity<Set<BenefitDTO>> getAllBenefits()
    {
        log.info(  "Received request for fetching all benefits " );

        Set<BenefitDTO> benefits = benefitService.getAllBenefits( );
        return ResponseEntity.status( HttpStatus.OK ).body( benefits );
    }


    @Override
    public ResponseEntity<Void> addBenefits( BenefitDTO benefitDTO )
    {
        log.info( "Received request for adding new benefits for users" );

        benefitService.addBenefit( benefitDTO );
        return ResponseEntity.status( HttpStatus.OK  ).build();
    }
}
