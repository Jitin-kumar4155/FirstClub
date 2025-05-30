package com.firstclub.core.mapper;

import com.firstclub.core.dto.MembershipPlanDTO;
import com.firstclub.core.entity.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface MembershipMapper {

    MembershipMapper INSTANCE = Mappers.getMapper(MembershipMapper.class);

    MembershipPlanDTO toDto( Membership membership);

    @Mapping ( target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    Membership toEntity(MembershipPlanDTO membershipPlanDTO);

    List<MembershipPlanDTO> toMembershipPlanDTOList(List<Membership> memberships);
}
