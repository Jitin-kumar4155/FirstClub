package com.firstclub.core.entity;

import com.firstclub.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private String id;

    private String userId;

    private String planId;

    private Date startDate;

    private Date expiryDate;

    private Status status;

    public static Subscription emptyInstance() {
        return new Subscription();
    }

    public static Subscription createInstanceFrom(String userId) {
        var subscriptionEntity = emptyInstance();
        subscriptionEntity.setUserId(userId);
        return subscriptionEntity;
    }
}
