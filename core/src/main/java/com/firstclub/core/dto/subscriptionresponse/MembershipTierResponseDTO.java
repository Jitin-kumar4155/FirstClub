package com.firstclub.core.dto.subscriptionresponse;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.firstclub.core.dto.BenefitDTO;
import com.firstclub.core.enums.MembershipTier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude ( JsonInclude.Include.NON_NULL )
@JsonIgnoreProperties ( ignoreUnknown = true )
public class MembershipTierResponseDTO
{

    private MembershipTier membershipTier;

    private String description;

    private BenefitDTO benefit;
}
