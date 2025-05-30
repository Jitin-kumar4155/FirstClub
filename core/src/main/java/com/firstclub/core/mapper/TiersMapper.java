package com.firstclub.core.mapper;

import com.firstclub.core.dto.MembershipTierDTO;
import com.firstclub.core.entity.Tiers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TiersMapper
{
    TiersMapper INSTANCE = Mappers.getMapper( TiersMapper.class );

    MembershipTierDTO toDto( Tiers tiers );

    @Mapping ( target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    Tiers toEntity( MembershipTierDTO membershipTierDTO );

    List<MembershipTierDTO> toMembershipTierDTOList(List<Tiers> tiersList);
}
