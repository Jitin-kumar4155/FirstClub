package com.firstclub.core.entity;

import com.firstclub.core.enums.MembershipTier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tiers")
public class Tiers
{
    @Id
    private String id;

    private MembershipTier membershipTier;

    private String description;

    private List<String> benefits;
}
