package com.firstclub.core.mapper;

import com.firstclub.core.dto.BenefitDTO;
import com.firstclub.core.entity.Benefit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface BenefitMapper
{
    BenefitMapper INSTANCE = Mappers.getMapper( BenefitMapper.class );

    @Mapping( target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    Benefit toBenefitEntity( BenefitDTO benefitDTO );

    Set<BenefitDTO> toBenefitDTOSet( List<Benefit> benefits);
}
