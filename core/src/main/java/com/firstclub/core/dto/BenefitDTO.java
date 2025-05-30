package com.firstclub.core.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.firstclub.core.enums.MembershipTier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude ( JsonInclude.Include.NON_NULL )
@JsonIgnoreProperties ( ignoreUnknown = true )
@Schema ( description = "Represents a specific benefit or perk offered by a membership tier." )
public class BenefitDTO
{

    @Schema ( description = "A unique benefit with every tier." )
    @NotEmpty
    private String benefit;

    @Schema ( description = "A user-friendly description of the benefit.", example = "Free standard delivery on all eligible orders." )
    @NotEmpty
    private String description;

    @Schema ( description = "The membership tiers that this benefit applies to." )
    @NotEmpty
    private Set<MembershipTier> membershipTier;

}
