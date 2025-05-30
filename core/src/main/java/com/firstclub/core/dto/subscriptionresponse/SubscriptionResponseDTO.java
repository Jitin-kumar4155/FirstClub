package com.firstclub.core.dto.subscriptionresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.firstclub.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude ( JsonInclude.Include.NON_NULL )
@JsonIgnoreProperties ( ignoreUnknown = true )
public class SubscriptionResponseDTO
{

    private String userId;

    private MembershipPlanResponseDTO plan;

    private Date startDate;

    private Date expiryDate;

    private Status status;

}
