package com.firstclub.core.dto.subscriptionresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.firstclub.core.enums.MembershipPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude ( JsonInclude.Include.NON_NULL )
@JsonIgnoreProperties ( ignoreUnknown = true )
public class MembershipPlanResponseDTO
{

    private MembershipPlan membershipPlan;

    private Double price;

    private String description;

    private MembershipTierResponseDTO tiers;
}
