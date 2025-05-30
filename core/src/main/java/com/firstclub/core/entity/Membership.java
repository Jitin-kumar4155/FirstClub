package com.firstclub.core.entity;

import com.firstclub.core.enums.MembershipPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "membership")
public class Membership
{
    @Id
    private String id;

    private MembershipPlan membershipPlan;

    private Double price;

    private String description;

    private List<String> tier;
}
