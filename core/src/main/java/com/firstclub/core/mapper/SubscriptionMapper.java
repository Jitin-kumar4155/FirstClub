package com.firstclub.core.mapper;

import com.firstclub.core.dto.MembershipTierDTO;
import com.firstclub.core.dto.SubscriptionDTO;
import com.firstclub.core.dto.subscriptionresponse.SubscriptionResponseDTO;
import com.firstclub.core.entity.Benefit;
import com.firstclub.core.entity.Membership;
import com.firstclub.core.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;


@Mapper (componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);


    @Mapping( target = "plan", source = "plan")
    @Mapping( target = "plan.tiers", source = "tier")
    @Mapping( target = "plan.tiers.benefit", source = "benefits")
    SubscriptionResponseDTO toDto( Subscription subscription, Membership plan, MembershipTierDTO tier, Benefit benefits);


    @Mapping(source = "membershipPlan", target = "planId")
    Subscription toEntity(SubscriptionDTO subscriptionDTO);

    @Mapping ( target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping ( target = "userId", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping( target = "planId", source = "subscriptionDTO.membershipPlan")
    @Mapping( target = "startDate", expression = "java(new java.util.Date())")
    @Mapping( target = "expiryDate", source = "expiryDate")
    @Mapping( target = "status", constant = "ACTIVE")
    Subscription toEntity( SubscriptionDTO subscriptionDTO, Date expiryDate);

    @Mapping( target = "status", constant = "ACTIVE")
    Subscription updateEntity( String userId, String planId, Date expiryDate);


    @Named ("mapTier")
    default MembershipTierDTO mapTier(String tierId ) {
        if (tierId == null) {
            return null;
        }

        MembershipTierDTO placeholderTier = new MembershipTierDTO();
        placeholderTier.setDescription("Details for " + tierId);
        return placeholderTier;
    }
}
