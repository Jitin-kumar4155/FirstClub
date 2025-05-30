package com.firstclub.core.dto;

import com.firstclub.core.enums.MembershipPlan;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a purchasable membership plan (e.g., Monthly, Yearly).")
public class MembershipPlanDTO {

    @Schema(description = "The type of the plan (e.g., MONTHLY, QUARTERLY, YEARLY).",
        example = "YEARLY")
    @NonNull
    private MembershipPlan membershipPlan;

    @Schema(description = "The price of the membership plan.",
        example = "99.99")
    @NonNull
    private Double price;

    @Schema(description = "A user-friendly description of the plan.",
        example = "Get the best value with our annual membership.")
    private String description;

    @NotEmpty
    @Schema(description = "The membership tier associated with this plan.")
    private List<String> tier;
}
