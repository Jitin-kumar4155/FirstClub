package com.firstclub.core.controller;

import com.firstclub.core.dto.*;
import com.firstclub.core.dto.subscriptionresponse.SubscriptionResponseDTO;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping ("/v1/membership")
public interface MembershipControllerApi
{

    /*
    Please Note that since we didn't have login mechanism in place so we are generation UUID for users
    once login is there then we can take it from session and use for requests
     */

    /**
     * GET /v1/membership/plans : Fetch all available membership plans.
     * This API retrieves a list of all currently offered membership plans.
     *
     * @return A list of membership plans. (status code 200)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "getAvailablePlans",
        summary = "Get all available membership plans",
        description = "This API retrieves a list of all currently offered membership plans.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Membership plans retrieved successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/plans",
        produces = { "application/json" }
    )
    ResponseEntity<List<MembershipPlanDTO>> getAvailablePlans();


    /**
     * POST /v1/membership/plans : Add a new membership plan.
     * This API allows adding a new membership plan to the database.
     *
     * @param membershipPlanDTO The membership plan details to be added. (required)
     * @return Membership plan added successfully. (status code 201)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "addMembershipPlan",
        summary = "Add a new membership plan",
        description = "This API allows adding a new membership plan to the database.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Membership plan added successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = MembershipPlanDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/plans",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<MembershipPlanDTO> addMembershipPlan(
        @Parameter(name = "membershipPlanDTO", description = "The membership plan details to be added.", required = true, schema = @Schema(implementation = MembershipPlanDTO.class))
        @Valid @RequestBody MembershipPlanDTO membershipPlanDTO
    );



    /**
     * GET /v1/membership/tiers : Fetch all available membership tiers.
     * This API retrieves a list of all membership tiers and their associated benefits.
     *
     * @return A list of membership tiers. (status code 200)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "getAvailableTiers",
        summary = "Get all available membership tiers",
        description = "This API retrieves a list of all membership tiers and their associated benefits.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Membership tiers retrieved successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/tiers",
        produces = { "application/json" }
    )
    ResponseEntity<List<MembershipTierDTO>> getAvailableTiers();

    /**
     * POST /v1/membership/tiers : Add a new membership tier.
     * This API allows adding a new membership tier and returns the updated list of membership tiers.
     *
     * @param membershipTierDTO The membership tier details to be added. (required)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "addMembershipTier",
        summary = "Add a new membership tier",
        description = "This API allows adding a new membership tier and returns the updated list of membership tiers.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Membership tier added successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/tiers",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Void> addMembershipTier(
        @Parameter(name = "membershipTierDTO", description = "The membership tier details to be added.", required = true, schema = @Schema(implementation = MembershipTierDTO.class))
        @Valid @RequestBody MembershipTierDTO membershipTierDTO
    );




    /**
     * GET /v1/membership/users/{userId}/subscription : Fetch current subscription for a user.
     * This API retrieves the active subscription details for a specific user.
     *
     * @param userId The ID of the user. (required)
     * @return Subscription details retrieved successfully. (status code 200)
     * or User or Subscription not found. (status code 404)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "getUserSubscription",
        summary = "Get current subscription for a user",
        description = "This API retrieves the active subscription details for a specific user.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Subscription details retrieved successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User or Subscription not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users/{userId}/subscription",
        produces = { "application/json" }
    )
    ResponseEntity<SubscriptionResponseDTO> getUserSubscription(
        @Parameter(name = "userId", description = "The ID of the user.", required = true, in = ParameterIn.PATH) @NotEmpty @PathVariable("userId") String userId
    );

    /**
     * POST /v1/membership/users/{userId}/subscription : Subscribe a user to a plan.
     * This API creates a new subscription for a user based on the provided plan ID.
     *
     * @param subscribeRequestDTO The plan ID to subscribe to. (required)
     * @return Subscription created successfully. (status code 201)
     * or Invalid request (bad planId, user already subscribed, etc.). (status code 400)
     * or User not found. (status code 404)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "subscribeUser",
        summary = "Subscribe a user to a plan",
        description = "This API creates a new subscription for a user based on the provided plan ID.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Subscription created successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users/subscription",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<SubscriptionDTO> subscribeUser(
        @Parameter(name = "subscribeRequestDTO", description = "The plan ID to subscribe to.", required = true, schema = @Schema(implementation = SubscriptionDTO.class))
        @Valid @RequestBody SubscriptionDTO subscribeRequestDTO
    );

    /**
     * PUT /v1/membership/users/{userId}/subscription : Change a user's subscription plan.
     * This API allows a user to upgrade or downgrade their current membership plan.
     *
     * @param userId The ID of the user. (required)
     * @param subscribeRequestDTO The new plan ID to switch to. (required)
     * @return Subscription changed successfully. (status code 200)
     * or Invalid request. (status code 400)
     * or User or active subscription not found. (status code 404)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "changeSubscription",
        summary = "Change a user's subscription plan",
        description = "This API allows a user to upgrade or downgrade their current membership plan.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Subscription changed successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User or active subscription not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/users/{userId}/subscription",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<SubscriptionDTO> changeSubscription(
        @Parameter(name = "userId", description = "The ID of the user.", required = true, in = ParameterIn.PATH) @NotEmpty @PathVariable("userId") String userId,
        @Parameter(name = "subscribeRequestDTO", description = "The new plan ID to switch to.", required = true, schema = @Schema(implementation = SubscriptionDTO.class)) @Valid @RequestBody SubscriptionDTO subscribeRequestDTO
    );

    /**
     * DELETE /v1/membership/users/{userId}/subscription : Cancel a user's subscription.
     * This API cancels the user's active subscription, typically marking it to not renew at the expiry date.
     *
     * @param userId The ID of the user. (required)
     * @return Subscription cancelled successfully. (status code 204)
     * or User or active subscription not found. (status code 404)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "cancelSubscription",
        summary = "Cancel a user's subscription",
        description = "This API cancels the user's active subscription, typically marking it to not renew at the expiry date.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "204", description = "Subscription cancelled successfully."),
            @ApiResponse(responseCode = "404", description = "User or active subscription not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/users/{userId}/subscription"
    )
    ResponseEntity<Void> cancelSubscription(
        @Parameter(name = "userId", description = "The ID of the user.", required = true, in = ParameterIn.PATH) @NotEmpty @PathVariable("userId") String userId
    );

    /**
     * GET /v1/membership/users/{userId}/benefits : Fetch effective benefits for a user.
     * This API retrieves the list of benefits a user is currently entitled to based on their subscription tier.
     *
     * @param userId The ID of the user. (required)
     * @return List of benefits retrieved successfully. (status code 200)
     * or User or Subscription not found. (status code 404)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "getUserBenefits",
        summary = "Fetch effective benefits for a user",
        description = "This API retrieves the list of benefits a user is currently entitled to based on their subscription tier.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "List of benefits retrieved successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Set.class))
            }),
            @ApiResponse(responseCode = "404", description = "User or Subscription not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users/{userId}/benefits",
        produces = { "application/json" }
    )
    ResponseEntity<Set<BenefitDTO>> getUserBenefits(
        @Parameter(name = "userId", description = "The ID of the user.", required = true, in = ParameterIn.PATH) @NotEmpty @PathVariable("userId") Long userId
    );

    /**
     * GET /v1/membership/users/{userId}/benefits : retrieves the list of all benefits .
     * This API retrieves the list of all benefits
     *
     * @return List of benefits retrieved successfully. (status code 200)
     * Subscription not found. (status code 404)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "getAllBenefits",
        summary = "retrieves the list of all benefits ",
        description = "This API retrieves the list of all benefits.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "List of benefits retrieved successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Set.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users/benefits",
        produces = { "application/json" }
    )
    ResponseEntity<Set<BenefitDTO>> getAllBenefits();




    /**
     * POST /v1/membership/users/benefits : Add benefits for users.
     * This API helps in Adding new benefits for users.
     *
     * @return Added benefits successfully. (status code 200)
     * or Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "addBenefits",
        summary = "Add benefit offers for users",
        description = "This API helps in Adding new benefits for users.",
        tags = { "Membership Management" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Added benefits successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Set.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users/benefits",
        produces = { "application/json" }
    )
    ResponseEntity<Void> addBenefits(
        @Parameter(name = "benefitDTO", description = "Benefits for users", required = true, schema = @Schema(implementation = BenefitDTO.class))
        @Valid @RequestBody BenefitDTO benefitDTO
    );


}
