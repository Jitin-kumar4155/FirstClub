package com.firstclub.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema (description = "Represents a user's membership subscription details.")
public class SubscriptionDTO {

    @Schema(description = "The membership plan associated with this subscription.")
    @NotNull
    private String membershipPlan;
}
