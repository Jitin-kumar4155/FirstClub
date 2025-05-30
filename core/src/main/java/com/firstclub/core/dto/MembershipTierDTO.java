package com.firstclub.core.dto;

import com.firstclub.core.enums.MembershipTier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a membership tier (e.g., Silver, Gold) and its associated benefits.")
public class MembershipTierDTO {

    @Schema(description = "The name of the membership tier.",
        example = "GOLD")
    private MembershipTier membershipTier;

    @Schema(description = "A description of the membership tier.",
        example = "Enjoy enhanced benefits and rewards.")
    private String description;

    @Schema(description = "A set of benefits included with this tier.")
    private List<String> benefits;
}
