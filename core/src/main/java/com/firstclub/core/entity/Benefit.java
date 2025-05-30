package com.firstclub.core.entity;

import com.firstclub.core.enums.MembershipTier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document ( collection = "benefit" )
public class Benefit implements Serializable
{
    @Id
    private String id;

    private String benefit;

    private String description;

    private Set<MembershipTier> membershipTier;
}
