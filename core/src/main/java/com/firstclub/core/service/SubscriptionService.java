package com.firstclub.core.service;

import com.firstclub.core.dto.MembershipTierDTO;
import com.firstclub.core.dto.SubscriptionDTO;
import com.firstclub.core.dto.subscriptionresponse.SubscriptionResponseDTO;
import com.firstclub.core.entity.Membership;
import com.firstclub.core.entity.Subscription;
import com.firstclub.core.enums.MembershipPlan;
import com.firstclub.core.enums.Status;
import com.firstclub.core.exception.ResourceNotFoundException;
import com.firstclub.core.helper.DbHelper;
import com.firstclub.core.mapper.SubscriptionMapper;
import com.firstclub.core.mapper.TiersMapper;
import com.firstclub.core.repository.BenefitRepository;
import com.firstclub.core.repository.MembershipRepository;
import com.firstclub.core.repository.SubscriptionRepository;
import com.firstclub.core.repository.TiersRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SubscriptionService
{

    SubscriptionRepository subscriptionRepository;

    MembershipRepository membershipRepository;

    TiersRepository tiersRepository;

    MongoTemplate mongoTemplate;

    BenefitRepository benefitRepository;

    @Transactional (readOnly = true)
    public SubscriptionResponseDTO getUserSubscription(String userId) {
        log.debug("Fetching active subscription for user ");

        Optional<Subscription> activeSubscriptionOpt = subscriptionRepository
            .findByUserIdAndStatus(userId, Status.ACTIVE);

        if (activeSubscriptionOpt.isEmpty()) {
            log.warn("No active subscription found for user");
            throw new ResourceNotFoundException("No active subscription found for user: " + userId);
        }

        Subscription subscription = activeSubscriptionOpt.get();

        var membership = membershipRepository.findById(subscription.getPlanId())
            .orElseThrow(() -> new ResourceNotFoundException("Membership Plan not found for ID: " + subscription.getPlanId()));

        var tierDTO = fetchTierDetails(membership.getTier().get(0));

        var benefitDTO = benefitRepository.findById( tierDTO.getBenefits().get( 0 ) )
            .orElseThrow(() -> new ResourceNotFoundException("benefit not found for ID: " + tierDTO.getBenefits().get( 0 )));

        return SubscriptionMapper.INSTANCE.toDto(subscription, membership, tierDTO, benefitDTO);
    }

    @Transactional
    public SubscriptionDTO subscribeUser(SubscriptionDTO subscriptionDTO)
    {

        try{
            var planId = subscriptionDTO.getMembershipPlan();
            log.info("Attempting to subscribe to plan ID: {}", planId);

            Membership plan = membershipRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Membership Plan not found: " + planId));

            if ( CollectionUtils.isEmpty(plan.getTier())) {
                log.error("Plan {} has no associated tier IDs.", planId);
                throw new BadRequestException("Selected plan has no configured tiers.");
            }

            var expiryDate = calculateExpiryDate( plan.getMembershipPlan());
            var newSubscription = SubscriptionMapper.INSTANCE.toEntity(subscriptionDTO, expiryDate);


            Subscription savedSubscription = subscriptionRepository.save(newSubscription);
            log.info("User {} successfully subscribed to plan {}. Subscription ID: {}", savedSubscription.getUserId(), planId, savedSubscription.getId());

            return subscriptionDTO;
        }
        catch ( Exception e ){
            log.error( "Exception occurred while subscribing user: {}", ExceptionUtils.getStackTrace( e ) );
        }
        return new SubscriptionDTO();
    }

    @Transactional
    public SubscriptionDTO changeSubscription(String userId, String newPlanId) {

        try {
            log.info("Attempting to change subscription for user to new plan ID: {}", newPlanId);

            Subscription currentSubscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No active subscription found for user: " + userId + " to change."));

            Membership newPlan = membershipRepository.findById(newPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("New Membership Plan not found: " + newPlanId));

            if (currentSubscription.getPlanId().equals(newPlanId)) {
                throw new BadRequestException("User is already subscribed to this plan.");
            }

            if (CollectionUtils.isEmpty(newPlan.getTier())) {
                log.error("New plan {} has no associated tier IDs.", newPlanId);
                throw new BadRequestException("Selected new plan has no configured tiers.");
            }


            var expiryDate = calculateExpiryDate( newPlan.getMembershipPlan());
            var updatedSubscription = SubscriptionMapper.INSTANCE.updateEntity(userId, newPlanId, expiryDate);

            update( updatedSubscription );
            log.info("Subscription for user successfully changed to plan {}. Subscription ID: {}", newPlanId, updatedSubscription.getId());


            return new SubscriptionDTO(newPlanId);
        }
        catch (  Exception e ){
            log.error( "Exception while changing subscription: {}", ExceptionUtils.getStackTrace(e ) );
        }
        return new SubscriptionDTO();
    }

    @Transactional
    public void cancelSubscription(String userId) {
        log.info("Attempting to cancel subscription for user");

        var updatedSubscription = Subscription.createInstanceFrom( userId );
        updatedSubscription.setStatus(Status.CANCELED);

        update( updatedSubscription );
        log.info("Subscription for user has been canceled.");
    }

    private MembershipTierDTO fetchTierDetails(String tierId) {
        if (tierId == null) return null;
        return tiersRepository.findById(tierId)
            .map( TiersMapper.INSTANCE::toDto)
            .orElseGet(() -> {
                log.warn("Tier details not found for tierId: {}. Returning placeholder.", tierId);
                MembershipTierDTO placeholder = new MembershipTierDTO();
                return placeholder;
            });
    }

    private Date calculateExpiryDate( MembershipPlan planType) {
        var startDate =  new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        switch (planType) {
            case MONTHLY:
                calendar.add(Calendar.MONTH, 1);
                break;
            case QUARTERLY:
                calendar.add(Calendar.MONTH, 3);
                break;
            case YEARLY:
                calendar.add(Calendar.YEAR, 1);
                break;
            default:
                log.warn("Unknown plan type: {}. Defaulting to 1 month expiry.", planType);
                calendar.add(Calendar.MONTH, 1);
        }
        return calendar.getTime();
    }


    public void update(@NonNull Subscription subscriptionEntity) {

        var query = new Query();
        query.addCriteria( Criteria.where("userId").in(subscriptionEntity.getUserId()) );

        var update = DbHelper.createUpdateObjectWithFieldsToUpdate(subscriptionEntity, Subscription.class);

        mongoTemplate.updateFirst(query, update, Subscription.class);
    }
}
