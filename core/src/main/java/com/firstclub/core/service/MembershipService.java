package com.firstclub.core.service;

import com.firstclub.core.dto.MembershipPlanDTO;
import com.firstclub.core.entity.Membership;
import com.firstclub.core.exception.ResourceNotFoundException;
import com.firstclub.core.mapper.MembershipMapper;
import com.firstclub.core.repository.MembershipRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    /**
     * Adds a new membership plan to the database.
     *
     * @param membershipPlanDTO The DTO containing the plan details.
     * @return The created Membership DTO with its new ID.
     */
    public MembershipPlanDTO addPlan(MembershipPlanDTO membershipPlanDTO) {
        try {
            log.debug("Received request to add membership plan: {}", membershipPlanDTO);

            Membership planEntity = MembershipMapper.INSTANCE.toEntity(membershipPlanDTO);

            Membership savedEntity = membershipRepository.insert(planEntity);

            log.info("Successfully inserted new membership plan with ID: {}", savedEntity.getId());

            return MembershipMapper.INSTANCE.toDto(savedEntity);
        } catch (Exception e) {
            log.error("Error while adding membership plan: {} with message:{}", membershipPlanDTO, ExceptionUtils.getStackTrace(e));

            throw new RuntimeException("Failed to add membership plan", e);
        }
    }

    /**
     * Retrieves all available membership plans.
     *
     * @return A list of MembershipPlanDTOs.
     * @throws ResourceNotFoundException if no plans are found in the database.
     */
    public List<MembershipPlanDTO> getAllPlans() {
        try {
            log.debug("Received request to get all membership plans");
            List<Membership> plans = membershipRepository.findAll();

            if ( CollectionUtils.isEmpty(plans)) {
                log.warn("No membership plans found in the database.");
                throw new ResourceNotFoundException("No Membership Plans found");
            }

            log.info("Found {} membership plans.", plans.size());
            return MembershipMapper.INSTANCE.toMembershipPlanDTOList(plans);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching all membership plans: {}", ExceptionUtils.getStackTrace(e));

            throw new RuntimeException("An error occurred while fetching plans", e);
        }
    }

}
